package com.clinic.patient_provider_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.patient_provider_management.model.Sale;
import com.clinic.patient_provider_management.repository.SaleRepository;

@Service
public class SaleService {
    @Autowired private SaleRepository repository;
    public Sale save(Sale s){ return repository.save(s); }
    public List<Sale> all(){ return repository.findAll(); }
}

