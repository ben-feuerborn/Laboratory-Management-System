package com.clinic.patient_provider_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.patient_provider_management.model.TestRecord;
import com.clinic.patient_provider_management.repository.TestRecordRepository;

@Service
public class TestRecordService {
    @Autowired private TestRecordRepository repository;
    public TestRecord save(TestRecord t){ return repository.save(t); }
    public List<TestRecord> all(){ return repository.findAll(); }
}

