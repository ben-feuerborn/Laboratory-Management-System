package com.clinic.patient_provider_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.patient_provider_management.model.Patient;
import com.clinic.patient_provider_management.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    public List<Patient> getAll() {
        return repository.findAll();
    }

    public long countDistinctProviders() {
        return repository.countDistinctProviders();
    }

    public List<Patient> findByProvider(String provider) {
        return repository.findByProvider(provider);
    }
}
