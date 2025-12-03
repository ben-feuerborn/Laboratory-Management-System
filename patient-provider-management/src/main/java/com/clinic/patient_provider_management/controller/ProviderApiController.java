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

import com.clinic.patient_provider_management.model.Provider;
import com.clinic.patient_provider_management.service.ProviderService;

@RestController
@RequestMapping("/api/providers")
public class ProviderApiController {
    @Autowired private ProviderService service;
    @GetMapping public List<Provider> all(){ return service.all(); }
    @PostMapping public ResponseEntity<Provider> create(@RequestBody Provider p){ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(p)); }
}

