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
import com.example.orbix_web.models.TillFloat;
import com.example.orbix_web.repositories.TillFloatRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class TillFloatServiceController {

    @Autowired
    TillFloatRepository tillFloatRepository;
    
    // Get All TillFloats
    @GetMapping("/till_float")
    public List<TillFloat> getAllTillFloats() {
        return tillFloatRepository.findAll();
    }

    // Create a new TillFloat
    @PostMapping(value="/till_float")
    @ResponseBody
    public TillFloat createTillFloat(@Valid @RequestBody TillFloat tillFloat) {
        return tillFloatRepository.save(tillFloat);
    }

    // Get a Single TillFloat
    @GetMapping("/till_float/{id}")
    public TillFloat getTillFloatById(@PathVariable(value = "id") Long tillFloatId) {
        return tillFloatRepository.findById(tillFloatId)
                .orElseThrow(() -> new ResourceNotFoundException("TillFloat", "id", tillFloatId));
    }

    // Update a TillFloat
    @PutMapping("/till_float/{id}")
    public TillFloat updateNote(@PathVariable(value = "id") Long tillFloatId,
                                            @Valid @RequestBody TillFloat tillFloatDetails) {

        TillFloat tillFloat = tillFloatRepository.findById(tillFloatId)
                .orElseThrow(() -> new ResourceNotFoundException("TillFloat", "id", tillFloatId));

        

        TillFloat updatedTillFloat = tillFloatRepository.save(tillFloat);
        return updatedTillFloat;
    }

    // Delete a TillFloat
    @DeleteMapping("/till_float/{id}")
    public ResponseEntity<?> deleteTillFloat(@PathVariable(value = "id") Long tillFloatId) {
    	TillFloat tillFloat = tillFloatRepository.findById(tillFloatId)
                .orElseThrow(() -> new ResourceNotFoundException("TillFloat", "id", tillFloatId));

    	tillFloatRepository.delete(tillFloat);

        return ResponseEntity.ok().build();
    }
}