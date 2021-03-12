/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.CustomerInvoice;
import com.example.orbix_web.models.Receipt;
import com.example.orbix_web.models.SalesReceipt;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.ReceiptRepository;
import com.example.orbix_web.repositories.SalesReceiptRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SalesReceiptServiceController {

    @Autowired
    SalesReceiptRepository salesReceiptRepository;
    @Autowired
    CustomerRepository customerRepository;
    
 // Create a new Invoice
    @RequestMapping(method = RequestMethod.POST, value = "/sales_receipts")
    @ResponseBody
    @Transactional
	public SalesReceipt createSalesReceipt(@Valid @RequestBody SalesReceipt rec ) {
    	/*
    	 * Capture receipt data
    	 */
    	SalesReceipt _receipt = rec;
    	SalesReceipt receipt = null;
    	String receiptNo = _receipt.getReceiptNo();
    	Date receiptDate = _receipt.getReceiptDate();
    	double amount = _receipt.getReceiptAmount();
    	String paymentMode = _receipt.getPaymentMode();
    	String chequeNo = _receipt.getChequeNo();
    	Date chequeDate = _receipt.getChequeDate();
    	String bank = _receipt.getChequeBank();
    	String creditNoteNo = _receipt.getCreditNoteNo();
    	String custNo = _receipt.getCustomer().getCustomerNo();
    	Customer customer;
    	
    	
    	/*
    	 * Validate receipt data
    	 */
    	receipt = salesReceiptRepository.findByReceiptNo(receiptNo);
    	if(receipt != null) {
    		throw new InvalidOperationException("Could not save receipt, a receipt with similar receipt number exist");
    	}else {
    		receipt = new SalesReceipt();
    	}
    	if(paymentMode == null || paymentMode == "") {
    		throw new MissingInformationException("Could not save receipt, payment mode not specified");
    	}
    	if(custNo == "") {
    		throw new MissingInformationException("Could not save receipt, customer required");
    	}else {
    		customer = customerRepository.findByCustomerNo(custNo).get();
    		if(customer == null) {
    			throw new MissingInformationException("Could not save receipt, customer required");
    		}
    	}
    	//add more validation code
    	
    	/*
    	 * Save receipt
    	 */
    	receipt = new SalesReceipt();
    	receipt.setCustomer(customer);
    	receipt.setReceiptNo(receiptNo);
    	receipt.setReceiptDate(receiptDate);
    	receipt.setReceiptAmount(amount);
    	receipt.setPaymentMode(paymentMode);
    	receipt.setChequeNo(chequeNo);
    	receipt.setChequeDate(chequeDate);
    	receipt.setChequeBank(bank);
    	receipt.setCreditNoteNo(creditNoteNo);
    	salesReceiptRepository.save(receipt);
    	/*
    	 * Update customer unallocated amount
    	 */
    	customer.setAmountUnallocated(customer.getAmountUnallocated() + amount);
    	customerRepository.save(customer);
    	
    	return receipt;
    }
    
  
}

