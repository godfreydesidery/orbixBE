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
import com.example.orbix_web.models.Sale;
import com.example.orbix_web.repositories.SaleRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class SaleServiceController {

    @Autowired
    SaleRepository saleRepository;
    
    // Get All Sales
    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Create a new Sale
    @PostMapping(value="/sales")
    @ResponseBody
    public Sale createSale(@Valid @RequestBody Sale sale) {
        return saleRepository.save(sale);
    }

    // Get a Single Sale
    @GetMapping("/sales/{id}")
    public Sale getSaleById(@PathVariable(value = "id") Long saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", saleId));
    }

    // Update a Sale
    @PutMapping("/sales/{id}")
    public Sale updateNote(@PathVariable(value = "id") Long saleId,
                                            @Valid @RequestBody Sale saleDetails) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", saleId));

        

        Sale updatedSale = saleRepository.save(sale);
        return updatedSale;
    }

    // Delete a Sale
    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable(value = "id") Long saleId) {
    	Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", saleId));

    	saleRepository.delete(sale);

        return ResponseEntity.ok().build();
    }
}
