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

import com.clinic.patient_provider_management.model.Medication;
import com.clinic.patient_provider_management.service.MedicationService;

@RestController
@RequestMapping("/api/medications")
public class MedicationApiController {
    @Autowired private MedicationService service;
    @GetMapping public List<Medication> all(){ return service.all(); }
    @PostMapping public ResponseEntity<Medication> create(@RequestBody Medication m){ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(m)); }
}

