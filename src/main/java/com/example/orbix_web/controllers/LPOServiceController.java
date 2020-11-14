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
import com.example.orbix_web.models.LPO;
import com.example.orbix_web.repositories.LPORepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class LPOServiceController {

    @Autowired
    LPORepository localPurchaseOrderRepository;
    
    // Get All LPOs
    @GetMapping("/local_purchase_orders")
    public List<LPO> getAllLPOs() {
        return localPurchaseOrderRepository.findAll();
    }

    // Create a new LPO
    @PostMapping(value="/local_purchase_orders")
    @ResponseBody
    public LPO createLPO(@Valid @RequestBody LPO localPurchaseOrder) {
        return localPurchaseOrderRepository.save(localPurchaseOrder);
    }

    // Get a Single LPO
    @GetMapping("/local_purchase_orders/{id}")
    public LPO getLPOById(@PathVariable(value = "id") Long localPurchaseOrderId) {
        return localPurchaseOrderRepository.findById(localPurchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("LPO", "id", localPurchaseOrderId));
    }

    // Update a LPO
    @PutMapping("/local_purchase_orders/{id}")
    public LPO updateNote(@PathVariable(value = "id") Long localPurchaseOrderId,
                                            @Valid @RequestBody LPO localPurchaseOrderDetails) {

        LPO localPurchaseOrder = localPurchaseOrderRepository.findById(localPurchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("LPO", "id", localPurchaseOrderId));

        

        LPO updatedLPO = localPurchaseOrderRepository.save(localPurchaseOrder);
        return updatedLPO;
    }

    // Delete a LPO
    @DeleteMapping("/local_purchase_orders/{id}")
    public ResponseEntity<?> deleteLPO(@PathVariable(value = "id") Long localPurchaseOrderId) {
    	LPO localPurchaseOrder = localPurchaseOrderRepository.findById(localPurchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("LPO", "id", localPurchaseOrderId));

    	localPurchaseOrderRepository.delete(localPurchaseOrder);

        return ResponseEntity.ok().build();
    }
}
