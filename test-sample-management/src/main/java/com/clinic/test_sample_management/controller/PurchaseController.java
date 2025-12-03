package com.clinic.test_sample_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clinic.test_sample_management.model.PurchaseData;
import com.clinic.test_sample_management.model.TestItem;

@Controller
public class PurchaseController {

    @PostMapping("/purchase")
    public String submitPurchase(
            @RequestParam String patientName,
            @RequestParam String patientId,
            @RequestParam(required = false, name = "tests") List<String> tests,
            RedirectAttributes ra) {

        if (tests == null) {
            tests = new ArrayList<>();
        }

        List<TestItem> items = new ArrayList<>();
        int total = 0;
        for (String t : tests) {
            // expected format: Name|Price
            String[] parts = t.split("\\|");
            String name = parts.length > 0 ? parts[0] : t;
            int price = 0;
            if (parts.length > 1) {
                try { price = Integer.parseInt(parts[1]); } catch (NumberFormatException ex) { price = 0; }
            }
            items.add(new TestItem(name, price));
            total += price;
        }

        PurchaseData pd = new PurchaseData(patientName, patientId, items, total);
        ra.addFlashAttribute("purchase", pd);
        return "redirect:/TestandSampleManagementPages/PurchaseTestCheckout.html";
    }

    @GetMapping("/TestandSampleManagementPages/PurchaseTestCheckout.html")
    public String showCheckout(@ModelAttribute("purchase") PurchaseData purchase, Model model) {
        if (purchase == null || purchase.getTests() == null || purchase.getTests().isEmpty()) {
            return "redirect:/TestandSampleManagementPages/PurchaseTest.html";
        }
        model.addAttribute("purchase", purchase);
        return "TestandSampleManagementPages/PurchaseTestCheckout";
    }

    @GetMapping("/TestandSampleManagementPages/PurchaseTest.html")
    public String showPurchaseForm() {
        return "TestandSampleManagementPages/PurchaseTest";
    }

    @PostMapping("/purchase/confirm")
    public ResponseEntity<Void> confirmPurchase() {
        // In a real app you'd persist the transaction here and return details.
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order/physician")
    public String submitPhysicianOrder(@RequestParam(required = false, name = "tests") List<String> tests,
                                       RedirectAttributes ra) {
        if (tests == null) tests = new java.util.ArrayList<>();

        List<TestItem> items = new java.util.ArrayList<>();
        int total = 0;
        for (String t : tests) {
            String[] parts = t.split("\\|");
            String name = parts.length > 0 ? parts[0] : t;
            int price = 0;
            if (parts.length > 1) {
                try { price = Integer.parseInt(parts[1]); } catch (NumberFormatException ex) { price = 0; }
            }
            items.add(new TestItem(name, price));
            total += price;
        }

        PurchaseData pd = new PurchaseData(null, null, items, total);
        ra.addFlashAttribute("purchase", pd);
        return "redirect:/TestandSampleManagementPages/OrderTestCheckout.html";
    }
}
