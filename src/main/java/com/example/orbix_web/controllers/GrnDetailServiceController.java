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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.DuplicateEntryException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.GrnDetail;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;
import com.example.orbix_web.repositories.GrnDetailRepository;
import com.example.orbix_web.repositories.GrnRepository;
import com.example.orbix_web.repositories.LpoRepository;
import com.example.orbix_web.repositories.UserRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GrnDetailServiceController {
	@Autowired
    GrnDetailRepository grnDetailRepository;
	@Autowired
    GrnRepository grnRepository;
	@Autowired
    UserRepository userRepository;
	// Get All GRN details
    @RequestMapping(method = RequestMethod.GET, value = "/grn_details")
    public List<GrnDetail> getAllGrnDetails() {
        return grnDetailRepository.findAll();
    }
	// Create a new Grn detail
    @RequestMapping(method = RequestMethod.POST, value="/grn_detai", produces = {"text/html","application/json"})
    @ResponseBody
    public void createGrnDetail(@Valid @RequestBody GrnDetail grnDetail) {    	
    	/*Grn grn;
    	String grnNo = (grnDetail.getGrn()).getGrnNo();
    	grn = grnRepository.findByGrnNo(grnNo).get();
	    grnRepository.save(grn);	    
    	if(grnDetailRepository.existsByGrnAndItemCode(grn, grnDetail.getItemCode()) == true){
    		throw new DuplicateEntryException("\nCould not add order item\nDuplicate entry in "+grnDetail.getDescription()+"\nConsider updating the existing entry.");
    	}
    	try {
	    	grnDetail.setGrn(grn);
    	}catch(Exception e) {
    		grnDetail.setGrn(null);
    	}
        return grnDetailRepository.save(grnDetail);*/
    }
    
    
    
    //Update grn detail
    @RequestMapping(method = RequestMethod.PUT, value = "/grn_details/{id}", produces = "text/html")
    public ResponseEntity<Object> updateGrnDetail(@PathVariable(value = "id") Long grnDetailId,
                                            @Valid @RequestBody GrnDetail grnDetailDetails) {

    	GrnDetail grnDetail = grnDetailRepository.findById(grnDetailId)
                .orElseThrow(() -> new NotFoundException("GRN detail not found"));

        grnDetail = grnDetailDetails;
        
    	Grn grn;
    	try {
    		String grnNo = grnDetail.getGrn().getGrnNo();
    		grn = grnRepository.findByGrnNo(grnNo).get();
    		grn.setGrnNo(grnNo);
	    	grnRepository.save(grn);
	    	grnDetail.setGrn(grn);
    	}catch(Exception e) {
    		throw new NotFoundException("Fail to process GRN detail. GRN not found");
    	}
    	try {
    		grnDetailRepository.save(grnDetail);
    		return new ResponseEntity<Object>("GRN detail updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<Object>("GRN detail update failed", HttpStatus.EXPECTATION_FAILED);
    	}
    }
}
