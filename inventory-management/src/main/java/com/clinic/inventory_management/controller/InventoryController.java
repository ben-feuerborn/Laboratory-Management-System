package com.clinic.inventory_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.inventory_management.model.InventoryItem;
import com.clinic.inventory_management.model.TestType;
import com.clinic.inventory_management.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;
    
    @PostMapping
    public ResponseEntity<InventoryItem> addItem(@RequestBody @Valid InventoryItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addItem(item));
    }

    @GetMapping
    public List<InventoryItem> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @GetMapping("/testType/{testType}")
    public List<InventoryItem> getByTestType(@PathVariable TestType testType) {
        return service.getByTestType(testType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(
            @PathVariable Long id,
            @RequestBody InventoryItem updatedItem) {
        return ResponseEntity.ok(service.updateItem(id, updatedItem));
    }

    @PutMapping("/{id}/quantity/{qty}")
    public InventoryItem updateQuantity(@PathVariable Long id, @PathVariable int qty) {
        return service.updateQuantity(id, qty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
