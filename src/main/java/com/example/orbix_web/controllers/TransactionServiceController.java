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
import com.example.orbix_web.models.Transaction;
import com.example.orbix_web.repositories.TransactionRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class TransactionServiceController {

    @Autowired
    TransactionRepository transactionRepository;
    
    // Get All Transactions
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Create a new Transaction
    @PostMapping(value="/transactions")
    @ResponseBody
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Get a Single Transaction
    @GetMapping("/transactions/{id}")
    public Transaction getTransactionById(@PathVariable(value = "id") Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
    }

    // Update a Transaction
    @PutMapping("/transactions/{id}")
    public Transaction updateNote(@PathVariable(value = "id") Long transactionId,
                                            @Valid @RequestBody Transaction transactionDetails) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));

        

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return updatedTransaction;
    }

    // Delete a Transaction
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(value = "id") Long transactionId) {
    	Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));

    	transactionRepository.delete(transaction);

        return ResponseEntity.ok().build();
    }
}
