package com.clinic.patient_provider_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clinic.patient_provider_management.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByProvider(String provider);

    @Query("SELECT COUNT(DISTINCT p.provider) FROM Patient p WHERE p.provider IS NOT NULL AND p.provider <> ''")
    long countDistinctProviders();
}

