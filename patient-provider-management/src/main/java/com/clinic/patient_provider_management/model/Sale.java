package com.clinic.patient_provider_management.model;

import java.math.BigDecimal;
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
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal cost;
    private String paymentOption; // e.g. CASH / CARD / INSURANCE
    private LocalDateTime saleDate;
    private String patientName;

    @PrePersist
    public void prePersist(){
        if (saleDate == null) saleDate = LocalDateTime.now();
    }
}
