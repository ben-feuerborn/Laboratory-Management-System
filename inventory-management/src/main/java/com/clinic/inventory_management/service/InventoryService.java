package com.clinic.inventory_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.inventory_management.model.InventoryItem;
import com.clinic.inventory_management.model.TestType;
import com.clinic.inventory_management.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public InventoryItem addItem(InventoryItem item) {
        return repository.save(item);
    }

    public List<InventoryItem> getAllItems() {
        return repository.findAll();
    }

    public Optional<InventoryItem> getById(Long id) {
        return repository.findById(id);
    }

    public List<InventoryItem> getByTestType(TestType testType) {
        return repository.findByTestType(testType);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    public InventoryItem updateItem(Long id, InventoryItem updatedItem) {
        InventoryItem existingItem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setName(updatedItem.getName());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setExpirationDate(updatedItem.getExpirationDate());
        existingItem.setTestType(updatedItem.getTestType());

        return repository.save(existingItem);
    }

    public InventoryItem updateQuantity(Long id, int qty) {
        InventoryItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(qty);
        return repository.save(item);
    }
}
