package com.clinic.test_sample_management.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinic.test_sample_management.model.TestRequest;
import com.clinic.test_sample_management.service.TestRequestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/test-requests")
public class TestRequestMvcController {
    private final TestRequestService svc;

    public TestRequestMvcController(TestRequestService svc) {
        this.svc = svc;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("testRequest", new TestRequest());
        return "TestandSampleManagementPages/EnterTestRequests";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute TestRequest testRequest, BindingResult bindingResult, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("testRequest", testRequest);
            return "TestandSampleManagementPages/EnterTestRequests";
        }
        svc.save(testRequest);
        ra.addFlashAttribute("success", "Test request submitted successfully.");
        return "redirect:/test-requests/new";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", svc.findAll());
        return "TestandSampleManagementPages/PatientTestResultList";
    }

    @GetMapping("/verify")
    public String verifyList(Model model) {
        model.addAttribute("requests", svc.findAll());
        return "TestandSampleManagementPages/VerifyTestRequest";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Optional<TestRequest> r = svc.findById(id);
        if (r.isPresent()) {
            model.addAttribute("testRequest", r.get());
            return "TestandSampleManagementPages/VerifyTestRequest";
        }
        return "redirect:/test-requests";
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        svc.deleteById(id);
        String x = request.getHeader("X-Requested-With");
        if (x != null && "XMLHttpRequest".equalsIgnoreCase(x)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/test-requests/verify").build();
    }
}
