package com.clinic.patient_provider_management.model;

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
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private String author;
    private LocalDateTime createdDate;
    private String filePath; // Path to generated PDF
    private LocalDateTime generatedAt; // when PDF created

    @PrePersist
    public void prePersist(){
        if(createdDate == null){
            createdDate = LocalDateTime.now();
        }
    }
}
