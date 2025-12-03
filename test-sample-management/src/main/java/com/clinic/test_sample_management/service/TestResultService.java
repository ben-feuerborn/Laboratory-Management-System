package com.clinic.test_sample_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.test_sample_management.model.TestResult;
import com.clinic.test_sample_management.repository.TestResultRepository;

@Service
public class TestResultService {
    @Autowired
    private TestResultRepository repository;

    public TestResult addTestResult(TestResult result) {
        // Ensure DB-required `reportid` column is populated. If the form used `testID`
        // but the DB requires `reportid`, fall back to testID when reportID is missing.
        if (result.getReportID() == null || result.getReportID().isBlank()) {
            result.setReportID(result.getTestID() != null ? result.getTestID() : java.util.UUID.randomUUID().toString());
        }
        return repository.save(result);
    }

    public void deleteTestResult(Long id) {
        repository.deleteById(id);
    }

    public List<TestResult> getAllResults() {
        return repository.findAll();
    }

    public List<TestResult> getTestResultsByID(String testID) {
        return repository.findByTestID(testID);
    }

    public List<TestResult> getPendingResults() {
        // Use case-insensitive lookup so values like "Pending" / "PENDING" / "pending" are all returned
        return repository.findByResultStatusIgnoreCase("Pending");
    }

    public void approveResult(Long id) {
        TestResult r = repository.findById(id).orElseThrow();
        r.setResultStatus("APPROVED");
        repository.save(r);
    }
        
}
