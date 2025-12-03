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
@Table(name = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialty;
    private String phone;
    private String email;
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist(){
        if(createdDate == null){
            createdDate = LocalDateTime.now();
        }
    }
}
