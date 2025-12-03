package com.clinic.test_sample_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Default landing page
    @GetMapping({"", "/"})
    public String home() {
        return "test-sample-dashboard";
    }
    
    // Individual dashboards
    @GetMapping("/cashier")
    public String cashierDashboard() {
        return "user-dashboards/cashier-dashboard";
    }

    @GetMapping("/lab-manager")
    public String labManagerDashboard() {
        return "user-dashboards/lab-manager-dashboard";
    }

    @GetMapping("/lab-manager/menu")
    public String labManagerMenu() {
        return "sub-menus/lab-manager-sub-menu";
    }

    @GetMapping("/lab-tech")
    public String labTechDashboard() {
        return "user-dashboards/lab-tech-dashboard";
    }

    @GetMapping("/lab-tech/menu")
    public String labTechMenu() {
        return "sub-menus/lab-tech-sub-menu";
    }

    @GetMapping("/pathologist")
    public String pathologistDashboard() {
        return "user-dashboards/pathologist-dashboard";
    }

    @GetMapping("/patient")
    public String patientDashboard() {
        return "user-dashboards/patient-dashboard";
    }

    @GetMapping("/physician")
    public String physicianDashboard() {
        return "user-dashboards/physician-dashboard";
    }

    @GetMapping("/specimen-collector")
    public String specimenCollectorDashboard() {
        return "user-dashboards/specimen-collector-dashboard";
    }

    @GetMapping("/specimen-collector/menu")
    public String specimenCollectorMenu() {
        return "sub-menus/specimen-collector-sub-menu";
    }
}
