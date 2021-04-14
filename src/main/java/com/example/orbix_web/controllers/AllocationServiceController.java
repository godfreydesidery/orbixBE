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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.InvalidEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Allocation;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.CustomerInvoice;
import com.example.orbix_web.models.CustomerInvoiceDetail;
import com.example.orbix_web.models.Sale;
import com.example.orbix_web.models.SaleDetail;
import com.example.orbix_web.repositories.AllocationRepository;
import com.example.orbix_web.repositories.CustomerInvoiceRepository;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.SaleDetailRepository;
import com.example.orbix_web.repositories.SaleRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AllocationServiceController {
	
	@Autowired
	AllocationRepository allocationRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerInvoiceRepository customerInvoiceRepository;
	@Autowired
	SaleRepository saleRepository;
	@Autowired
	SaleDetailRepository saleDetailRepository;
	
	
	// Create a new Customer
    @PostMapping(value="/allocations")
    @ResponseBody
    @Transactional
    public Allocation allocate(@Valid @RequestBody Allocation alloc) {
    	/*
    	 * Capture allocation data
    	 */
    	Allocation _allocation = alloc;
    	String allocationNo = _allocation.getAllocationNo();
    	Date allocationDate =_allocation.getAllocationDate();
    	double allocationAmount = _allocation.getAllocationAmount();
    	String customerNo = _allocation.getCustomer().getCustomerNo();
    	String invoiceNo = _allocation.getCustomerInvoice().getInvoiceNo();
    	
    	/*
    	 * Validate data
    	 */
    	Customer customer = null;
    	CustomerInvoice invoice;
    	try {
    		customer = customerRepository.findByCustomerNo(customerNo).get();
    	}catch(Exception e) {
    		customer = null;
    	}
    	if(customer == null) {
    		throw new NotFoundException("Customer not found");
    	}
    	invoice = customerInvoiceRepository.findByInvoiceNo(invoiceNo);
    	if(invoice == null) {
    		throw new NotFoundException("Customer invoice not found");
    	}
    	if(invoice.getInvoiceStatus().equals("PAID")) {
    		throw new InvalidOperationException("Could not allocate, invoice already paid");
    	}
    	if(invoice.getInvoiceStatus().equals("CANCELLED")) {
    		throw new InvalidOperationException("Could not allocate, invoice cancelled");
    	}
    	double totalAmountDue = customer.getAmountDue();
    	double amountUnallocated = customer.getAmountUnallocated();
    	double invoiceAmountDue = invoice.getInvoiceAmountDue();
    	if(allocationAmount <= 0) {
    		throw new InvalidEntryException("Invalid entry, negative values not allowed");
    	}
    	if(allocationAmount > amountUnallocated) {
    		throw new InvalidOperationException("Operation failed, allocated amount exceeds the allocation balance");
    	}
    	if(allocationAmount > invoiceAmountDue) {
    		throw new InvalidOperationException("Operation failed, allocated amount exceeds due balance");
    	}
    	
    	/*
    	 * Save allocation
    	 */
    	Allocation allocation = new Allocation();
    	allocation.setAllocationNo(allocationNo);
    	allocation.setAllocationDate(allocationDate);
    	allocation.setAllocationAmount(allocationAmount);
    	allocation.setCustomer(customer);
    	allocation.setCustomerInvoice(invoice);
    	allocationRepository.save(allocation);
    	
    	/*
    	 * Update amount due and amount unallocated in customer
    	 */
    	customer.setAmountDue(customer.getAmountDue() - allocationAmount);
    	customer.setAmountUnallocated(customer.getAmountUnallocated() - allocationAmount);
    	customerRepository.save(customer);
    	
    	/*
    	 * Update amount payed and amount due in invoice
    	 */
    	invoice.setInvoiceAmountDue(invoice.getInvoiceAmountDue() - allocationAmount);
    	invoice.setInvoiceAmountPayed(invoice.getInvoiceAmountPayed() + allocationAmount);
    	if(invoice.getInvoiceAmountDue() == 0) {
    		invoice.setInvoiceStatus("PAID");
    	}else {
    		invoice.setInvoiceStatus("PARTIAL");
    	}
    	customerInvoiceRepository.save(invoice);
    	
    	
    	
    	
    	return allocation;
    }


}
