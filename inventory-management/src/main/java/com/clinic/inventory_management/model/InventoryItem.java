package com.clinic.inventory_management.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TestType testType;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    private LocalDate expirationDate;

    private LocalDate dateEntered;

    @PrePersist
    public void prePersist() {
        this.dateEntered = LocalDate.now();
    }

    public InventoryItem(TestType testType, String name, int quantity, LocalDate expirationDate,
            LocalDate dateEntered) {
        this.testType = testType;
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.dateEntered = dateEntered;
    }

}
