/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;
import java.util.Optional;

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

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.CustomerInvoice;
import com.example.orbix_web.models.CustomerInvoiceDetail;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.repositories.CustomerInvoiceDetailRepository;
import com.example.orbix_web.repositories.CustomerInvoiceRepository;
import com.example.orbix_web.repositories.CustomerRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerInvoiceServiceController {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerInvoiceRepository customerInvoiceRepository;
	@Autowired
	CustomerInvoiceDetailRepository customerInvoiceDetailRepository;
	
	// Create a new Invoice
    @RequestMapping(method = RequestMethod.POST, value = "/customer_invoices")
    @ResponseBody
    @Transactional
	public CustomerInvoice createCustomerInvoice(@Valid @RequestBody CustomerInvoice _invoice ) {
		Long customerId = _invoice.getCustId();
		Customer customer = customerRepository.findById(customerId).get();
				
		CustomerInvoice invoice = new CustomerInvoice();
		
		
		invoice.setInvoiceNo(_invoice.getInvoiceNo());
		invoice.setInvoiceDate(_invoice.getInvoiceDate());
		invoice.setCustomer(customer);
		
		CustomerInvoice savedInvoice = customerInvoiceRepository.saveAndFlush(invoice);
		
		List<CustomerInvoiceDetail> details = _invoice.getInvoiceDetails();
		
		for(CustomerInvoiceDetail _detail :details) {
			System.out.println(_detail.getItemCode());
			CustomerInvoiceDetail detail = new CustomerInvoiceDetail();
			detail.setCustomerInvoice(savedInvoice);
			detail.setItemCode(_detail.getItemCode());
			detail.setDescription(_detail.getDescription());
			detail.setPrice(_detail.getPrice());
			detail.setQty(_detail.getQty());
			customerInvoiceDetailRepository.saveAndFlush(detail);
		}
		
		invoice.setInvoiceDetails(details);
		return invoice;
	}

}
