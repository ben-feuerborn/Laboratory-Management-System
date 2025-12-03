package com.clinic.patient_provider_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.patient_provider_management.model.Medication;
import com.clinic.patient_provider_management.repository.MedicationRepository;

@Service
public class MedicationService {
    @Autowired private MedicationRepository repository;
    public Medication save(Medication m){ return repository.save(m); }
    public List<Medication> all(){ return repository.findAll(); }
}

