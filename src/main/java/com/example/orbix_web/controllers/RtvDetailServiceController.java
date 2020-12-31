/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.orbix_web.exceptions.OperationFailedException;
import com.example.orbix_web.models.Rtv;
import com.example.orbix_web.models.RtvDetail;
import com.example.orbix_web.repositories.RtvDetailRepository;
import com.example.orbix_web.repositories.RtvRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RtvDetailServiceController {
	
	@Autowired
	RtvRepository rtvRepository;
	@Autowired
	RtvDetailRepository rtvDetailRepository;
	
	// get rtv detail
	@Transactional
	@RequestMapping(method = RequestMethod.GET, value = "/rtv_details/{id}")
	public RtvDetail getRtvDetail(@PathVariable(value = "id") Long id) {
		RtvDetail rtvDetail = null;
		try {
			rtvDetail = rtvDetailRepository.findById(id).get();
		}catch(Exception e) {
			throw new NotFoundException("RTV detail not found");
		}
		return rtvDetail;
	}
	// Create Rtv Detail
	@Transactional
	@RequestMapping(method = RequestMethod.GET, value = "/rtv_details/rtv_id= {rtv_id}")
	public List<RtvDetail> getRtvDetails(@PathVariable(value = "rtv_id") Long rtvId) {
		Rtv rtv = null;
		try {
			rtv = rtvRepository.findById(rtvId).get();
		}catch(Exception e) {
			throw new NotFoundException("RTV not found");
		}
		return rtvDetailRepository.findByRtv(rtv);
	}
	// Create Rtv Detail
	@RequestMapping(method = RequestMethod.POST, value = "/rtv_details")
    @ResponseBody
    @Transactional
	public boolean createRtvDetail(@Valid @RequestBody RtvDetail rtvDetail) {
		boolean created = false;
		Rtv rtv = rtvRepository.findById(rtvDetail.getRtv().getId())
                .orElseThrow(() -> new NotFoundException("RTV not found"));
		if(rtv.getStatus().equals("COMPLETED")) {
			throw new InvalidOperationException("Could not modify, RTV already completed");
		}
    	try {
    		Long rtvId = (rtvDetail.getRtv().getId());
    		rtv = rtvRepository.findById(rtvId).get();
	    	rtvRepository.save(rtv);
	    	rtvDetail.setRtv(rtv);
	    	rtvDetailRepository.saveAndFlush(rtvDetail);
	    	created = true;
    	}catch(Exception e) {
    		throw new NotFoundException("RTV not found");
    	}
    	return created;
	}
	// Update Rtv Detail
	@RequestMapping(method = RequestMethod.PUT, value = "/rtv_details/{id}")
    @ResponseBody
    @Transactional
	public boolean updateRtvDetail(@PathVariable(value = "id") Long rtvDetailId, @Valid @RequestBody RtvDetail rtvDetail) {
		boolean updated = false;
		Rtv rtv = rtvRepository.findById(rtvDetail.getRtv().getId())
                .orElseThrow(() -> new NotFoundException("RTV not found"));
		if(rtv.getStatus().equals("COMPLETED")) {
			throw new InvalidOperationException("Could not update, RTV already completed");
		}
		try {
			rtvDetailRepository.saveAndFlush(rtvDetail);
			updated = true;
		}catch(Exception e) {
			throw new OperationFailedException("Failed to update");
		}
		return updated;
	}
	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, value = "/rtv_details/{id}")
	public boolean deleteRtvDetail(@PathVariable(value = "id") Long rtvDetailId) {
		boolean deleted = false;
		RtvDetail rtvDetail = rtvDetailRepository.findById(rtvDetailId)
                .orElseThrow(() -> new NotFoundException("RTV detail not found"));
		Rtv rtv = rtvRepository.findById(rtvDetail.getRtv().getId())
                .orElseThrow(() -> new NotFoundException("RTV not found"));
		if(rtv.getStatus().equals("COMPLETED")) {
			throw new InvalidOperationException("Could not delete, RTV already completed");
		}
		try {
			rtvDetailRepository.delete(rtvDetail);
			deleted = true;
		}catch(Exception e) {
			throw new OperationFailedException("Could not delete");
		}
		return deleted;
	}
}
////add validation checks
