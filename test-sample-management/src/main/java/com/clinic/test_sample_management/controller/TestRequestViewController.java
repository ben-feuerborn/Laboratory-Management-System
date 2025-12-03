package com.clinic.test_sample_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinic.test_sample_management.model.TestRequest;
import com.clinic.test_sample_management.service.TestRequestService;

@Controller
@RequestMapping("/test-requests")
public class TestRequestViewController {

    private final TestRequestService svc;

    public TestRequestViewController(TestRequestService svc) {
        this.svc = svc;
    }

    @GetMapping("/add-request")
    public String showEnterTestRequestForm(Model model) {
        model.addAttribute("testRequest", new TestRequest());
        return "TestandSampleManagementPages/EnterTestRequests";
    }

    @GetMapping("/verify-request")
    public String showVerifyPage(Model model) {
        model.addAttribute("requests", svc.findAll());
        return "TestandSampleManagementPages/VerifyTestRequest";
    }
}
