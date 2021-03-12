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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.DuplicateEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.CustomerInvoice;
import com.example.orbix_web.models.CustomerInvoiceDetail;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.repositories.CustomerInvoiceDetailRepository;
import com.example.orbix_web.repositories.CustomerInvoiceRepository;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.StockCardRepository;

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
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	StockCardRepository stockCardRepository;
	
	
	// Create a new Invoice
    @RequestMapping(method = RequestMethod.POST, value = "/customer_invoices")
    @ResponseBody
    @Transactional
	public CustomerInvoice createCustomerInvoice(@Valid @RequestBody CustomerInvoice inv ) {
    	/*
    	 * Define field variables
    	 */
    	CustomerInvoice invoice;
    	CustomerInvoice _invoice;
    	List<CustomerInvoiceDetail> details;
    	Customer customer;
    	/*
    	 * Capture invoice data
    	 */
    	 _invoice = inv;
    	customer = customerRepository.findById(_invoice.getCustId()).get();
    	details = _invoice.getInvoiceDetails();
    	
    	
    	/*
    	 * Validate invoice data
    	 */
    	if(customer == null) {
			throw new MissingInformationException("Could not process invoice, missing customer information");
		}
		invoice = customerInvoiceRepository.findByInvoiceNo(_invoice.getInvoiceNo());
		if(invoice != null) {
			throw new DuplicateEntryException("Could not save invoice, invoice exists");
		}
		for(CustomerInvoiceDetail _detail :details) {
			if(_detail.getItemCode().equals("")) {
				
			}
		}
		if(details.isEmpty() == true) {
			throw new InvalidOperationException("Could not save an empty invoice");
		}
		
		/*
		 * Save invoice data
		 */
		invoice = new CustomerInvoice();		
		invoice.setInvoiceNo(_invoice.getInvoiceNo());
		invoice.setInvoiceDate(_invoice.getInvoiceDate());
		invoice.setCustomer(customer);		
		invoice = customerInvoiceRepository.save(invoice);
		double amount = 0;
		for(CustomerInvoiceDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
			
			CustomerInvoiceDetail detail = new CustomerInvoiceDetail();
			detail.setCustomerInvoice(invoice);
			detail.setItemCode(_detail.getItemCode());
			detail.setDescription(_detail.getDescription());
			detail.setPrice(_detail.getPrice());
			if(_item.getUnitRetailPrice() > _detail.getPrice()) {
				detail.setDiscount(_item.getUnitRetailPrice() - _detail.getPrice());
			}else {
				detail.setDiscount(0);
			}
			detail.setQty(_detail.getQty());
			amount = amount + (_detail.getPrice()*_detail.getQty());
			customerInvoiceDetailRepository.save(detail);
		}
		invoice.setInvoiceAmount(amount);
		invoice.setInvoiceAmountDue(amount);
		invoice = customerInvoiceRepository.save(invoice);
		customer.setAmountDue(customer.getAmountDue()+amount);
		customerRepository.save(customer);
		/*
		 * Update stock
		 */
		for(CustomerInvoiceDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
			itemRepository.saveAndFlush(
					new ItemServiceController()
					.deductFromStock(_item, _detail.getQty()));
		}
		/*
		 * Update stock cards
		 */
		for(CustomerInvoiceDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
			double _stockBalance =_item.getQuantity();
			stockCardRepository.save(
					new StockCardServiceController()
					.creditSale(_item, _invoice.getInvoiceDate(), _detail.getQty(), _invoice.getInvoiceNo(), _stockBalance));	
		}
		/*
		 * Return invoice with details
		 */
		invoice.setInvoiceDetails(details);
		return invoice;
	}
    
 // Get a Single Customer Invoice by invoice no
    @RequestMapping(method = RequestMethod.GET, value = "/customer_invoices/invoice_no={invoice_no}")
    public CustomerInvoice getInvoiceByInvoiceNo(@PathVariable(value = "invoice_no") String invoiceNo) {
    	CustomerInvoice invoice = customerInvoiceRepository.findByInvoiceNo(invoiceNo);
    	if(invoice == null) {
    		throw new NotFoundException("Invoice not found");
    	}
        return invoice;
    }
    
 // Get a Single Customer Invoice by invoice no and customer no
    @RequestMapping(method = RequestMethod.GET, value = "/customer_invoices/invoice_no={invoice_no}/customer_no={customer_no}")
    public CustomerInvoice getInvoiceByInvoiceNoAndCustomerNo(@PathVariable(value = "invoice_no") String invoiceNo,@PathVariable(value = "customer_no") String customerNo) {
    	CustomerInvoice invoice = customerInvoiceRepository.findByInvoiceNo(invoiceNo);
    	if(invoice == null) {
    		throw new NotFoundException("Invoice not found");
    	}
    	Customer customer;
    	try {
    		customer = customerRepository.findByCustomerNo(customerNo).get();
    	}catch(Exception e) {
    		throw new NotFoundException("Customer not found");
    	}
    	if(customer != invoice.getCustomer()) {
    		throw new InvalidOperationException("The specified invoice does not match customer");
    	}
        return invoice;
    }


}
