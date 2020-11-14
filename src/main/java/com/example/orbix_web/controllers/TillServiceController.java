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
import com.example.orbix_web.models.Till;
import com.example.orbix_web.repositories.TillRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class TillServiceController {

    @Autowired
    TillRepository tillRepository;
    
    // Get All Tills
    @GetMapping("/tills")
    public List<Till> getAllTills() {
        return tillRepository.findAll();
    }

    // Create a new Till
    @PostMapping(value="/tills")
    @ResponseBody
    public Till createTill(@Valid @RequestBody Till till) {
        return tillRepository.save(till);
    }

    // Get a Single Till
    @GetMapping("/tills/{id}")
    public Till getTillById(@PathVariable(value = "id") Long tillId) {
        return tillRepository.findById(tillId)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "id", tillId));
    }

    // Update a Till
    @PutMapping("/tills/{id}")
    public Till updateNote(@PathVariable(value = "id") Long tillId,
                                            @Valid @RequestBody Till tillDetails) {

        Till till = tillRepository.findById(tillId)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "id", tillId));

        

        Till updatedTill = tillRepository.save(till);
        return updatedTill;
    }

    // Delete a Till
    @DeleteMapping("/tills/{id}")
    public ResponseEntity<?> deleteTill(@PathVariable(value = "id") Long tillId) {
    	Till till = tillRepository.findById(tillId)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "id", tillId));

    	tillRepository.delete(till);

        return ResponseEntity.ok().build();
    }
}
