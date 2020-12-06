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
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.LpoRepository;
import com.example.orbix_web.repositories.SupplierRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LpoServiceController {

    @Autowired
    LpoRepository lpoRepository;
    @Autowired
    SupplierRepository supplierRepository;
    
    // Get All LPOs
    @GetMapping("/lpos")
    public List<Lpo> getAllLpos() {
        return lpoRepository.findAll();
    }

    // Create a new LPO
    @PostMapping(value="/lpos")
    @ResponseBody
    public Lpo createLPO(@Valid @RequestBody Lpo lpo) {
    	
    	Supplier supplier;
    	try {
    		String supplierName = (lpo.getSupplier()).getSupplierName();
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
    		supplier.setSupplierName(supplierName);
	    	supplierRepository.save(supplier);
	    	lpo.setSupplier(supplier);
    	}catch(Exception e) {
    		System.out.println(e.toString());
    		lpo.setSupplier(null);
    	}
        return lpoRepository.save(lpo);
    }

    // Get a Single LPO
    @GetMapping("/lpos/{id}")
    public Lpo getLPOById(@PathVariable(value = "id") Long localPurchaseOrderId) {
        return lpoRepository.findById(localPurchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("LPO", "id", localPurchaseOrderId));
    }

    // Update a LPO
    @PutMapping("/local_purchase_orders/{id}")
    public Lpo updateNote(@PathVariable(value = "id") Long localPurchaseOrderId,
                                            @Valid @RequestBody Lpo localPurchaseOrderDetails) {

    	Lpo localPurchaseOrder = lpoRepository.findById(localPurchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("LPO", "id", localPurchaseOrderId));

        

    	Lpo updatedLPO = lpoRepository.save(localPurchaseOrder);
        return updatedLPO;
    }

    // Delete a LPO
    @DeleteMapping("/local_purchase_orders/{id}")
    public ResponseEntity<?> deleteLPO(@PathVariable(value = "id") Long localPurchaseOrderId) {
    	Lpo localPurchaseOrder = lpoRepository.findById(localPurchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("LPO", "id", localPurchaseOrderId));

    	lpoRepository.delete(localPurchaseOrder);

        return ResponseEntity.ok().build();
    }
}
