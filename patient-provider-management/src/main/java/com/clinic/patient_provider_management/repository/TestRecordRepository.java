package com.clinic.patient_provider_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinic.patient_provider_management.model.TestRecord;

public interface TestRecordRepository extends JpaRepository<TestRecord, Long> { }

