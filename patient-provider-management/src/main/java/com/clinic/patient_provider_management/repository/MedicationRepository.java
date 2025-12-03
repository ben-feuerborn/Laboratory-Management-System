package com.clinic.patient_provider_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinic.patient_provider_management.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> { }

