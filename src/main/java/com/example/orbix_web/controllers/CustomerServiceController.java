/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDateTime;
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

import com.example.orbix_web.accessories.Formater;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.SupplierRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerServiceController {

    @Autowired
    CustomerRepository customerRepository;
    
    // Get All Corporate Customers
    @GetMapping("/customers")
    public List<Customer> getAllCorporateCustomers() {
        return customerRepository.findAll();
    }
    
    /**
     * 
     * @return array of customers' names
     */
    @GetMapping(value="/customers/customer_names")
    public Iterable<Customer> getAllCustomerByNames() {
        return customerRepository.getCustomerNames();
    }
    

    // Create a new Customer
    @PostMapping(value="/customers")
    @ResponseBody
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
    	customer.setCustomerNo(String.valueOf(Math.random()));
    	customerRepository.save(customer);
    	String serial = customer.getId().toString();
    	String custNo = Formater.formatNine(serial);
    	customer.setCustomerNo(custNo);
    	customerRepository.save(customer);
    	return customer;
    }

    // Get a Single Customer
    @GetMapping("/customers/{id}")
    public Customer getCorporateCustomerById(@PathVariable(value = "id") Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
    }
    // Get a Single Customer by name
    @GetMapping("/customers/customer_name={customer_name}")
    public Customer getCustomerByCustomerName(@PathVariable(value = "customer_name") String customerName) {
    	
        return customerRepository.findByCustomerName(customerName)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customer_name", customerName));
    }
    
    // Get a Single Customer by customer no
    @GetMapping("/customers/customer_no={customer_no}")
    public Customer getCorporateCustomerByCorporateCustomerNo(@PathVariable(value = "customer_no") String customerNo) {
    	
        return customerRepository.findByCustomerNo(customerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customer_no", customerNo));
    }

    // Update a Customer
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Long customerId,
                                            @Valid @RequestBody Customer customerDetails) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        if(!customerDetails.getCustomerNo().equals(customer.getCustomerNo())) {
        	throw new InvalidOperationException("Changing customer number not allowed");
        }

        customer = customerDetails;

        Customer updatedCustomer = customerRepository.save(customer);
        return updatedCustomer;
    }

    // Delete a Customer
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") Long customerId) {
    	Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

    	customerRepository.delete(customer);

        return ResponseEntity.ok().build();
    }
}
