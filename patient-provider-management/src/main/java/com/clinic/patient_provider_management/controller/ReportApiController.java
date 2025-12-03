package com.clinic.patient_provider_management.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.patient_provider_management.model.Report;
import com.clinic.patient_provider_management.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportApiController {
    @Autowired private ReportService service;
    @GetMapping public List<Report> all(){ return service.all(); }
    @PostMapping public ResponseEntity<Report> create(@RequestBody Report r){ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(r)); }

    @PostMapping("/generate")
    public ResponseEntity<Report> generate(@RequestParam(defaultValue = "Inventory") String type){
        Report generated = service.generateReport(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(generated);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> download(@PathVariable Long id){
        File f = service.getReportFile(id);
        if(f == null || !f.exists()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            byte[] data = Files.readAllBytes(f.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", f.getName());
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
