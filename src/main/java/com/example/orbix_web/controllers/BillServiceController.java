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
import com.example.orbix_web.models.Bill;
import com.example.orbix_web.repositories.BillRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class BillServiceController {

    @Autowired
    BillRepository billRepository;
    
    // Get All Bills
    @GetMapping("/bills")
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // Create a new Bill
    @PostMapping(value="/bills")
    @ResponseBody
    public Bill createBill(@Valid @RequestBody Bill bill) {
        return billRepository.save(bill);
    }

    // Get a Single Bill
    @GetMapping("/bills/{id}")
    public Bill getBillById(@PathVariable(value = "id") Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));
    }

    // Update a Bill
    @PutMapping("/bills/{id}")
    public Bill updateNote(@PathVariable(value = "id") Long billId,
                                            @Valid @RequestBody Bill billDetails) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));

        

        Bill updatedBill = billRepository.save(bill);
        return updatedBill;
    }

    // Delete a Bill
    @DeleteMapping("/bills/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable(value = "id") Long billId) {
    	Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));

    	billRepository.delete(bill);

        return ResponseEntity.ok().build();
    }
}
