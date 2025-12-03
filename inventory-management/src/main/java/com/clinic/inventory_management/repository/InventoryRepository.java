package com.clinic.inventory_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.inventory_management.model.InventoryItem;
import com.clinic.inventory_management.model.TestType;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByTestType(TestType testType);
}
