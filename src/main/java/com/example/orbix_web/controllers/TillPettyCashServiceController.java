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
import com.example.orbix_web.models.TillPettyCash;
import com.example.orbix_web.repositories.TillPettyCashRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class TillPettyCashServiceController {

    @Autowired
    TillPettyCashRepository tillPettyCashRepository;
    
    // Get All TillPettyCashs
    @GetMapping("/till_petty_cash")
    public List<TillPettyCash> getAllTillPettyCashs() {
        return tillPettyCashRepository.findAll();
    }

    // Create a new TillPettyCash
    @PostMapping(value="/till_petty_cash")
    @ResponseBody
    public TillPettyCash createTillPettyCash(@Valid @RequestBody TillPettyCash tillPettyCash) {
        return tillPettyCashRepository.save(tillPettyCash);
    }

    // Get a Single TillPettyCash
    @GetMapping("/till_petty_cash/{id}")
    public TillPettyCash getTillPettyCashById(@PathVariable(value = "id") Long tillPettyCashId) {
        return tillPettyCashRepository.findById(tillPettyCashId)
                .orElseThrow(() -> new ResourceNotFoundException("TillPettyCash", "id", tillPettyCashId));
    }

    // Update a TillPettyCash
    @PutMapping("/till_petty_cash/{id}")
    public TillPettyCash updateNote(@PathVariable(value = "id") Long tillPettyCashId,
                                            @Valid @RequestBody TillPettyCash tillPettyCashDetails) {

        TillPettyCash tillPettyCash = tillPettyCashRepository.findById(tillPettyCashId)
                .orElseThrow(() -> new ResourceNotFoundException("TillPettyCash", "id", tillPettyCashId));

        

        TillPettyCash updatedTillPettyCash = tillPettyCashRepository.save(tillPettyCash);
        return updatedTillPettyCash;
    }

    // Delete a TillPettyCash
    @DeleteMapping("/till_petty_cash/{id}")
    public ResponseEntity<?> deleteTillPettyCash(@PathVariable(value = "id") Long tillPettyCashId) {
    	TillPettyCash tillPettyCash = tillPettyCashRepository.findById(tillPettyCashId)
                .orElseThrow(() -> new ResourceNotFoundException("TillPettyCash", "id", tillPettyCashId));

    	tillPettyCashRepository.delete(tillPettyCash);

        return ResponseEntity.ok().build();
    }
}

