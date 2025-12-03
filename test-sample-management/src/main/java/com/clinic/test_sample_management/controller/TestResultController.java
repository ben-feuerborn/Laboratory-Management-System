package com.clinic.test_sample_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.test_sample_management.model.TestResult;
import com.clinic.test_sample_management.service.TestResultService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/test-results")
public class TestResultController {

    @Autowired
    private TestResultService service;

    // Create a new test result
    @PostMapping
    public ResponseEntity<TestResult> addTestResult(@RequestBody @Valid TestResult result) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addTestResult(result));
    }

    // Get all test results
    @GetMapping
    public List<TestResult> getAllResults() {
        return service.getAllResults();
    }

    // Get test results by TestID
    @GetMapping("/testID/{testID}")
    public List<TestResult> getByTestID(@PathVariable String testID) {
        return service.getTestResultsByID(testID);
    }

    // Optional: Get one result by numeric ID
    @GetMapping("/{id}")
    public ResponseEntity<TestResult> getById(@PathVariable Long id) {
        return service.getAllResults()
                .stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a test result
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTestResult(@PathVariable Long id) {
        service.deleteTestResult(id);
    }
}
