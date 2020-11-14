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
import com.example.orbix_web.models.Receipt;
import com.example.orbix_web.repositories.ReceiptRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class ReceiptServiceController {

    @Autowired
    ReceiptRepository receiptRepository;
    
    // Get All Receipts
    @GetMapping("/receipts")
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    // Create a new Receipt
    @PostMapping(value="/receipts")
    @ResponseBody
    public Receipt createReceipt(@Valid @RequestBody Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    // Get a Single Receipt
    @GetMapping("/receipts/{id}")
    public Receipt getReceiptById(@PathVariable(value = "id") Long receiptId) {
        return receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt", "id", receiptId));
    }

    // Update a Receipt
    @PutMapping("/receipts/{id}")
    public Receipt updateNote(@PathVariable(value = "id") Long receiptId,
                                            @Valid @RequestBody Receipt receiptDetails) {

        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt", "id", receiptId));

        

        Receipt updatedReceipt = receiptRepository.save(receipt);
        return updatedReceipt;
    }

    // Delete a Receipt
    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<?> deleteReceipt(@PathVariable(value = "id") Long receiptId) {
    	Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt", "id", receiptId));

    	receiptRepository.delete(receipt);

        return ResponseEntity.ok().build();
    }
}

