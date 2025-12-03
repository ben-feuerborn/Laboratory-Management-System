package com.clinic.patient_provider_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clinic.patient_provider_management.model.Patient;
import com.clinic.patient_provider_management.model.Provider;
import com.clinic.patient_provider_management.model.Medication;
import com.clinic.patient_provider_management.model.TestRecord;
import com.clinic.patient_provider_management.model.Report;
import com.clinic.patient_provider_management.service.PatientService;
import com.clinic.patient_provider_management.service.ProviderService;
import com.clinic.patient_provider_management.service.MedicationService;
import com.clinic.patient_provider_management.service.TestRecordService;
import com.clinic.patient_provider_management.service.ReportService;

@Controller
public class PatientProviderViewController {

    @Autowired private PatientService patientService;
    @Autowired private ProviderService providerService;
    @Autowired private MedicationService medicationService;
    @Autowired private TestRecordService testRecordService;
    @Autowired private ReportService reportService;

    @GetMapping({"/", "/patient-provider"})
    public String viewPatients(Model model, @RequestParam(name = "ds", required = false, defaultValue = "patients") String dataset) {
        model.addAttribute("patients", patientService.getAll());
        model.addAttribute("providersCount", providerService.all().size());
        model.addAttribute("dataset", dataset); // initial dataset selection
        return "patient-list";
    }

    @GetMapping({"/add", "/patient-provider/add"})
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "add-patient";
    }

    @PostMapping({"/add", "/patient-provider/add", "/save", "/patient-provider/save"})
    public String submitPatient(@ModelAttribute Patient patient) {
        patientService.save(patient);
        return "redirect:/patient-provider?ds=patients";
    }

    // Provider form
    @GetMapping({"/add/provider", "/patient-provider/add/provider"})
    public String addProviderForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "add-provider";
    }
    @PostMapping({"/add/provider", "/patient-provider/add/provider"})
    public String submitProvider(@ModelAttribute Provider provider) {
        providerService.save(provider);
        return "redirect:/patient-provider?ds=providers";
    }

    // Medication form
    @GetMapping({"/add/medication", "/patient-provider/add/medication"})
    public String addMedicationForm(Model model) {
        model.addAttribute("medication", new Medication());
        return "add-medication";
    }
    @PostMapping({"/add/medication", "/patient-provider/add/medication"})
    public String submitMedication(@ModelAttribute Medication medication) {
        medicationService.save(medication);
        return "redirect:/patient-provider?ds=medications";
    }

    // Test record form
    @GetMapping({"/add/test", "/patient-provider/add/test"})
    public String addTestForm(Model model) {
        model.addAttribute("test", new TestRecord());
        return "add-test";
    }
    @PostMapping({"/add/test", "/patient-provider/add/test"})
    public String submitTest(@ModelAttribute TestRecord testRecord) {
        testRecordService.save(testRecord);
        return "redirect:/patient-provider?ds=tests";
    }

    // Report form
    @GetMapping({"/add/report", "/patient-provider/add/report"})
    public String addReportForm(Model model) {
        model.addAttribute("report", new Report());
        return "add-report";
    }
    @PostMapping({"/add/report", "/patient-provider/add/report"})
    public String submitReport(@ModelAttribute Report report) {
        reportService.save(report);
        return "redirect:/patient-provider?ds=reports";
    }
}
