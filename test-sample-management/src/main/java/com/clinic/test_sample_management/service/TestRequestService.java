package com.clinic.test_sample_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.clinic.test_sample_management.model.TestRequest;
import com.clinic.test_sample_management.repository.TestRequestRepository;

@Service
public class TestRequestService {
    private final TestRequestRepository repo;

    public TestRequestService(TestRequestRepository repo) {
        this.repo = repo;
    }

    public TestRequest save(TestRequest tr) {
        return repo.save(tr);
    }

    public List<TestRequest> findAll() {
        return repo.findAll();
    }

    public Optional<TestRequest> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<TestRequest> findByReportId(String reportId) {
        return repo.findByReportId(reportId);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
