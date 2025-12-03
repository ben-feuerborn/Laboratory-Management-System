package com.clinic.test_sample_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.test_sample_management.model.TestResult;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByTestID(String testID);
    List<TestResult> findByResultStatus(String resultStatus);
    List<TestResult> findByResultStatusIgnoreCase(String resultStatus);
}
