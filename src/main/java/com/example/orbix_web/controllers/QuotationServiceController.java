/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Quotation;
import com.example.orbix_web.models.QuotationDetail;
import com.example.orbix_web.models.Sale;
import com.example.orbix_web.models.SaleDetail;
import com.example.orbix_web.models.SalesInvoice;
import com.example.orbix_web.models.SalesInvoiceDetail;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.QuotationDetailRepository;
import com.example.orbix_web.repositories.QuotationRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuotationServiceController {
	@Autowired
    QuotationRepository quotationRepository;
	@Autowired
    QuotationDetailRepository quotationDetailRepository;
	@Autowired
    ItemRepository itemRepository;
	
	// Get All Quotations
    @RequestMapping(method = RequestMethod.GET, value = "/quotations")
    public List<Quotation> getAllQuotations() {
        return quotationRepository.findAll();
    }
    
    /**
     * get quotation by quotationNo
     * @param quotationNo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/quotations/quotation_no={quotation_no}")
    @Transactional
    public Quotation getQuotationByQuotationNo(@PathVariable(value = "quotation_no") String quotationNo) {
    	Quotation quotation = quotationRepository.findByQuotationNo(quotationNo);
    	if(quotation == null) {
    		throw new NotFoundException("Quotation not found");
    	}
        return quotation;
    }
	
	//Create a new quotation
	@RequestMapping(method = RequestMethod.POST, value = "/quotations/create")
    @ResponseBody
    @Transactional
    public Quotation createQuotation(@Valid @RequestBody Quotation quot) {
		/*
		 * Define field variables
		 */
		Quotation quotation;
		Quotation _quotation;
		List<QuotationDetail> details;
		/*
		 * Capture quotation data
		 */
		_quotation = quot;
		details = _quotation.getQuotationDetails();
		/*
		 * Validate quotation data
		 */
		quotation = quotationRepository.findByQuotationNo(_quotation.getQuotationNo());
		if(quotation != null) {
			throw new DuplicateEntryException("Could not save quotation, quotation exists");
		}
		if(_quotation.getIssueDate() == null) {
			throw new MissingInformationException("Could not process quotation, issue date required");
		}
		if(_quotation.getExpiryDate() == null) {
			throw new MissingInformationException("Could not process quotation, expiry date required");
		}
		if(_quotation.getCustomerName().equals("") || _quotation.getCustomerAddress().equals("") || _quotation.getCustomerTelephone().equals("")) {
			throw new MissingInformationException("Could not process quotation, incomplete customer information");
		}
		for(QuotationDetail _detail :details) {
			if(_detail.getItemCode().equals("")) {
				throw new InvalidOperationException("Could not save an empty quotation");
			}
		}
		if(details.isEmpty() == true) {
			throw new InvalidOperationException("Could not save an empty quotation");
		}
		
		
		/*
		 * Save quotation data
		 */
		quotation = new Quotation();		
		quotation.setQuotationNo(String.valueOf(Math.random()));
		quotation.setIssueDate(_quotation.getIssueDate());
		quotation.setExpiryDate(_quotation.getExpiryDate());
		quotation.setCustomerName(_quotation.getCustomerName());
		quotation.setCustomerAddress(_quotation.getCustomerAddress());
		quotation.setCustomerTelephone(_quotation.getCustomerTelephone());
		quotation.setCustomerEmail(_quotation.getCustomerEmail());
		quotation.setCustomerFax(_quotation.getCustomerFax());
		quotation.setStatus("NEW");
		
		quotation = quotationRepository.save(quotation);
		String serial = quotation.getId().toString();
    	String quotationNo = "QUT-"+Formater.formatNine(serial);
    	quotation.setQuotationNo(quotationNo);
    	quotationRepository.save(quotation);
    	
    	
    	double amount = 0;
		for(QuotationDetail _detail :details) {
			Item _item =itemRepository.findByItemCode(_detail.getItemCode()).get();
						
			
			QuotationDetail detail = new QuotationDetail();
			detail.setQuotation(quotation);
			detail.setItemCode(_detail.getItemCode());
			detail.setDescription(_detail.getDescription());
			detail.setPrice(_detail.getPrice());
			detail.setTax(_detail.getTax());
			if(_item.getUnitRetailPrice() > _detail.getPrice()) {
				detail.setDiscount(_item.getUnitRetailPrice() - _detail.getPrice());
			}else {
				detail.setDiscount(0);
			}
			detail.setQty(_detail.getQty());
			amount = amount + (_detail.getPrice()*_detail.getQty());
			quotationDetailRepository.save(detail);
		}
		
		return quotation;
	}
	
	/**
     * 
     * @param quotationId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/quotations/{id}", produces = "text/html")
    public ResponseEntity<?> deleteQuotation(@PathVariable(value = "id") Long quotationId) {
    	Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new NotFoundException("Quotation not found"));
    	try {
    		quotationRepository.delete(quotation);
    		return ResponseEntity.ok().build();
    	}catch(Exception ex) {
    		return new ResponseEntity<>("Could not delete quotation: "+ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
    	}
    }
	
}
