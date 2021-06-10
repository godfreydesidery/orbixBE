/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
import java.util.Date;
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
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.CustomerCreditNote;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.Rtv;
import com.example.orbix_web.models.RtvDetail;
import com.example.orbix_web.models.SalesInvoiceDetail;
import com.example.orbix_web.models.SalesReturnDetail;
import com.example.orbix_web.models.StockCard;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.VendorCreditNote;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.RtvDetailRepository;
import com.example.orbix_web.repositories.RtvRepository;
import com.example.orbix_web.repositories.StockCardRepository;
import com.example.orbix_web.repositories.SupplierRepository;
import com.example.orbix_web.repositories.VendorCreditNoteRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RtvServiceController {
	
	@Autowired
    SupplierRepository supplierRepository;
	@Autowired
    RtvRepository rtvRepository;
	@Autowired
    RtvDetailRepository rtvDetailRepository;
	@Autowired
    ItemRepository itemRepository;
	@Autowired
    StockCardRepository stockCardRepository;
	@Autowired
    VendorCreditNoteRepository vendorCreditNoteRepository;
	
	// Get All RTVs
	@Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/rtvs")
    public List<Rtv> getAllRtvs() {
        return rtvRepository.findAll();
    }
	// Get a Single RTV
    @Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/rtvs/{id}")
    public Rtv getRtvById(@PathVariable(value = "id") Long rtvId) {
        return rtvRepository.findById(rtvId)
                .orElseThrow(() -> new NotFoundException("RTV not found"));
    }
    // Get a Single RTV by rtv no
    @Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/rtvs/rtv_no={rtv_no}")
    public Rtv getLpoById(@PathVariable(value = "rtv_no") String rtvNo) {
        return rtvRepository.findByRtvNo(rtvNo)
                .orElseThrow(() -> new NotFoundException("RTV not found"));
    }
    //Create RTV
    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/rtvs")
    @ResponseBody
    public Rtv createRtv(@Valid @RequestBody Rtv _rtv) {
    	System.out.println("Success");
    	//get aand validate rtv
    	Rtv rtv = new Rtv();
    	Supplier supplier;
    	try {
    		String supplierName = (_rtv.getSupplier()).getSupplierName();
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
	    	supplierRepository.save(supplier);
	    	rtv.setSupplier(supplier);
    	}catch(Exception e) {
    		throw new MissingInformationException("Could not process RTV. Supplier required");
    	}
    	List <RtvDetail> rtvDetails = _rtv.getRtvDetails();
    	
    	
    	//validate rtv
    	
    	//validate rtv details
    	int size = rtvDetails.size();
    	String itemList[] = new String[size];
    	int i = 0;
    	for(RtvDetail _rtvDetail : rtvDetails) {
    		Item _item = new Item();
    		_item = itemRepository.findByItemCode(_rtvDetail.getItemCode()).get();
    		if(_item == null) {
    			throw new NotFoundException("Item "+_rtvDetail.getItemCode()+" "+_rtvDetail.getDescription()+" not found");
    		}
    		if(!_item.getSupplier().getSupplierName().equals(rtv.getSupplier().getSupplierName())) {
    			throw new InvalidOperationException("Item "+_rtvDetail.getItemCode()+" "+_rtvDetail.getDescription()+" not available for this supplier");
    		}
    		
    	}
    	//now save rtv
    	rtv.setRtvDate(_rtv.getRtvDate());
    	rtv.setRtvNo("NA");
    	
    	rtv = rtvRepository.save(rtv);
    	String serial1 = rtv.getId().toString();
    	String rtvNo = "RTV-"+Formater.formatNine(serial1);
    	rtv.setRtvNo(rtvNo);
    	
    	rtv = rtvRepository.save(rtv);
    	
    	double balance = 0;
    	for(RtvDetail _rtvDetail : rtvDetails) {
    		String itemCode = _rtvDetail.getItemCode();
    		String description = _rtvDetail.getItemCode(); // can be changed later to match invoice description
    		double qtyReturned = _rtvDetail.getQty();
    		LocalDate date = null;//salesReturn.getReturnDate();
    		String reason = _rtvDetail.getReason();
    		//add return detail
    		RtvDetail detail = new RtvDetail();
    		detail.setRtv(rtv);
    		detail.setItemCode(itemCode);
    		detail.setDescription(description);
    		detail.setQty(qtyReturned);
    		detail.setReason(reason);
    		rtvDetailRepository.save(detail);
    		
    		Item item;
    		//update inventory
    		item = itemRepository.findByItemCode(itemCode).get();
    		item.setQuantity(item.getQuantity() - qtyReturned);
    		itemRepository.save(item);
    		
    		balance = balance + (qtyReturned * item.getUnitCostPrice());//set credit note balance
    		
    		//update stock cards
    		item = itemRepository.findByItemCode(itemCode).get();
    		double _stockBalance =item.getQuantity();
    		stockCardRepository.save(
    				new StockCardServiceController()
	    			.qtyOut(
	    				LocalDate.now(), 
						item, 
						qtyReturned, 
						_stockBalance, 
						"Return to vendor"));
	    		
    		
    	}
    	//create customer credit note
    	VendorCreditNote crNote = (new VendorCreditNoteServiceController()).newCreditNote(rtv.getRtvNo(), rtv.getRtvDate(), balance, balance);
    	crNote.setCreditNoteNo(String.valueOf(Math.random()));
    	crNote = vendorCreditNoteRepository.save(crNote);
    	String serial = crNote.getId().toString();
    	String crNoteNo = "VCN-"+Formater.formatNine(serial);
    	crNote.setCreditNoteNo(crNoteNo);
    	
    	return rtv;
    	
    }
    
	// Create a new RTV
	@Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/rtvss")
    @ResponseBody
	public Rtv createRtvvvv(@Valid @RequestBody Rtv rtv) {	
    	Supplier supplier;
    	try {
    		String supplierName = (rtv.getSupplier()).getSupplierName();
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
	    	supplierRepository.save(supplier);
	    	rtv.setSupplier(supplier);
    	}catch(Exception e) {
    		throw new MissingInformationException("Could not process RTV. Supplier required");
    	}
    	rtv.setStatus("PENDING");
    	//validate inputs
    	if(rtv.getRtvDate() == null) {
    		throw new MissingInformationException("Could not process RTV. RTV Date required");
    	}
    	return rtvRepository.saveAndFlush(rtv);
	}
    
    // Update a RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/{id}", produces = "text/html")
    public ResponseEntity<Object> updateRtv(@PathVariable(value = "id") Long rtvId, @Valid @RequestBody Rtv rtvDetails){
    	Rtv rtv = rtvRepository.findById(rtvId)
                .orElseThrow(() -> new NotFoundException("RTV not found"));
    	Supplier _supplier = supplierRepository.findBySupplierName(
    			rtvDetails
    			.getSupplier()
    			.getSupplierName())
    			.get();
    	if(!rtv.getSupplier().equals(_supplier)) {
    		throw new InvalidOperationException("Editing supplier information on a pending RTV is not allowed");
    	}
    	rtv = rtvDetails;
    	try {
    		rtvRepository.save(rtv);
    		return new ResponseEntity<>("RTV updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not update RTV, "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
    //Approve RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/approve/{id}")
    public ResponseEntity<Object> approveRtv(@PathVariable(value = "id") Long rtvId){
    	Rtv rtv = rtvRepository.findById(rtvId)
    			.orElseThrow(() -> new NotFoundException("RTV not found"));
		String status = rtv.getStatus();
		if(status.equals("PENDING")) {
			rtv.setStatus("APPROVED");
			rtvRepository.saveAndFlush(rtv);
			return new ResponseEntity<>("RTV approved", HttpStatus.OK);
		}else if(status.equals("APPROVED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already approved.");
		}else if(status.equals("CANCELED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV has been canceled."); 
		}else if(status.equals("COMPLETED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already completed."); 
		}else {
			throw new InvalidOperationException("Could not approve RTV. RTV status unknown."); 
		}
    }
    
    //Complete RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/complete/{id}")
    public ResponseEntity<Object> completeRtv(@PathVariable(value = "id") Long rtvId){
		System.out.println("Success");
    	Rtv rtv = rtvRepository.findById(rtvId)
    			.orElseThrow(() -> new NotFoundException("RTV not found"));
		String status = rtv.getStatus();
		if(status.equals("APPROVED")) {
			//now complete details
			List<RtvDetail> _rtvDetails = rtvDetailRepository.findByRtv(rtv);
			for(RtvDetail _rtvDetail : _rtvDetails) {
				Item _item = itemRepository.findByItemCode(_rtvDetail.getItemCode()).get();
				double _qty = _rtvDetail.getQty();
				//remove item from stock
				itemRepository.saveAndFlush(
	    				new ItemServiceController()
	    				.deductFromStock(_item, _qty));
				//register in stock card
				_item = itemRepository.findByItemCode(_rtvDetail.getItemCode()).get();
				stockCardRepository.saveAndFlush(
						new StockCardServiceController()
	    				.qtyOut(
	    						LocalDate.now(), 
	    						_item, 
	    						_rtvDetail.getQty(), 
	    						_item.getQuantity(), 
	    						"Returned to vendor"));
						
			}
			rtv.setStatus("COMPLETED");
			rtvRepository.saveAndFlush(rtv);
			return new ResponseEntity<>("RTV completed", HttpStatus.OK);
		}else if(status.equals("PENDING")) {
			throw new InvalidOperationException("Could not approve RTV. RTV not approved.");
		}else if(status.equals("CANCELED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV has been canceled."); 
		}else if(status.equals("COMPLETED")) {
			throw new InvalidOperationException("Could not approve RTV. RTV already completed."); 
		}else {
			throw new InvalidOperationException("Could not approve RTV. RTV status unknown."); 
		}
    }
    //Cancel RTV
	@Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/rtvs/cancel/{id}")
    public Rtv cancelRtv(@PathVariable(value = "id") Long rtvId){
    	Rtv rtv = rtvRepository.findById(rtvId)
    			.orElseThrow(() -> new NotFoundException("RTV not found"));
		String status = rtv.getStatus();
		if(status.equals("PENDING") || status.equals("APPROVED")) {
			rtv.setStatus("CANCELED");
			return rtvRepository.saveAndFlush(rtv);
		}else if(status.equals("COMPLETED")) {
			throw new InvalidOperationException("Could not cancel RTV. RTV already completed.");
		}else {
			throw new InvalidOperationException("Could not cancel RTV. RTV status unknown."); 
		}
    }
    
    // Delete RTV
	@Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/rtvs/{id}", produces = "text/html")
    public ResponseEntity<?> deleteRtv(@PathVariable(value = "id") Long rtvId) {
    	Rtv rtv = rtvRepository.findById(rtvId)
                .orElseThrow(() -> new NotFoundException("RTV not found"));
    	try {
    		String status = rtv.getStatus();
    		if(status.equals("COMPLETED") || status.equals("APPROVED")) {
    			throw new InvalidOperationException("Could not delete, you can only delete a pending or canceled RTV.\nTo delete, first cancel the RTV.");
    		}else {
    			rtvRepository.delete(rtv);
    			return new ResponseEntity<>("RTV deleted successifully.", HttpStatus.OK);
    		}
    	}catch(Exception ex) {
    		throw new InvalidOperationException("Could not delete RTV: "+ex.getMessage());
    	}    	
    }
                                            
}
