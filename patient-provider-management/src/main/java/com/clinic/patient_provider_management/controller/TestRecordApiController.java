package com.clinic.patient_provider_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.patient_provider_management.model.TestRecord;
import com.clinic.patient_provider_management.service.TestRecordService;

@RestController
@RequestMapping("/api/tests")
public class TestRecordApiController {
    @Autowired private TestRecordService service;
    @GetMapping public List<TestRecord> all(){ return service.all(); }
    @PostMapping public ResponseEntity<TestRecord> create(@RequestBody TestRecord t){ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(t)); }
}

