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
import com.example.orbix_web.models.SalesReceipt;
import com.example.orbix_web.repositories.ReceiptRepository;
import com.example.orbix_web.repositories.SalesReceiptRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class SalesReceiptServiceController {

    @Autowired
    SalesReceiptRepository salesReceiptRepository;
    
  /*  // Get All Receipts
    @GetMapping("/sales_receipts")
    public List<SalesReceipt> getAllReceipts() {
        return salesReceiptRepository.findAll();
    }

    // Create a new SalesReceipt
    @PostMapping(value="/sales_receipts")
    @ResponseBody
    public SalesReceipt createReceipt(@Valid @RequestBody SalesReceipt receipt) {
        return salesReceiptRepository.save(receipt);
    }

    // Get a Single SalesReceipt
    @GetMapping("/sales_receipts/{id}")
    public SalesReceipt getReceiptById(@PathVariable(value = "id") Long receiptId) {
        return salesReceiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("SalesReceipt", "id", receiptId));
    }

    

    // Delete a SalesReceipt
    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<?> deleteReceipt(@PathVariable(value = "id") Long receiptId) {
    	SalesReceipt receipt = salesReceiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("SalesReceipt", "id", receiptId));

    	salesReceiptRepository.delete(receipt);

        return ResponseEntity.ok().build();
    }
    */
}

