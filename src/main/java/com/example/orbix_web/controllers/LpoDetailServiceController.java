/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.DuplicateEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
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
    @Transactional
    public LpoDetail createLpoDetail(@Valid @RequestBody LpoDetail lpoDetail) {
    	
    	Lpo lpo;
    	String lpoNo = (lpoDetail.getLpo()).getLpoNo();
    	lpo = lpoRepository.findByLpoNo(lpoNo).get();
	    lpoRepository.save(lpo);
	    String status = lpo.getStatus();
	    if(!status.equals("PENDING")) {
	    	throw new InvalidOperationException("Can not add item, LPO not a pending order");
	    }
    	if(lpoDetailRepository.existsByLpoAndItemCode(lpo, lpoDetail.getItemCode()) == true){
    		throw new DuplicateEntryException("\nCould not add order item\nDuplicate entry in "+lpoDetail.getDescription()+"\nConsider updating the existing entry.");
    	}
    	try {
	    	lpoDetail.setLpo(lpo);
    	}catch(Exception e) {
    		lpoDetail.setLpo(null);
    	}
    	
    	lpoDetail.setOrderNo(lpo.getLpoNo());
        return lpoDetailRepository.saveAndFlush(lpoDetail);
    }
    /**
     * 
     * 
     */
    //Update lpo detail
    @Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/lpo_details/{id}", produces = "text/html")
    public ResponseEntity<Object> updateLpoDetail(@PathVariable(value = "id") Long lpoDetailId,
                                            @Valid @RequestBody LpoDetail lpoDetailDetails) {

    	LpoDetail lpoDetail = lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new NotFoundException("LPO detail not found"));

        lpoDetail = lpoDetailDetails;
        
    	Lpo lpo = null;
    	
    	try {
    		String lpoNo = lpoDetail.getLpo().getLpoNo();
    		lpo = lpoRepository.findByLpoNo(lpoNo).get();
    		lpo.setLpoNo(lpoNo);
	    	lpoRepository.save(lpo);
	    	lpoDetail.setLpo(lpo);
    	}catch(Exception e) {
    		lpoDetail.setLpo(null);
    	}
    	String _status = "";
    	if(lpo != null) {
    		_status = lpo.getStatus();
    		if(!_status.equals("PENDING")) {
    			throw new InvalidOperationException("Can not edit, LPO not a pending order");
    		}
    	}
    	
    	try {
    		//lpoDetail.setOrderNo(lpo.getLpoNo());
    		lpoDetailRepository.save(lpoDetail);
    		return new ResponseEntity<Object>("LPO detail updated", HttpStatus.OK);
    	}catch(Exception e) {
    		throw new InvalidOperationException("LPO detail update failed");
    	}
    }
    // Get a Single LpoDetail
    @Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/lpo_details/{id}")
    public LpoDetail getLpoDetailById(@PathVariable(value = "id") Long lpoDetailId) {
    	
        return lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new NotFoundException("LPO detail not found"));
    }
 // Get a a list of LpoDetails
    @Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/lpo_details/lpo_id={lpo_id}")
    public List<LpoDetail> getLpoDetails(@PathVariable(value = "lpo_id") Long lpoId) {
    	Lpo lpo = null;
    	try {
    		lpo = lpoRepository.findById(lpoId).get();
    	}catch(Exception e) {
    		throw new NotFoundException("LPO not found");
    	}
        return lpoDetailRepository.findByLpo(lpo);
    }
    /**
     * 
     * @param lpoDetailId
     * @return
     */
    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/lpo_details/{id}", produces = "text/html")
    public ResponseEntity<?> deleteLpoDetail(@PathVariable(value = "id") Long lpoDetailId) {
    	LpoDetail lpoDetail = lpoDetailRepository.findById(lpoDetailId)
                .orElseThrow(() -> new NotFoundException("LPO detail not found"));
    	
    	String _status = "";
    	String lpoNo = lpoDetail.getLpo().getLpoNo();
		Lpo lpo = lpoRepository.findByLpoNo(lpoNo).get();
    	if(lpo != null) {
    		_status = lpo.getStatus();
    		if(!_status.equals("PENDING")) {
    			throw new InvalidOperationException("Can not edit, LPO not a pending order");
    		}
    	}
    	
    	try {
    		lpoDetailRepository.delete(lpoDetail);
    		return new ResponseEntity<>("LPO detail deleted successifully.", HttpStatus.OK);
    	}catch(Exception e) {
    		throw new InvalidOperationException("Could not delete. "+e.getMessage());
    	}
    }
}
