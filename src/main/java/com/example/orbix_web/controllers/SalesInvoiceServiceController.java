/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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

import com.example.orbix_web.accessories.Formater;
import com.example.orbix_web.exceptions.DuplicateEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Sale;
import com.example.orbix_web.models.SaleDetail;
import com.example.orbix_web.models.SalesInvoice;
import com.example.orbix_web.models.SalesInvoiceDetail;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.SaleDetailRepository;
import com.example.orbix_web.repositories.SaleRepository;
import com.example.orbix_web.repositories.SalesInvoiceDetailRepository;
import com.example.orbix_web.repositories.SalesInvoiceRepository;
import com.example.orbix_web.repositories.StockCardRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SalesInvoiceServiceController {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	SalesInvoiceRepository salesInvoiceRepository;
	@Autowired
	SalesInvoiceDetailRepository salesInvoiceDetailRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	StockCardRepository stockCardRepository;
	@Autowired
	SaleRepository saleRepository;
	@Autowired
	SaleDetailRepository saleDetailRepository;
	
	
	// Create a new Invoice
    @RequestMapping(method = RequestMethod.POST, value = "/sales_invoices")
    @ResponseBody
    @Transactional
	public SalesInvoice createCustomerInvoice(@Valid @RequestBody SalesInvoice inv ) {
    	/*
    	 * Define field variables
    	 */
    	SalesInvoice invoice;
    	SalesInvoice _invoice;
    	List<SalesInvoiceDetail> details;
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
		invoice = salesInvoiceRepository.findByInvoiceNo(_invoice.getInvoiceNo());
		if(invoice != null) {
			throw new DuplicateEntryException("Could not save invoice, invoice exists");
		}
		if(_invoice.getInvoiceDate() == null) {
			throw new MissingInformationException("Could not process invoice, invoice date required");
		}
		for(SalesInvoiceDetail _detail :details) {
			if(_detail.getItemCode().equals("")) {
				
			}
		}
		if(details.isEmpty() == true) {
			throw new InvalidOperationException("Could not save an empty invoice");
		}
		
		/*
		 * Save invoice data
		 */
		invoice = new SalesInvoice();		
		invoice.setInvoiceNo(String.valueOf(Math.random()));
		invoice.setInvoiceDate(_invoice.getInvoiceDate());
		invoice.setTerms(_invoice.getTerms());
		invoice.setOrderNo(_invoice.getOrderNo());
		invoice.setDateShipped(_invoice.getDateShipped());
		invoice.setShippedVia(_invoice.getShippedVia());
		invoice.setCustomer(customer);	
		invoice.setInvoiceStatus("SENT");
		invoice = salesInvoiceRepository.save(invoice);
		String serial = invoice.getId().toString();
    	String invoiceNo = "CINV-"+Formater.formatNine(serial);
    	invoice.setInvoiceNo(invoiceNo);
    	salesInvoiceRepository.save(invoice);
		
		
		
		double amount = 0;
		for(SalesInvoiceDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
			LocalDate returnStartDate = _invoice.getInvoiceDate();
			int returnPeriod = 365;//_detail.getReturnPeriod();  //change this later
						
			LocalDate returnLastDate = returnStartDate.plusDays(returnPeriod);
			
			SalesInvoiceDetail detail = new SalesInvoiceDetail();
			detail.setSalesInvoice(invoice);
			detail.setItemCode(_detail.getItemCode());
			detail.setDescription(_detail.getDescription());
			detail.setPrice(_detail.getPrice());
			detail.setReturnPeriod(_detail.getReturnPeriod());
			detail.setReturnLastDate(returnLastDate);
			if(_item.getUnitRetailPrice() > _detail.getPrice()) {
				detail.setDiscount(_item.getUnitRetailPrice() - _detail.getPrice());
			}else {
				detail.setDiscount(0);
			}
			detail.setQty(_detail.getQty());
			amount = amount + (_detail.getPrice()*_detail.getQty());
			salesInvoiceDetailRepository.save(detail);
		}
		invoice.setInvoiceAmount(amount);
		invoice.setInvoiceAmountDue(amount);
		invoice = salesInvoiceRepository.save(invoice);
		customer.setOutstandingBalance(customer.getOutstandingBalance()+amount);
		customerRepository.save(customer);
		/*
		 * Update stock
		 */
		for(SalesInvoiceDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
			itemRepository.saveAndFlush(
					new ItemServiceController()
					.deductFromStock(_item, _detail.getQty()));
		}
		/*
		 * Update stock cards
		 */
		for(SalesInvoiceDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
			double _stockBalance =_item.getQuantity();
			stockCardRepository.save(
					new StockCardServiceController()
					.creditSale(_item, _invoice.getInvoiceDate(), _detail.getQty(), invoiceNo, _stockBalance));	
		}
		/*
    	 * Now post invoice to sales
    	 */
    	Sale sale = new Sale();
    	//sale.setCustomerInvoice(invoice);
    	sale.setSaleDate(invoice.getInvoiceDate());
    	saleRepository.save(sale);
    	
    	//List<CustomerInvoiceDetail> invoiceDetails;
    	//invoiceDetails = invoice.getInvoiceDetails();
    	for(SalesInvoiceDetail _detail :details) {
    		SaleDetail saleDetail = new SaleDetail();
    		saleDetail.setItemCode(_detail.getItemCode());
    		saleDetail.setDescription(_detail.getDescription());
    		saleDetail.setPrice(_detail.getPrice());
    		saleDetail.setQty(_detail.getQty());
    		saleDetail.setDiscount(_detail.getDiscount());
    		saleDetail.setSale(sale);
    		
    		saleDetailRepository.save(saleDetail);
    		
    	}
		/*
		 * Return invoice with details
		 */
		invoice.setInvoiceDetails(details);
		return invoice;
	}
    
 // Get a Single Customer Invoice by invoice no
    @RequestMapping(method = RequestMethod.GET, value = "/sales_invoices/invoice_no={invoice_no}")
    public SalesInvoice getInvoiceByInvoiceNo(@PathVariable(value = "invoice_no") String invoiceNo) {
    	SalesInvoice invoice = salesInvoiceRepository.findByInvoiceNo(invoiceNo);
    	if(invoice == null) {
    		throw new NotFoundException("Invoice not found");
    	}
        return invoice;
    }
    
 // Get a Single Customer Invoice by invoice no and customer no
    @RequestMapping(method = RequestMethod.GET, value = "/sales_invoices/invoice_no={invoice_no}/customer_no={customer_no}")
    public SalesInvoice getInvoiceByInvoiceNoAndCustomerNo(@PathVariable(value = "invoice_no") String invoiceNo,@PathVariable(value = "customer_no") String customerNo) {
    	SalesInvoice invoice = salesInvoiceRepository.findByInvoiceNo(invoiceNo);
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
    
 // Get a Single Customer Invoice by invoice no
    @RequestMapping(method = RequestMethod.GET, value = "/due_sales_invoices/customer_id={cust_id}")
    public List<SalesInvoice> getDueInvoiceByCustomer(@PathVariable(value = "cust_id") Long custId) {
    	System.out.println("check");
    	Customer customer = customerRepository.findById(custId).get();
    	
    	List<SalesInvoice> invoices = salesInvoiceRepository.findByCustomerAndInvoiceStatus(customer, "PENDING");
    	
        return invoices;
    }


}
