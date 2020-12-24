/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.NotFoundException;
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
    @RequestMapping(method = RequestMethod.GET, value = "/lpos")
    public List<Lpo> getAllLpos() {
        return lpoRepository.findAll();
    }
    // Create a new LPO
    @RequestMapping(method = RequestMethod.POST, value = "/lpos")
    @ResponseBody
    public Lpo createLpo(@Valid @RequestBody Lpo lpo) {
    	LocalDate lpoDate = LocalDate.now();
    	Supplier supplier;
    	try {
    		String supplierName = (lpo.getSupplier()).getSupplierName();
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
    		supplier.setSupplierName(supplierName);
	    	supplierRepository.save(supplier);
	    	lpo.setSupplier(supplier);
    	}catch(Exception e) {
    		lpo.setSupplier(null);
    	}
    	lpo.setLpoDate(lpoDate);
    	lpo.setStatus("PENDING");
        return lpoRepository.save(lpo);
    }
    // Get a Single LPO
    @RequestMapping(method = RequestMethod.GET, value = "/lpos/{id}")
    public Lpo getLpoById(@PathVariable(value = "id") Long lpoId) {
        return lpoRepository.findById(lpoId)
                .orElseThrow(() -> new NotFoundException("LPO not found"));
    }
    
    // Get a Single LPO by lpo no
    @RequestMapping(method = RequestMethod.GET, value = "/lpos/lpo_no={lpo_no}")
    public Lpo getLpoByLpoNo(@PathVariable(value = "lpo_no") String lpoNo) {
        return lpoRepository.findByLpoNo(lpoNo)
                .orElseThrow(() -> new NotFoundException("LPO not found"));
    }
    // Update a LPO
    @RequestMapping(method = RequestMethod.PUT, value = "/lpos/{id}", produces = "text/html")
    public ResponseEntity<Object> updateLpo(@PathVariable(value = "id") Long lpoId,
                                            @Valid @RequestBody Lpo lpoDetails) {
    	Lpo lpo = lpoRepository.findById(lpoId)
                .orElseThrow(() -> new NotFoundException("LPO not found"));
    	lpo = lpoDetails;
    	try {
    		lpoRepository.save(lpo);
    		return new ResponseEntity<>("LPO updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not update LPO, "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }
    //Approve LPO
    @RequestMapping(method = RequestMethod.PUT, value = "/lpos/approve/{id}", produces = "text/html")
    public ResponseEntity<Object> approveLpo(@PathVariable(value = "id") Long lpoId) {
		Lpo lpo = lpoRepository.findById(lpoId)
		.orElseThrow(() -> new NotFoundException("LPO not found"));
		if(lpo.getStatus().equals("PENDING")) {
			lpo.setStatus("APPROVED");
			lpoRepository.save(lpo);
			return new ResponseEntity<Object>("LPO approved", HttpStatus.OK);
		}else {
			throw new InvalidOperationException("Could not approve, Only pending LPO can be approved");
		}
	}
    //Print LPO
    @RequestMapping(method = RequestMethod.PUT, value = "/lpos/print/{id}", produces = "text/html")
    public ResponseEntity<Object> printLpo(@PathVariable(value = "id") Long lpoId) {
		Lpo lpo = lpoRepository.findById(lpoId)
		.orElseThrow(() -> new NotFoundException("LPO not found"));
		String status = lpo.getStatus();
		if(status.equals("APPROVED")) {
			lpo.setStatus("PRINTED");
			lpoRepository.save(lpo);
			return new ResponseEntity<Object>("LPO printed successifully.", HttpStatus.OK);
		}else if(status.equals("PRINTED") || status.equals("REPRINTED")) {
			lpo.setStatus("REPRINTED");
			lpoRepository.save(lpo);
			return new ResponseEntity<Object>("LPO reprinted successifully.", HttpStatus.OK);
		}else {
			throw new InvalidOperationException("Could not approve, Only approved/Printed/Reprinted LPO can be printed");
		}
	}
    //Cancel LPO
    @RequestMapping(method = RequestMethod.PUT, value = "/lpos/cancel/{id}", produces = "text/html")
    public ResponseEntity<Object> cancelLpo(@PathVariable(value = "id") Long lpoId) {
		Lpo lpo = lpoRepository.findById(lpoId)
		.orElseThrow(() -> new NotFoundException("LPO not found"));
		String status = lpo.getStatus();
		if(status.equals("PENDING") || status.equals("APPROVED")) {
			lpo.setStatus("CANCELLED");
			lpoRepository.save(lpo);
			return new ResponseEntity<Object>("LPO cancelled.", HttpStatus.OK);
		}else if(status.equals("CANCELLED")) {
			throw new InvalidOperationException("Could not cancel, LPO already canceled.");
		}else if(status.equals("PRINTED") || status.equals("REPRINTED")) {
			throw new InvalidOperationException("Can not cancel a printed LPO.");			
		}else {
			throw new InvalidOperationException("Could not cancel, status unknown");
		}
	}
    // Delete a LPO
    @RequestMapping(method = RequestMethod.DELETE, value = "/lpos/{id}", produces = "text/html")
    public ResponseEntity<?> deleteLPO(@PathVariable(value = "id") Long lpoId) {
    	Lpo lpo = lpoRepository.findById(lpoId)
                .orElseThrow(() -> new NotFoundException("LPO not found"));
    	try {
    		String status = lpo.getStatus();
    		if(status.equals("PRINTED") || status.equals("REPRINTED") || status.equals("COMPLETED")) {
    			throw new InvalidOperationException("Could not delete, LPO already printed. Only NEW, PENDING, APPROVED  and ARCHIVED LPOs can be deleted.");
    		}else {
    			lpoRepository.delete(lpo);
    			return new ResponseEntity<>("LPO deleted successifully.", HttpStatus.OK);
    		}
    	}catch(Exception ex) {
    		throw new InvalidOperationException("Could not delete LPO: "+ex.getMessage());
    	}    	
    }
}
