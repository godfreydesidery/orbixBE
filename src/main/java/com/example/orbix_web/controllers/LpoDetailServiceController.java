/**
 * 
 */
package com.example.orbix_web.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;
import com.example.orbix_web.models.Supplier;
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
	
	@Autowired
    LpoRepository lpoRepository;
	@Autowired
	LpoDetailRepository lpoDetailRepository;
	
	// Create a new Lpo detail
    @PostMapping(value="/lpo_details")
    @ResponseBody
    public LpoDetail createLpoDetail(@Valid @RequestBody LpoDetail lpoDetail) {
    	
    	Lpo lpo;
    	try {
    		String lpoNo = (lpoDetail.getLpo()).getLpoNo();
    		lpo = lpoRepository.findByLpoNo(lpoNo).get();
    		lpo.setLpoNo(lpoNo);
	    	lpoRepository.save(lpo);
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
    @PutMapping("/lpo_details/{id}")
    public LpoDetail updateLpoDetail(@PathVariable(value = "id") Long lpoDetailId,
                                            @Valid @RequestBody LpoDetail lpoDetailDetails) {

    	LpoDetail lpoDetail = lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("LpoDetail", "id", lpoDetailId));

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
        return lpoDetailRepository.save(lpoDetail);
    }
    
    
 // Get a Single LpoDetail
    @GetMapping("/lpo_details/{id}")
    public LpoDetail getLpoDetailById(@PathVariable(value = "id") Long lpoDetailId) {
        return lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("LpoDetail", "id", lpoDetailId));
    }
    
    /**
     * 
     * @param lpoDetailId
     * @return
     */
    @DeleteMapping("/lpo_details/{id}")
    public ResponseEntity<?> deleteLpoDetail(@PathVariable(value = "id") Long lpoDetailId) {
    	LpoDetail lpoDetail = lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("LpoDetail", "id", lpoDetailId));

    	lpoDetailRepository.delete(lpoDetail);

        return ResponseEntity.ok().build();
    }
}
