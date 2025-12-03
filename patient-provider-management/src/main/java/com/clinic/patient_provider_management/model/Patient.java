package com.clinic.patient_provider_management.model;

import java.time.LocalDate;

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
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Legacy combined name retained for backward compatibility
    private String name;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phone;

    private String email;

    private String address;

    private String medicalHistory;

    // Optional provider reference (string for now)
    private String provider;

    private LocalDate registeredDate;

    @PrePersist
    public void prePersist() {
        if (registeredDate == null) {
            registeredDate = LocalDate.now();
        }
        if ((name == null || name.isBlank()) && firstName != null && lastName != null) {
            name = firstName + " " + lastName;
        }
    }
}
