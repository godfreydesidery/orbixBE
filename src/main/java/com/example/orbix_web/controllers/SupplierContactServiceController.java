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
import com.example.orbix_web.models.SupplierContact;
import com.example.orbix_web.repositories.SupplierContactRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class SupplierContactServiceController {

    @Autowired
    SupplierContactRepository supplierContactRepository;
    
    // Get All SupplierContacts
    @GetMapping("/supplier_contacts")
    public List<SupplierContact> getAllSupplierContacts() {
        return supplierContactRepository.findAll();
    }

    // Create a new SupplierContact
    @PostMapping(value="/supplier_contacts")
    @ResponseBody
    public SupplierContact createSupplierContact(@Valid @RequestBody SupplierContact supplierContact) {
        return supplierContactRepository.save(supplierContact);
    }

    // Get a Single SupplierContact
    @GetMapping("/supplier_contacts/{id}")
    public SupplierContact getSupplierContactById(@PathVariable(value = "id") Long supplierContactId) {
        return supplierContactRepository.findById(supplierContactId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierContact", "id", supplierContactId));
    }

    // Update a SupplierContact
    @PutMapping("/supplier_contacts/{id}")
    public SupplierContact updateNote(@PathVariable(value = "id") Long supplierContactId,
                                            @Valid @RequestBody SupplierContact supplierContactDetails) {

        SupplierContact supplierContact = supplierContactRepository.findById(supplierContactId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierContact", "id", supplierContactId));

        

        SupplierContact updatedSupplierContact = supplierContactRepository.save(supplierContact);
        return updatedSupplierContact;
    }

    // Delete a SupplierContact
    @DeleteMapping("/supplier_contacts/{id}")
    public ResponseEntity<?> deleteSupplierContact(@PathVariable(value = "id") Long supplierContactId) {
    	SupplierContact supplierContact = supplierContactRepository.findById(supplierContactId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierContact", "id", supplierContactId));

    	supplierContactRepository.delete(supplierContact);

        return ResponseEntity.ok().build();
    }
}
