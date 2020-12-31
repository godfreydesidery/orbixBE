/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.Vcn;
import com.example.orbix_web.repositories.SupplierRepository;
import com.example.orbix_web.repositories.VcnRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VcnServiceController {
	@Autowired
	VcnRepository vcnRepository;
	@Autowired
	SupplierRepository supplierRepository;
	// Get All VCNs
    @RequestMapping(method = RequestMethod.GET, value = "/vcns")
    public List<Vcn> getAllVcns() {
        return vcnRepository.findAll();
    }
    
 

}
