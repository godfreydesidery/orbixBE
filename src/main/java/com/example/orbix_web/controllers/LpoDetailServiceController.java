/**
 * 
 */
package com.example.orbix_web.controllers;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.DuplicateEntryException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;
import com.example.orbix_web.repositories.LpoDetailRepository;
import com.example.orbix_web.repositories.LpoRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LpoDetailServiceController {
	EntityManager entityManager;
	@Autowired
    LpoRepository lpoRepository;
	@Autowired
	LpoDetailRepository lpoDetailRepository;
	
	// Create a new Lpo detail
    @RequestMapping(method = RequestMethod.POST, value="/lpo_details", produces = {"text/html","application/json"})
    @ResponseBody
    public LpoDetail createLpoDetail(@Valid @RequestBody LpoDetail lpoDetail) {
    	
    	Lpo lpo;
    	String lpoNo = (lpoDetail.getLpo()).getLpoNo();
    	lpo = lpoRepository.findByLpoNo(lpoNo).get();
	    lpoRepository.save(lpo);	    
    	if(lpoDetailRepository.existsByLpoAndItemCode(lpo, lpoDetail.getItemCode()) == true){
    		throw new DuplicateEntryException("\nCould not add order item\nDuplicate entry in "+lpoDetail.getDescription()+"\nConsider updating the existing entry.");
    	}
    	try {
	    	lpoDetail.setLpo(lpo);
    	}catch(Exception e) {
    		lpoDetail.setLpo(null);
    	}
        return lpoDetailRepository.save(lpoDetail);
    }
    /**
     * 
     * 
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/lpo_details/{id}", produces = "text/html")
    public ResponseEntity<Object> updateLpoDetail(@PathVariable(value = "id") Long lpoDetailId,
                                            @Valid @RequestBody LpoDetail lpoDetailDetails) {

    	LpoDetail lpoDetail = lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new NotFoundException("LPO detail not found"));

        lpoDetail = lpoDetailDetails;
        
    	Lpo lpo;
    	try {
    		String lpoNo = lpoDetail.getLpo().getLpoNo();
    		lpo = lpoRepository.findByLpoNo(lpoNo).get();
    		lpo.setLpoNo(lpoNo);
	    	lpoRepository.save(lpo);
	    	lpoDetail.setLpo(lpo);
    	}catch(Exception e) {
    		lpoDetail.setLpo(null);
    	}
    	try {
    		lpoDetailRepository.save(lpoDetail);
    		return new ResponseEntity<Object>("LPO detail updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<Object>("LPO detail update failed", HttpStatus.EXPECTATION_FAILED);
    	}
    }
    // Get a Single LpoDetail
    @RequestMapping(method = RequestMethod.GET, value = "/lpo_details/{id}")
    public LpoDetail getLpoDetailById(@PathVariable(value = "id") Long lpoDetailId) {
        return lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new NotFoundException("LPO detail not found"));
    }
    /**
     * 
     * @param lpoDetailId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/lpo_details/{id}", produces = "text/html")
    public ResponseEntity<?> deleteLpoDetail(@PathVariable(value = "id") Long lpoDetailId) {
    	LpoDetail lpoDetail = lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new NotFoundException("LPO detail not found"));
    	try {
    		lpoDetailRepository.delete(lpoDetail);
    		return new ResponseEntity<>("LPO detail deleted successifully.", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not delete. "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }
}
