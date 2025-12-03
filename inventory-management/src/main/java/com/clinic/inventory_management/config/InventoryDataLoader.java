package com.clinic.inventory_management.config;

import com.clinic.inventory_management.model.InventoryItem;
import com.clinic.inventory_management.model.TestType;
import com.clinic.inventory_management.repository.InventoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.time.LocalDate;

@Configuration
public class InventoryDataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    public InventoryDataLoader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Skip loading if DB already has data
        if (inventoryRepository.count() > 0) return;
        
        var resource = new ClassPathResource("data/inventory-data.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(new InputStreamReader(resource.getInputStream()));

        for (CSVRecord record : records) {
            TestType testType = TestType.valueOf(record.get("Test Type").toUpperCase().replace(" ", "_"));
            String name = record.get("Name");
            int quantity = Integer.parseInt(record.get("Quantity"));
            LocalDate expirationDate = LocalDate.parse(record.get("Expiration Date"));
            LocalDate dateEntered = LocalDate.parse(record.get("Date Entered"));

            InventoryItem item = new InventoryItem(testType, name, quantity, expirationDate, dateEntered);
            inventoryRepository.save(item);

        }
    }
}