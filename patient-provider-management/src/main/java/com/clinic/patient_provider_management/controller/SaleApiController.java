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

import com.clinic.patient_provider_management.model.Sale;
import com.clinic.patient_provider_management.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleApiController {
    @Autowired private SaleService service;
    @GetMapping public List<Sale> all(){ return service.all(); }
    @PostMapping public ResponseEntity<Sale> create(@RequestBody Sale s){ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(s)); }
}

