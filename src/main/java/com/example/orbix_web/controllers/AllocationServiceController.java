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
    	double totalAmountDue = customer.getAmountDue();
    	double amountUnallocated = customer.getAmountUnallocated();
    	double invoiceAmountDue = invoice.getInvoiceAmountDue();
    	if(allocationAmount <= 0) {
    		throw new InvalidEntryException("Invalid allocation amount, negative values are not allowed");
    	}
    	if(allocationAmount > amountUnallocated) {
    		throw new InvalidOperationException("Operation failed, allocated amount exceeds the allocation balance");
    	}
    	if(allocationAmount != invoiceAmountDue) {
    		throw new InvalidOperationException("Operation failed, allocated amount should be equal to the due balance");
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
    	customerInvoiceRepository.save(invoice);
    	/*
    	 * Now post invoice to sales
    	 */
    	Sale sale = new Sale();
    	sale.setCustomerInvoice(invoice);
    	sale.setSaleDate(_allocation.getAllocationDate());
    	saleRepository.save(sale);
    	
    	List<CustomerInvoiceDetail> invoiceDetails;
    	invoiceDetails = invoice.getInvoiceDetails();
    	for(CustomerInvoiceDetail invoiceDetail : invoiceDetails) {
    		SaleDetail saleDetail = new SaleDetail();
    		saleDetail.setItemCode(invoiceDetail.getItemCode());
    		saleDetail.setDescription(invoiceDetail.getDescription());
    		saleDetail.setPrice(invoiceDetail.getPrice());
    		saleDetail.setQty(invoiceDetail.getQty());
    		saleDetail.setDiscount(invoiceDetail.getDiscount());
    		saleDetail.setSale(sale);
    		
    		saleDetailRepository.save(saleDetail);
    		
    	}
    	
    	
    	
    	return allocation;
    }


}
