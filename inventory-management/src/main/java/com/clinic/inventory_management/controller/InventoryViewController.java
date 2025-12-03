package com.clinic.inventory_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinic.inventory_management.model.InventoryItem;
import com.clinic.inventory_management.model.TestType;
import com.clinic.inventory_management.service.InventoryService;

@Controller
@RequestMapping
public class InventoryViewController {

    @Autowired
    private InventoryService service;

    @GetMapping({"", "/"})
    public String viewInventory(Model model) {
        model.addAttribute("items", service.getAllItems());
        return "inventory-list"; // HTML file name
    }

    @GetMapping("/add")
    public String addItemForm(Model model) {
        model.addAttribute("item", new InventoryItem());
        model.addAttribute("testTypes", TestType.values());
        return "add-item";
    }

    @PostMapping("/add")
    public String submitNewItem(@ModelAttribute InventoryItem item) {
        service.addItem(item);
        return "redirect:/inventory";
    }

    @GetMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        InventoryItem item = service.getById(id)
                .orElseThrow(() -> new RuntimeException("Item not found")); // or custom exception
        model.addAttribute("item", item);
        model.addAttribute("testTypes", TestType.values());
        return "edit-item";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute InventoryItem item) {
        service.updateItem(id, item);
        return "redirect:/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return "redirect:/inventory";
    }

}
