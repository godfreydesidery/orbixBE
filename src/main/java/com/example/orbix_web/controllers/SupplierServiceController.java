/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.SupplierRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierServiceController {

    @Autowired
    SupplierRepository supplierRepository;
    
    // Get All Suppliers
    @GetMapping("/suppliers")
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    /**
     * 
     * @return array of suppliers' names
     */
    @GetMapping(value="/suppliers/supplier_names")
    public Iterable<Supplier> getAllSupplierByNames() {
        return supplierRepository.getSupplierNames();
    }

    // Create a new Supplier
    @PostMapping(value="/suppliers")
    @ResponseBody
    public Supplier createSupplier(@Valid @RequestBody Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    // Get a Single Supplier
    @GetMapping("/suppliers/{id}")
    public Supplier getSupplierById(@PathVariable(value = "id") Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", supplierId));
    }
    // Get a Single Supplier by name
    @GetMapping("/suppliers/supplier_name={supplier_name}")
    public Supplier getSupplierBySupplierName(@PathVariable(value = "supplier_name") String supplierName) {
    	
        return supplierRepository.findBySupplierName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "supplier_name", supplierName));
    }
    
    // Get a Single Supplier by code
    @GetMapping("/suppliers/supplier_code={supplier_code}")
    public Supplier getSupplierBySupplierCode(@PathVariable(value = "supplier_code") String supplierCode) {
    	
        return supplierRepository.findBySupplierCode(supplierCode)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "supplier_code", supplierCode));
    }

    // Update a Supplier
    @PutMapping("/suppliers/{id}")
    public Supplier updateNote(@PathVariable(value = "id") Long supplierId,
                                            @Valid @RequestBody Supplier supplierDetails) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", supplierId));

        supplier = supplierDetails;

        Supplier updatedSupplier = supplierRepository.save(supplier);
        return updatedSupplier;
    }

    // Delete a Supplier
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable(value = "id") Long supplierId) {
    	Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", supplierId));

    	supplierRepository.delete(supplier);

        return ResponseEntity.ok().build();
    }
}
