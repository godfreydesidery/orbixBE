/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
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

import com.example.orbix_web.accessories.Formater;
import com.example.orbix_web.exceptions.InvalidEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.CustomerCreditNote;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.LpoDetail;
import com.example.orbix_web.models.SalesInvoice;
import com.example.orbix_web.models.SalesInvoiceDetail;
import com.example.orbix_web.models.SalesReturn;
import com.example.orbix_web.models.SalesReturnDetail;
import com.example.orbix_web.repositories.CustomerCreditNoteRepository;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.SaleDetailRepository;
import com.example.orbix_web.repositories.SaleRepository;
import com.example.orbix_web.repositories.SalesInvoiceDetailRepository;
import com.example.orbix_web.repositories.SalesInvoiceRepository;
import com.example.orbix_web.repositories.SalesReturnDetailRepository;
import com.example.orbix_web.repositories.SalesReturnRepository;
import com.example.orbix_web.repositories.StockCardRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SalesReturnServiceController {
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
	@Autowired
	SalesReturnRepository salesReturnRepository;
	@Autowired
	SalesReturnDetailRepository salesReturnDetailRepository;
	@Autowired
	CustomerCreditNoteRepository customerCreditNoteRepository;
	
	// Create a new Invoice
	@RequestMapping(method = RequestMethod.POST, value = "/sales_returns")
    @ResponseBody
    @Transactional
	public SalesReturn createSalesReturn(@Valid @RequestBody SalesReturn ret ) {
		SalesReturn salesReturn = new SalesReturn();
    	//Validate sale return
		if(ret.getSalesInvoice().getInvoiceNo().equals("")) {
    		throw new MissingInformationException("Invoice Required");
    	}
    	if(ret.getSalesReturnDetails() == null) {
    		throw new MissingInformationException("Items required");
    	}    	
    	SalesInvoice invoice = salesInvoiceRepository.findByInvoiceNo(ret.getSalesInvoice().getInvoiceNo());
    	if(invoice == null) {
    		throw new NotFoundException("Invoice not found");
    	}
    	//if(invoice.getInvoiceDate().isAfter(ret.getReturnDate())) {
    		//throw new InvalidEntryException("Invalid date. Return date must be after Invoice date");
    	//}
    	//salesReturn.setReturnDate(ret.getReturnDate());
    	//sales
    	List<SalesReturnDetail> salesReturnDetails = ret.getSalesReturnDetails();
    	List<SalesInvoiceDetail> salesInvoiceDetails = salesInvoiceDetailRepository.findBySalesInvoice(invoice);
    	//validate sale return
    	
    	//validate sale return details
    	int size = salesReturnDetails.size();
    	String itemList[] = new String[size];
    	int i = 0;
    	for(SalesReturnDetail _salesReturnDetail : salesReturnDetails) {
    		boolean isPresent = false;
    		for(SalesInvoiceDetail _salesInvoiceDetail : salesInvoiceDetails) {
        		if(_salesReturnDetail.getItemCode().equals(_salesInvoiceDetail.getItemCode())) {
        			itemList[i] = _salesReturnDetail.getItemCode();
        			int count = 0;
        			for(int j = 0; j < itemList.length; j++) {
        				if(_salesReturnDetail.getItemCode().equals(itemList[j])) {
        					count++;
        					if(count > 1) {
        						throw new InvalidOperationException("Could not return, duplicate items. "+_salesReturnDetail.getItemCode()+" "+_salesReturnDetail.getDescription());
        					}
        				}
        			}
        			isPresent = true;
        			if(_salesReturnDetail.getQtyReturned() > (_salesInvoiceDetail.getQty()-_salesInvoiceDetail.getQtyReturned())) {
        				throw new InvalidOperationException("Could not return, Qty returned exceeds Qty balance in "+_salesInvoiceDetail.getItemCode()+" "+_salesInvoiceDetail.getDescription());
        			}
        		}
        		
        	}
    		i++;
    		if(isPresent == false) {
    			throw new NotFoundException("Invoice does not contain item "+_salesReturnDetail.getItemCode()+" "+_salesReturnDetail.getDescription());
    		}
    	}
    	//now save sale return
    	salesReturn.setReturnDate(ret.getReturnDate());
    	salesReturn = salesReturnRepository.save(salesReturn);
    	
    	double balance = 0;
    	for(SalesReturnDetail _salesReturnDetail : salesReturnDetails) {
    		String itemCode = _salesReturnDetail.getItemCode();
    		String description = _salesReturnDetail.getItemCode(); // can be changed later to match invoice description
    		double qtyReturned = _salesReturnDetail.getQtyReturned();
    		LocalDate date = null;//salesReturn.getReturnDate();
    		String reason = _salesReturnDetail.getReason();
    		//add return detail
    		SalesReturnDetail detail = new SalesReturnDetail();
    		detail.setSalesReturn(salesReturn);
    		detail.setItemCode(itemCode);
    		detail.setDescription(description);
    		detail.setQtyReturned(qtyReturned);
    		detail.setReason(reason);
    		salesReturnDetailRepository.save(detail);
    		
    		//update sales invoice by incrementing qty returned
    		SalesInvoiceDetail invoiceDetail = salesInvoiceDetailRepository.findBySalesInvoiceAndItemCode(invoice, itemCode);
    		invoiceDetail.setQtyReturned(invoiceDetail.getQtyReturned() + qtyReturned);
    		salesInvoiceDetailRepository.save(invoiceDetail);
    		balance = balance + (qtyReturned * invoiceDetail.getPrice());//set credit note balance
    		Item item;
    		//update inventory
    		item = itemRepository.findByItemCode(itemCode).get();
    		item.setQuantity(item.getQuantity() + qtyReturned);
    		itemRepository.save(item);
    		
    		//update stock cards
    		item = itemRepository.findByItemCode(itemCode).get();
    		double _stockBalance =item.getQuantity();
    		stockCardRepository.save(
    				new StockCardServiceController()
	    			.qtyIn(
	    				LocalDate.now(), 
						item, 
						qtyReturned, 
						_stockBalance, 
						"Sales Returns"));
	    		
    		
    	}
    	//create customer credit note
    	CustomerCreditNote crNote = (new CustomerCreditNoteServiceController()).newCreditNote(invoice.getInvoiceNo(), "", ret.getReturnDate(), balance, balance);
    	crNote.setCreditNoteNo(String.valueOf(Math.random()));
    	crNote = customerCreditNoteRepository.save(crNote);
    	String serial = crNote.getId().toString();
    	String crNoteNo = "CCN-"+Formater.formatNine(serial);
    	crNote.setCreditNoteNo(crNoteNo);
    	
    	return ret;
    }

}
