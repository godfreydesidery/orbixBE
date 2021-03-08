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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.CompanyProfile;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.repositories.CompanyProfileRepository;

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
    
    /**
     * get company details
     */
    @RequestMapping(method = RequestMethod.GET, value = "/company_profile")
    @Transactional
    public CompanyProfile getCompanyProfile() {
    	String key = "1";
        return companyProfileRepository.findByCompanyKey(key)
                .orElseThrow(() -> new NotFoundException("Company details not available"));
    }  
    
    
	// Create profile
    @PostMapping(value="/company_profile")
    @ResponseBody
    public CompanyProfile createCompanyProfile(@Valid @RequestBody CompanyProfile company) {
    	companyProfileRepository.deleteAll();
    	company.setCompanyKey("1");
    	
    	return companyProfileRepository.save(company);
    }
}
