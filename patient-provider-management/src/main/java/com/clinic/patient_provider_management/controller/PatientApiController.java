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

import com.clinic.patient_provider_management.model.Patient;
import com.clinic.patient_provider_management.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientApiController {

    @Autowired
    private PatientService service;

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody @Valid Patient patient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(patient));
    }

    @GetMapping
    public List<Patient> all() {
        return service.getAll();
    }
}

