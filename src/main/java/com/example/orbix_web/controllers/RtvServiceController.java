/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

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

import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.Rtv;
import com.example.orbix_web.models.RtvDetail;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.RtvDetailRepository;
import com.example.orbix_web.repositories.RtvRepository;
import com.example.orbix_web.repositories.SupplierRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RtvServiceController {
	
	@Autowired
    SupplierRepository supplierRepository;
	@Autowired
    RtvRepository rtvRepository;
	@Autowired
    RtvDetailRepository rtvDetailRepository;
	
	// Get All RTVs
	@Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/rtvs")
    public List<Rtv> getAllRtvs() {
        return rtvRepository.findAll();
    }
	
	// Create a new RTV
	@Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/rtvs")
    @ResponseBody
	public Rtv createRtv(@Valid @RequestBody Rtv rtv) {	
    	Supplier supplier;
    	try {
    		String supplierName = (rtv.getSupplier()).getSupplierName();
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
	    	supplierRepository.save(supplier);
	    	rtv.setSupplier(supplier);
    	}catch(Exception e) {
    		rtv.setSupplier(null);
    	}
    	rtv.setStatus("PENDING");
    	return rtvRepository.saveAndFlush(rtv);
	}
    
    // Update a RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/{id}", produces = "text/html")
    public ResponseEntity<Object> updateRtv(@PathVariable(value = "id") Long rtvId, @Valid @RequestBody Rtv rtvDetails){
    	Rtv rtv = rtvRepository.findById(rtvId)
                .orElseThrow(() -> new NotFoundException("RTV not found"));
    	rtv = rtvDetails;
    	try {
    		rtvRepository.save(rtv);
    		return new ResponseEntity<>("RTV updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not update RTV, "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
    //Approve RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/approve/{id}")
    public Rtv approveRtv(@PathVariable(value = "id") Long rtvId){
    	Rtv rtv = rtvRepository.findById(rtvId)
    			.orElseThrow(() -> new NotFoundException("RTV not found"));
		String status = rtv.getStatus();
		if(status.equals("PENDING")) {
			rtv.setStatus("APPROVED");
			return rtvRepository.saveAndFlush(rtv);
		}else if(status.equals("APPROVED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already approved.");
		}else if(status.equals("CANCELED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV has been canceled."); 
		}else if(status.equals("COMPLETED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already completed."); 
		}else {
			throw new InvalidOperationException("Could not approve RTV. RTV status unknown."); 
		}
    }
    
    //Complete RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/complete/{id}")
    public Rtv completeRtv(@PathVariable(value = "id") Long rtvId){
    	Rtv rtv = rtvRepository.findById(rtvId)
    			.orElseThrow(() -> new NotFoundException("RTV not found"));
		String status = rtv.getStatus();
		if(status.equals("APPROVED")) {
			//now complete details
			List<RtvDetail> _rtvDetails = rtvDetailRepository.findByRtv(rtv);
			for(RtvDetail _rtvDetail : _rtvDetails) {
				//remove item from stock
			}
			rtv.setStatus("COMPLETED");
			return rtvRepository.saveAndFlush(rtv);
		}else if(status.equals("PENDING")) {
			throw new InvalidOperationException("Could not approve RTV. RTV not approved.");
		}else if(status.equals("CANCELED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV has been canceled."); 
		}else if(status.equals("COMPLETED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already completed."); 
		}else {
			throw new InvalidOperationException("Could not approve RTV. RTV status unknown."); 
		}
    }
    //Cancel RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/cancel/{id}")
    public Rtv cancelRtv(@PathVariable(value = "id") Long rtvId){
    	Rtv rtv = rtvRepository.findById(rtvId)
    			.orElseThrow(() -> new NotFoundException("RTV not found"));
		String status = rtv.getStatus();
		if(status.equals("PENDING") || status.equals("APPROVED")) {
			rtv.setStatus("CANCELED");
			return rtvRepository.saveAndFlush(rtv);
		}else if(status.equals("COMPLETED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already completed.");
		}else {
			throw new InvalidOperationException("Could not approve RTV. RTV status unknown."); 
		}
    }
    
    // Delete RTV
	@Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/rtvs/{id}", produces = "text/html")
    public ResponseEntity<?> deleteRtv(@PathVariable(value = "id") Long rtvId) {
    	Rtv rtv = rtvRepository.findById(rtvId)
                .orElseThrow(() -> new NotFoundException("RTV not found"));
    	try {
    		String status = rtv.getStatus();
    		if(status.equals("COMPLETED") || status.equals("APPROVED")) {
    			throw new InvalidOperationException("Could not delete, you can only delete a pending or canceled RTV.\nTo delete, first cancel the RTV.");
    		}else {
    			rtvRepository.delete(rtv);
    			return new ResponseEntity<>("RTV deleted successifully.", HttpStatus.OK);
    		}
    	}catch(Exception ex) {
    		throw new InvalidOperationException("Could not delete RTV: "+ex.getMessage());
    	}    	
    }
                                            
}
