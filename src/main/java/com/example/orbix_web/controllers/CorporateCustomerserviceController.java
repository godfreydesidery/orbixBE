/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

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
import com.example.orbix_web.models.CorporateCustomer;
import com.example.orbix_web.repositories.CorporateCustomerRepository;
import com.example.orbix_web.repositories.SupplierRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CorporateCustomerserviceController {

    @Autowired
    CorporateCustomerRepository corporateCustomerRepository;
    
    // Get All Corporate Customers
    @GetMapping("/corporate_customers")
    public List<CorporateCustomer> getAllCorporateCustomers() {
        return corporateCustomerRepository.findAll();
    }
    
    /**
     * 
     * @return array of customers' names
     */
    @GetMapping(value="/corporate_customers/customer_names")
    public Iterable<CorporateCustomer> getAllCorporateCustomerByNames() {
        return corporateCustomerRepository.getCorporateCustomerNames();
    }

    // Create a new Customer
    @PostMapping(value="/corporate_customers")
    @ResponseBody
    public CorporateCustomer createCustomer(@Valid @RequestBody CorporateCustomer customer) {
    	return corporateCustomerRepository.save(customer);
    }

    // Get a Single Customer
    @GetMapping("/corporate_customers/{id}")
    public CorporateCustomer getCorporateCustomerById(@PathVariable(value = "id") Long customerId) {
        return corporateCustomerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateCustomer", "id", customerId));
    }
    // Get a Single Customer by name
    @GetMapping("/corporate_customers/customer_name={customer_name}")
    public CorporateCustomer getCorporateCustomerByCorporateCustomerName(@PathVariable(value = "customer_name") String customerName) {
    	
        return corporateCustomerRepository.findByCustomerName(customerName)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateCustomer", "customer_name", customerName));
    }
    
    // Get a Single Customer by customer no
    @GetMapping("/corporate_customers/customer_no={customer_no}")
    public CorporateCustomer getCorporateCustomerByCorporateCustomerNo(@PathVariable(value = "customer_no") String customerNo) {
    	
        return corporateCustomerRepository.findByCustomerNo(customerNo)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateCustomer", "customer_no", customerNo));
    }

    // Update a Customer
    @PutMapping("/corporate_customers/{id}")
    public CorporateCustomer updateCorporateCustomer(@PathVariable(value = "id") Long customerId,
                                            @Valid @RequestBody CorporateCustomer customerDetails) {

        CorporateCustomer customer = corporateCustomerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateCustomer", "id", customerId));

        customer = customerDetails;

        CorporateCustomer updatedCustomer = corporateCustomerRepository.save(customer);
        return updatedCustomer;
    }

    // Delete a Customer
    @DeleteMapping("/corporate_customers/{id}")
    public ResponseEntity<?> deleteCorporateCustomer(@PathVariable(value = "id") Long customerId) {
    	CorporateCustomer customer = corporateCustomerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateCustomer", "id", customerId));

    	corporateCustomerRepository.delete(customer);

        return ResponseEntity.ok().build();
    }
}
