package com.clinic.patient_provider_management.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clinic.patient_provider_management.model.Provider;
import com.clinic.patient_provider_management.model.Medication;
import com.clinic.patient_provider_management.model.TestRecord;
import com.clinic.patient_provider_management.model.Report;
import com.clinic.patient_provider_management.service.ProviderService;
import com.clinic.patient_provider_management.service.MedicationService;
import com.clinic.patient_provider_management.service.TestRecordService;
import com.clinic.patient_provider_management.service.ReportService;
import com.clinic.patient_provider_management.service.PatientService;
import com.clinic.patient_provider_management.model.Patient;
import com.clinic.patient_provider_management.service.SaleService;
import com.clinic.patient_provider_management.model.Sale;

import java.time.LocalDate;
import java.math.BigDecimal;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(
        ProviderService providerService,
        MedicationService medicationService,
        TestRecordService testRecordService,
        ReportService reportService,
        PatientService patientService,
        SaleService saleService
    ) {
        return args -> {
            // Seed providers
            if (providerService.all().isEmpty()) {
                providerService.save(new Provider(null, "Dr. Alice Carter", "Pathology", "555-1111", "alice@clinic.test", null));
                providerService.save(new Provider(null, "Dr. Ben Ortiz", "Microbiology", "555-2222", "ben@clinic.test", null));
            }
            // Seed medications
            if (medicationService.all().isEmpty()) {
                medicationService.save(new Medication(null, "Amoxicillin", "500mg", "TID", "Take with food", null));
                medicationService.save(new Medication(null, "Ibuprofen", "200mg", "PRN", "Max 1200mg/day", null));
            }
            // Seed test records
            if (testRecordService.all().isEmpty()) {
                testRecordService.save(new TestRecord(null, "CBC", "Hematology", "Completed", LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), null));
                testRecordService.save(new TestRecord(null, "Lipid Panel", "Chemistry", "In Progress", LocalDate.now(), null, null));
            }
            // Seed reports
            if (reportService.all().isEmpty()) {
                reportService.save(new Report(null, "Weekly Lab Summary", "Operational", "System", null, null, null));
                reportService.save(new Report(null, "Quality Control Q4", "QC", "Auditor", null, null, null));
            }
            // Seed patients
            if (patientService.getAll().isEmpty()) {
                Patient p = new Patient();
                p.setFirstName("Jane");
                p.setLastName("Doe");
                p.setGender("F");
                p.setPhone("555-3333");
                p.setEmail("jane.doe@clinic.test");
                p.setAddress("123 Wellness Way");
                p.setMedicalHistory("No significant history.");
                p.setDateOfBirth(LocalDate.of(1990, 5, 14));
                p.setProvider("Dr. Alice Carter");
                patientService.save(p);
            }
            // Seed sales
            if (saleService.all().isEmpty()) {
                saleService.save(new Sale(null, new BigDecimal("49.99"), "CARD", null, "Jane Doe"));
                saleService.save(new Sale(null, new BigDecimal("89.50"), "INSURANCE", null, "Mark Lee"));
            }
        };
    }
}
