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

import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.User;
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
public class GrnServiceController {
	
	@Autowired
    GrnRepository grnRepository;
	@Autowired
    LpoRepository lpoRepository;
	@Autowired
    UserRepository userRepository;
	
	// Get All GRNs
    @RequestMapping(method = RequestMethod.GET, value = "/grns")
    public List<Grn> getAllGrns() {
        return grnRepository.findAll();
    }
    // Create a new GRN
    @RequestMapping(method = RequestMethod.POST, value = "/grns")
    @ResponseBody
    public Grn createGrn(@Valid @RequestBody Grn grn) {
    	Lpo lpo;
    	User user;
    	try {
    		String lpoNo = (grn.getLpo().getLpoNo());
    		lpo = lpoRepository.findByLpoNo(lpoNo).get();
	    	lpoRepository.save(lpo);
	    	grn.setLpo(lpo);
    	}catch(Exception e) {
    		throw new NotFoundException("Fail to process GRN. LPO not found");
    	}
    	try {
    		Long userId = (grn.getCreatedBy().getId());
    		user = userRepository.findById(userId).get();
	    	userRepository.save(user);
	    	grn.setCreatedBy(user);
    	}catch(Exception e) {
    		grn.setCreatedBy(null);
    	}  	
        return grnRepository.save(grn);
    }
    // Get a Single GRN
    @RequestMapping(method = RequestMethod.GET, value = "/grns/{id}")
    public Grn getGrnById(@PathVariable(value = "id") Long grnId) {
        return grnRepository.findById(grnId)
                .orElseThrow(() -> new NotFoundException("GRN not found"));
    }
    // Get a Single GRN by grn no
    @RequestMapping(method = RequestMethod.GET, value = "/grns/grn_no={grn_no}")
    public Grn getGrnByGrnNo(@PathVariable(value = "grn_no") String grnNo) {
        return grnRepository.findByGrnNo(grnNo)
                .orElseThrow(() -> new NotFoundException("GRN not found"));
    }
    // Update a GRN
    @RequestMapping(method = RequestMethod.PUT, value = "/grns/{id}", produces = "text/html")
    public ResponseEntity<Object> updateGrn(@PathVariable(value = "id") Long grnId,
                                            @Valid @RequestBody Grn grnDetails) {
    	Grn grn = grnRepository.findById(grnId)
                .orElseThrow(() -> new NotFoundException("GRN not found"));
    	grn = grnDetails;
    	try {
    		grnRepository.save(grn);
    		return new ResponseEntity<>("GRN updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not update GRN, "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
}
