package com.clinic.test_sample_management.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------------------------
    // Request Information
    // -------------------------
    @NotNull
    private String reportId;       // Request Id *

    @NotNull
    private LocalDate reportDate;  // Request Date *

    @NotNull
    private LocalTime reportTime;  // Request Time *

    // -------------------------
    // Patient Information
    // -------------------------
    @NotNull
    private String patientId;       // Patient Id *

    @NotNull
    private String patientName;     // Patient Name *

    @NotNull
    private LocalDate dateOfBirth;  // Date of Birth *

    @NotNull
    private String sex;             // Sex *

    private String contactNumber;   // Optional

    // -------------------------
    // Ordering Physician
    // -------------------------
    @NotNull
    private String physicianId;      // Physician Id *

    @NotNull
    private String physicianName;    // Physician Name *

    private String physicianContact; // Optional

    // -------------------------
    // Sample Collection
    // -------------------------
    @NotNull
    private String sampleType;       // Sample Type *

    @NotNull
    private String sampleId;         // Sample Id *

    @NotNull
    private LocalDate collectionDate; // Collection Date *

    @NotNull
    private LocalTime collectionTime; // Collection Time *

    private String collectionLocation;   // Optional

    @NotNull
    private String collectionTechnicianId;   // Technician Id *

    @NotNull
    private String collectionTechnicianName; // Technician Name *

    // -------------------------
    // Requested Tests
    // -------------------------
    @NotNull
    private String testsRequested;    // Textarea: list of requested tests *

    // -------------------------
    // Billing / Insurance
    // -------------------------
    @NotNull
    private String billingType;       // Insurance / Self-Pay *

    private String insuranceProvider;       // Optional

    private String insurancePolicyNumber;   // Optional

    // -------------------------
    // Automatic / audit fields
    // -------------------------
    private LocalDate dateRecorded;

    @PrePersist
    public void prePersist() {
        this.dateRecorded = LocalDate.now();
    }
}
