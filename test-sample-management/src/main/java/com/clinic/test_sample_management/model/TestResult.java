package com.clinic.test_sample_management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "test_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    // Custom Fields
    @NotNull
    private String testID;

    // Report ID (database column `reportid` exists and is NOT NULL in the schema)
    // Map explicitly to the DB column name to avoid naming mismatches.
    @jakarta.persistence.Column(name = "reportid")
    private String reportID;

    @NotNull
    private LocalDateTime reportDateTime;

    @NotNull
    private String patientID;

    @NotNull
    private String patientName;

    @NotNull
    private LocalDate dob;

    @NotNull
    private String gender;

    @NotNull
    private String physicianID;

    @NotNull
    private String physicianName;

    @NotNull
    private String sampleType;

    @NotNull
    private String sampleID;

    @NotNull
    private LocalDateTime collectionDateTime;

    private String collectedBy;

    private String testPerformedBy;

    private String machine;

    @NotNull
    private String testName;

    private String resultValues;

    private String units;

    private String referenceRange;

    private String resultStatus;

    private LocalDateTime resultsReceivedDateTime;

    private String insuranceProvider;

    private String insurancePolicy;

    // Automatically set
    private LocalDate dateRecorded;

    @PrePersist
    public void prePersist() {
        this.dateRecorded = LocalDate.now();
    }
}
