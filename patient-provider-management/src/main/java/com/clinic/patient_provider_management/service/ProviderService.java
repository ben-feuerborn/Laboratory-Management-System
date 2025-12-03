package com.clinic.patient_provider_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.patient_provider_management.model.Provider;
import com.clinic.patient_provider_management.repository.ProviderRepository;

@Service
public class ProviderService {
    @Autowired private ProviderRepository repository;
    public Provider save(Provider p){ return repository.save(p); }
    public List<Provider> all(){ return repository.findAll(); }
}

