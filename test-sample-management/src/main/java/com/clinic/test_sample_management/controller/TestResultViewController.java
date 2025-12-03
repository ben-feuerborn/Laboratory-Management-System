package com.clinic.test_sample_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clinic.test_sample_management.model.TestResult;
import com.clinic.test_sample_management.service.TestResultService;

@Controller
@RequestMapping("/test-results")    
public class TestResultViewController {

    @Autowired
    private TestResultService service;

    // Show all Test Results
    @GetMapping
    public String viewAllResults(Model model) {
        model.addAttribute("results", service.getAllResults());
        return "TestandSampleManagementPages/TestResultsPendingApproval"; // make sure template exists here
    }

    // Show Form to Add New Result
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("testResult", new TestResult());
        // Must match template folder and file name
        return "TestandSampleManagementPages/TestResultEntry";
    }

    // Save New Test Result
    @PostMapping("/add")
    public String submitNewTestResult(@ModelAttribute("testResult") TestResult testResult, Model model) {
        service.addTestResult(testResult);
        // Do not redirect — stay on the entry page and show a success message.
        model.addAttribute("testResult", new TestResult());
        model.addAttribute("successMessage", "Test result saved successfully.");
        return "TestandSampleManagementPages/TestResultEntry";
    }

    // Show Approved Test Results
    @GetMapping("/approved")
    public String showApprovedTestResults() {
        return "TestandSampleManagementPages/ApprovedTestResultsList";
    }

    // Delete Result
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteTestResult(id);
        return "redirect:/test-results";
    }

    // Return Pending Results as JSON (API)
    @GetMapping("/pending")
    @ResponseBody
    public List<TestResult> getPendingResults() {
        return service.getPendingResults();
    }

    // Approve a Test Result 
    @PostMapping("/approve/{id}")
    @ResponseBody
    public String approveResult(@PathVariable Long id) {
        service.approveResult(id);
        return "OK";
    }
}
