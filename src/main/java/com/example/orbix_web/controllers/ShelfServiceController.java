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
import com.example.orbix_web.models.Shelf;
import com.example.orbix_web.repositories.ShelfRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class ShelfServiceController {

    @Autowired
    ShelfRepository shelfRepository;
    
    // Get All Shelfs
    @GetMapping("/shelfs")
    public List<Shelf> getAllShelfs() {
        return shelfRepository.findAll();
    }

    // Create a new Shelf
    @PostMapping(value="/shelfs")
    @ResponseBody
    public Shelf createShelf(@Valid @RequestBody Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    // Get a Single Shelf
    @GetMapping("/shelfs/{id}")
    public Shelf getShelfById(@PathVariable(value = "id") Long shelfId) {
        return shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResourceNotFoundException("Shelf", "id", shelfId));
    }

    // Update a Shelf
    @PutMapping("/shelfs/{id}")
    public Shelf updateNote(@PathVariable(value = "id") Long shelfId,
                                            @Valid @RequestBody Shelf shelfDetails) {

        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResourceNotFoundException("Shelf", "id", shelfId));

        

        Shelf updatedShelf = shelfRepository.save(shelf);
        return updatedShelf;
    }

    // Delete a Shelf
    @DeleteMapping("/shelfs/{id}")
    public ResponseEntity<?> deleteShelf(@PathVariable(value = "id") Long shelfId) {
    	Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResourceNotFoundException("Shelf", "id", shelfId));

    	shelfRepository.delete(shelf);

        return ResponseEntity.ok().build();
    }
}

