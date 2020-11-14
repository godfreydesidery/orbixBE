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
import com.example.orbix_web.models.Voidd;
import com.example.orbix_web.repositories.VoidRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class VoidServiceController {

    @Autowired
    VoidRepository voiddRepository;
    
    // Get All Voidds
    @GetMapping("/voidds")
    public List<Voidd> getAllVoidds() {
        return voiddRepository.findAll();
    }

    // Create a new Voidd
    @PostMapping(value="/voidds")
    @ResponseBody
    public Voidd createVoidd(@Valid @RequestBody Voidd voidd) {
        return voiddRepository.save(voidd);
    }

    // Get a Single Voidd
    @GetMapping("/voidds/{id}")
    public Voidd getVoiddById(@PathVariable(value = "id") Long voiddId) {
        return voiddRepository.findById(voiddId)
                .orElseThrow(() -> new ResourceNotFoundException("Voidd", "id", voiddId));
    }

    // Update a Voidd
    @PutMapping("/voidds/{id}")
    public Voidd updateNote(@PathVariable(value = "id") Long voiddId,
                                            @Valid @RequestBody Voidd voiddDetails) {

        Voidd voidd = voiddRepository.findById(voiddId)
                .orElseThrow(() -> new ResourceNotFoundException("Voidd", "id", voiddId));

        

        Voidd updatedVoidd = voiddRepository.save(voidd);
        return updatedVoidd;
    }

    // Delete a Voidd
    @DeleteMapping("/voidds/{id}")
    public ResponseEntity<?> deleteVoidd(@PathVariable(value = "id") Long voiddId) {
    	Voidd voidd = voiddRepository.findById(voiddId)
                .orElseThrow(() -> new ResourceNotFoundException("Voidd", "id", voiddId));

    	voiddRepository.delete(voidd);

        return ResponseEntity.ok().build();
    }
}
