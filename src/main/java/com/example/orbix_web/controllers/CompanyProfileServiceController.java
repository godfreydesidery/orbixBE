/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.CompanyProfile;
import com.example.orbix_web.models.CorporateCustomer;
import com.example.orbix_web.repositories.CompanyProfileRepository;
import com.example.orbix_web.repositories.CorporateCustomerRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyProfileServiceController {
	
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	

	// Get profile
    @GetMapping("/company")
    public List<CompanyProfile> getCompanyProfilee() {
        return companyProfileRepository.findAll();
    }
    
    
	// Create profile
    @PostMapping(value="/company")
    @ResponseBody
    public CompanyProfile createCompanyProfile(@Valid @RequestBody CompanyProfile company) {
    	companyProfileRepository.deleteAll();
    	return companyProfileRepository.save(company);
    }
}
