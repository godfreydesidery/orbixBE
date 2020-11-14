/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Inventory;
import com.example.orbix_web.repositories.InventoryRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class InventoryServiceController {

    @Autowired
    InventoryRepository inventoryRepository;
    
    // Get All Inventory
    @GetMapping("/inventory")
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // Create a new Inventory
    @PostMapping(value="/inventory")
    @ResponseBody
    public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // Get a Single Inventory
    @GetMapping("/inventory/{id}")
    public Inventory getInventoryById(@PathVariable(value = "id") Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", inventoryId));
    }

    // Update a Inventory
    @PutMapping("/inventory/{id}")
    public Inventory updateNote(@PathVariable(value = "id") Long inventoryId,
                                            @Valid @RequestBody Inventory inventoryDetails) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", inventoryId));

        

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return updatedInventory;
    }

    // Delete a Inventory
    @DeleteMapping("/inventory/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable(value = "id") Long inventoryId) {
    	Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", inventoryId));

    	inventoryRepository.delete(inventory);

        return ResponseEntity.ok().build();
    }
}
