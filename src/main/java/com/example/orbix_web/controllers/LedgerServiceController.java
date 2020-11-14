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
import com.example.orbix_web.models.Ledger;
import com.example.orbix_web.repositories.LedgerRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class LedgerServiceController {

    @Autowired
    LedgerRepository ledgerRepository;
    
    // Get All Ledgers
    @GetMapping("/ledgers")
    public List<Ledger> getAllLedgers() {
        return ledgerRepository.findAll();
    }

    // Create a new Ledger
    @PostMapping(value="/ledgers")
    @ResponseBody
    public Ledger createLedger(@Valid @RequestBody Ledger ledger) {
        return ledgerRepository.save(ledger);
    }

    // Get a Single Ledger
    @GetMapping("/ledgers/{id}")
    public Ledger getLedgerById(@PathVariable(value = "id") Long ledgerId) {
        return ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ledger", "id", ledgerId));
    }

    // Update a Ledger
    @PutMapping("/ledgers/{id}")
    public Ledger updateNote(@PathVariable(value = "id") Long ledgerId,
                                            @Valid @RequestBody Ledger ledgerDetails) {

        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ledger", "id", ledgerId));

        

        Ledger updatedLedger = ledgerRepository.save(ledger);
        return updatedLedger;
    }

    // Delete a Ledger
    @DeleteMapping("/ledgers/{id}")
    public ResponseEntity<?> deleteLedger(@PathVariable(value = "id") Long ledgerId) {
    	Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ledger", "id", ledgerId));

    	ledgerRepository.delete(ledger);

        return ResponseEntity.ok().build();
    }
}

