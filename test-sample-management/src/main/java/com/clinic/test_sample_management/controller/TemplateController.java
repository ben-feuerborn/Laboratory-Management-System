package com.clinic.test_sample_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TemplateController {

    @GetMapping("/{folder}/{page}.html")
    public String renderTemplate(@PathVariable String folder, @PathVariable String page) {
        // Return the template path under templates/ e.g. "UserDashboards/LabManagerDashboard"
        return folder + "/" + page;
    }
}
