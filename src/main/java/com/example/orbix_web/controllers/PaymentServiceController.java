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
import com.example.orbix_web.models.Payment;
import com.example.orbix_web.repositories.PaymentRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class PaymentServiceController {

    @Autowired
    PaymentRepository paymentRepository;
    
    // Get All Payments
    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Create a new Payment
    @PostMapping(value="/payments")
    @ResponseBody
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    // Get a Single Payment
    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable(value = "id") Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
    }

    // Update a Payment
    @PutMapping("/payments/{id}")
    public Payment updateNote(@PathVariable(value = "id") Long paymentId,
                                            @Valid @RequestBody Payment paymentDetails) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

        

        Payment updatedPayment = paymentRepository.save(payment);
        return updatedPayment;
    }

    // Delete a Payment
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable(value = "id") Long paymentId) {
    	Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

    	paymentRepository.delete(payment);

        return ResponseEntity.ok().build();
    }
}

