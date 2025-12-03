package com.clinic.patient_provider_management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private String category;
    private String status;
    private LocalDate orderedDate;
    private LocalDate resultDate;
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist(){
        if(createdDate == null){
            createdDate = LocalDateTime.now();
        }
    }
}

