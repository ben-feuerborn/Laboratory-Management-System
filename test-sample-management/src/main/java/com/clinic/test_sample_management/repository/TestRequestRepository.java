package com.clinic.test_sample_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.test_sample_management.model.TestRequest;

public interface TestRequestRepository extends JpaRepository<TestRequest, Long> {
    Optional<TestRequest> findByReportId(String reportId);
    List<TestRequest> findByPatientId(String patientId);
}
