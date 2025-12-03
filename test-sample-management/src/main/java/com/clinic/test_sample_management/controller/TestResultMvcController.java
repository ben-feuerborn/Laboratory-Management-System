package com.clinic.test_sample_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.clinic.test_sample_management.model.TestResult;

@Controller
public class TestResultMvcController {

    @GetMapping("/TestandSampleManagementPages/TestResultEntry.html")
    public String showTestResultEntryForm(Model model) {
        model.addAttribute("testResult", new TestResult());
        return "TestandSampleManagementPages/TestResultEntry";
    }
}
