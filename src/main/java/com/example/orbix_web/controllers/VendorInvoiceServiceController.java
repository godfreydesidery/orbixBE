/**
 * 
 */
package com.example.orbix_web.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.VendorInvoice;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendorInvoiceServiceController {
	// Create a new LPO
    @RequestMapping(method = RequestMethod.POST, value = "/vendor_invoices/create")
    @ResponseBody
    @Transactional
    public VendorInvoice createVendorInvoice(@Valid @RequestBody VendorInvoice inv) {
    	System.out.println("Success");
    	
    	return inv;
    }

}
