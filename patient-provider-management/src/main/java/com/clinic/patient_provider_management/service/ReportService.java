package com.clinic.patient_provider_management.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.patient_provider_management.model.Report;
import com.clinic.patient_provider_management.repository.ReportRepository;
import com.clinic.patient_provider_management.client.InventoryClient;
import com.clinic.patient_provider_management.service.SaleService;
import com.clinic.patient_provider_management.model.Sale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import java.awt.image.BufferedImage;
import java.awt.Color;

@Service
public class ReportService {

    @Autowired private ReportRepository repository;
    @Autowired private InventoryClient inventoryClient;
    @Autowired private SaleService saleService;

    public Report save(Report r){ return repository.save(r); }
    public List<Report> all(){ return repository.findAll(); }

    private PDImageXObject loadDogImage(PDDocument doc) {
        String path = "static/images/dogs.jpg"; // resource under src/main/resources
        try (InputStream is = ReportService.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                System.out.println("[ReportService] dogs.jpg not found at classpath:" + path);
                return null;
            }
            byte[] bytes = is.readAllBytes();
            if (bytes.length == 0) {
                System.out.println("[ReportService] dogs.jpg empty at:" + path);
                return null;
            }
            System.out.println("[ReportService] Loaded dogs.jpg from:" + path + " size=" + bytes.length);
            return PDImageXObject.createFromByteArray(doc, bytes, "dog");
        } catch (IOException e) {
            System.out.println("[ReportService] Error loading dogs.jpg: " + e.getMessage());
            return null;
        }
    }

    private void drawTable(PDPageContentStream cs, PDPage page, float startY, String title,
                           List<String> headers, List<List<String>> rows) throws IOException {
        float pageWidth = page.getMediaBox().getWidth();
        float margin = 40f;
        float tableWidth = pageWidth - margin * 2;
        float y = startY;
        float rowHeight = 18f;
        int cols = headers.size();
        float colWidth = tableWidth / cols;

        // Header background
        cs.setNonStrokingColor(230/255f,236/255f,245/255f); // light blue-gray
        cs.addRect(margin, y - rowHeight, tableWidth, rowHeight);
        cs.fill();
        cs.setNonStrokingColor(0f,0f,0f);

        // Header text
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 11);
        cs.newLineAtOffset(margin + 4, y - 13);
        for (int i=0;i<cols;i++) {
            String h = headers.get(i);
            cs.showText(truncate(h, (int)(colWidth/6))); // crude width-based truncation
            if (i < cols-1) {
                cs.newLineAtOffset(colWidth, 0);
            }
        }
        cs.endText();

        // Horizontal line under header
        cs.setStrokingColor(200/255f,205/255f,215/255f);
        cs.moveTo(margin, y - rowHeight);
        cs.lineTo(margin + tableWidth, y - rowHeight);
        cs.stroke();

        y -= rowHeight;
        int rowIndex = 0;
        for (List<String> row : rows) {
            if (y < 60) break; // simple page cut-off
            // Alternate background
            if (rowIndex % 2 == 0) {
                cs.setNonStrokingColor(247/255f,249/255f,252/255f);
                cs.addRect(margin, y - rowHeight, tableWidth, rowHeight);
                cs.fill();
                cs.setNonStrokingColor(0f,0f,0f);
            }
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 10);
            cs.newLineAtOffset(margin + 4, y - 13);
            for (int i=0;i<cols;i++) {
                String cell = i < row.size() ? row.get(i) : "";
                cs.showText(truncate(cell, (int)(colWidth/6)));
                if (i < cols-1) {
                    cs.newLineAtOffset(colWidth, 0);
                }
            }
            cs.endText();
            // Row bottom line
            cs.setStrokingColor(225/255f,230/255f,235/255f);
            cs.moveTo(margin, y - rowHeight);
            cs.lineTo(margin + tableWidth, y - rowHeight);
            cs.stroke();
            y -= rowHeight;
            rowIndex++;
        }
        // Outer border left/right
        cs.setStrokingColor(200/255f,205/255f,215/255f);
        cs.moveTo(margin, startY - rowHeight); cs.lineTo(margin, y); cs.stroke();
        cs.moveTo(margin + tableWidth, startY - rowHeight); cs.lineTo(margin + tableWidth, y); cs.stroke();
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, Math.max(0,max-1)) + "…";
    }

    private List<String> extractInventoryHeaders(List<Object> inventory, int maxColumns) {
        LinkedHashSet<String> keys = new LinkedHashSet<>();
        for (Object o : inventory) {
            if (o instanceof Map<?,?> map) {
                for (Object k : map.keySet()) {
                    if (k != null) keys.add(k.toString());
                    if (keys.size() >= maxColumns) break;
                }
            }
            if (keys.size() >= maxColumns) break;
        }
        return new ArrayList<>(keys);
    }
    private List<List<String>> buildInventoryRows(List<Object> inventory, List<String> headers, int maxRows) {
        List<List<String>> rows = new ArrayList<>();
        int idx = 1;
        for (Object o : inventory) {
            if (idx > maxRows) break;
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(idx));
            if (o instanceof Map<?,?> map) {
                for (String h : headers) {
                    Object val = map.get(h);
                    row.add(val == null ? "" : String.valueOf(val));
                }
            } else {
                // Fallback: single summary across all headers
                for (int i=0;i<headers.size();i++) {
                    row.add(o == null ? "" : o.toString());
                }
            }
            rows.add(row);
            idx++;
        }
        return rows;
    }

    public Report generateReport(String type){
        String normalized = type.toLowerCase();
        Report report = new Report();
        report.setTitle(capitalize(type) + " Report");
        report.setType(normalized);
        report.setAuthor("System");
        report.setGeneratedAt(LocalDateTime.now());
        String fileName = "report_" + normalized + "_" + System.currentTimeMillis() + ".pdf";
        File pdfFile = new File(System.getProperty("java.io.tmpdir"), fileName);
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                if (normalized.equals("compliance")) {
                    // Center phrase "I LOVE RFK JR"
                    String phrase = "I LOVE RFK JR";
                    cs.beginText();
                    cs.setFont(PDType1Font.HELVETICA_BOLD, 36);
                    float pageWidth = page.getMediaBox().getWidth();
                    float pageHeight = page.getMediaBox().getHeight();
                    float textWidth = (PDType1Font.HELVETICA_BOLD.getStringWidth(phrase) / 1000f) * 36f;
                    float startX = (pageWidth - textWidth) / 2f;
                    float startY = pageHeight / 2f + 100; // push text up to leave space for image
                    cs.newLineAtOffset(startX, startY);
                    cs.showText(phrase);
                    cs.endText();

                    // Attempt to load dogs.jpg
                    PDImageXObject imageXObject = loadDogImage(doc);
                    if (imageXObject == null) {
                        // Fallback placeholder if resource missing
                        BufferedImage placeholder = new BufferedImage(400, 250, BufferedImage.TYPE_INT_RGB);
                        java.awt.Graphics2D g = placeholder.createGraphics();
                        g.setColor(Color.WHITE); g.fillRect(0,0,400,250);
                        g.setColor(Color.GRAY); g.drawRect(0,0,399,249);
                        g.setColor(Color.DARK_GRAY);
                        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
                        g.drawString("dogs.jpg missing", 80, 135);
                        g.dispose();
                        imageXObject = LosslessFactory.createFromImage(doc, placeholder);
                    }
                    float imgWidth = imageXObject.getWidth();
                    float imgHeight = imageXObject.getHeight();
                    float maxWidth = 400f;
                    float scale = imgWidth > maxWidth ? maxWidth / imgWidth : 1f;
                    float drawW = imgWidth * scale;
                    float drawH = imgHeight * scale;
                    float imgX = (pageWidth - drawW)/2f;
                    float imgY = startY - drawH - 40;
                    cs.drawImage(imageXObject, imgX, imgY, drawW, drawH);
                } else {
                    // Header (improved styling retained)
                    cs.beginText();
                    cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
                    cs.newLineAtOffset(40, 760);
                    cs.showText(report.getTitle());
                    cs.endText();

                    cs.beginText();
                    cs.setFont(PDType1Font.HELVETICA, 11);
                    cs.newLineAtOffset(40, 740);
                    cs.showText("Generated: " + report.getGeneratedAt());
                    cs.endText();

                    float tableStartY = 710f;
                    if (normalized.equals("inventory")) {
                        List<Object> inventory = inventoryClient.getInventoryItems();
                        List<String> fieldHeaders = extractInventoryHeaders(inventory, 12); // limit columns
                        List<String> headers = new ArrayList<>();
                        headers.add("#");
                        headers.addAll(fieldHeaders);
                        List<List<String>> rows = buildInventoryRows(inventory, fieldHeaders, 200);
                        drawTable(cs, page, tableStartY, "Inventory", headers, rows);
                    } else if (normalized.equals("sales")) {
                        List<Sale> sales = saleService.all();
                        List<List<String>> rows = new ArrayList<>();
                        for (Sale s : sales) {
                            rows.add(Arrays.asList(
                                String.valueOf(s.getId()),
                                s.getCost() != null ? s.getCost().toString() : "",
                                s.getPaymentOption(),
                                s.getSaleDate() != null ? s.getSaleDate().toString() : "",
                                truncate(s.getPatientName(), 30)
                            ));
                        }
                        drawTable(cs, page, tableStartY, "Sales", Arrays.asList("ID","Cost","Payment","Date","Patient"), rows);
                    }
                }
            }
            doc.save(pdfFile);
            report.setFilePath(pdfFile.getAbsolutePath());
        } catch (Exception ex){
            report.setFilePath("ERROR:" + ex.getMessage());
        }
        return repository.save(report);
    }

    private String capitalize(String s){
        return s == null || s.isBlank() ? "" : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public File getReportFile(Long id){
        return repository.findById(id)
            .filter(r -> r.getFilePath() != null && !r.getFilePath().startsWith("ERROR:"))
            .map(r -> new File(r.getFilePath()))
            .orElse(null);
    }
}
