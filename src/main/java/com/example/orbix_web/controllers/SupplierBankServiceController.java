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
import com.example.orbix_web.models.SupplierBank;
import com.example.orbix_web.repositories.SupplierBankRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class SupplierBankServiceController {

    @Autowired
    SupplierBankRepository supplierBankRepository;
    
    // Get All SupplierBanks
    @GetMapping("/supplier_banks")
    public List<SupplierBank> getAllSupplierBanks() {
        return supplierBankRepository.findAll();
    }

    // Create a new SupplierBank
    @PostMapping(value="/supplier_banks")
    @ResponseBody
    public SupplierBank createSupplierBank(@Valid @RequestBody SupplierBank supplierBank) {
        return supplierBankRepository.save(supplierBank);
    }

    // Get a Single SupplierBank
    @GetMapping("/supplier_banks/{id}")
    public SupplierBank getSupplierBankById(@PathVariable(value = "id") Long supplierBankId) {
        return supplierBankRepository.findById(supplierBankId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierBank", "id", supplierBankId));
    }

    // Update a SupplierBank
    @PutMapping("/supplier_banks/{id}")
    public SupplierBank updateNote(@PathVariable(value = "id") Long supplierBankId,
                                            @Valid @RequestBody SupplierBank supplierBankDetails) {

        SupplierBank supplierBank = supplierBankRepository.findById(supplierBankId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierBank", "id", supplierBankId));

        

        SupplierBank updatedSupplierBank = supplierBankRepository.save(supplierBank);
        return updatedSupplierBank;
    }

    // Delete a SupplierBank
    @DeleteMapping("/supplier_banks/{id}")
    public ResponseEntity<?> deleteSupplierBank(@PathVariable(value = "id") Long supplierBankId) {
    	SupplierBank supplierBank = supplierBankRepository.findById(supplierBankId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierBank", "id", supplierBankId));

    	supplierBankRepository.delete(supplierBank);

        return ResponseEntity.ok().build();
    }
}

