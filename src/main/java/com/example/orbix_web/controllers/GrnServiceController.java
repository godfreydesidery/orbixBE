/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ArrayUtils;

import com.example.orbix_web.accessories.Formater;
import com.example.orbix_web.exceptions.InvalidEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.OperationFailedException;
import com.example.orbix_web.models.Day;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.GrnDetail;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;
import com.example.orbix_web.models.StockCard;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.User;
import com.example.orbix_web.repositories.DayRepository;
import com.example.orbix_web.repositories.GrnDetailRepository;
import com.example.orbix_web.repositories.GrnRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.LpoDetailRepository;
import com.example.orbix_web.repositories.LpoRepository;
import com.example.orbix_web.repositories.StockCardRepository;
import com.example.orbix_web.repositories.UserRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GrnServiceController {
	
	@Autowired
    GrnRepository grnRepository;
	@Autowired
    GrnDetailRepository grnDetailRepository;
	@Autowired
    LpoRepository lpoRepository;
	@Autowired
    LpoDetailRepository lpoDetailRepository;
	@Autowired
    UserRepository userRepository;
	@Autowired
    ItemRepository itemRepository;
	@Autowired
    StockCardRepository stockCardRepository;
	@Autowired
    DayRepository dayRepository;
	
	// Get All GRNs
    @RequestMapping(method = RequestMethod.GET, value = "/grns")
    public List<Grn> getAllGrns() {
        return grnRepository.findAll();
    }
    /**
     *  Create a new GRN from order details if it does not exist
     *  return grn with grn details
     */
    @RequestMapping(method = RequestMethod.POST, value = "/grns")
    @ResponseBody
    @Transactional
    public Grn createGrn(@Valid @RequestBody Grn grn) {
    	Lpo lpo;
    	User user;
    	String orderType = grn.getOrderType();
    	String orderNo = "";
    	Grn _grn = null;
    	String[] orderTypes = {
    			"LOCAL PURCHASE ORDER",
    			"TRANSFER ORDER"
    			};
    	if(ArrayUtils.contains(orderTypes, orderType) == false) {
    		throw new InvalidEntryException("Invalid order type. Only orders of the type "+Arrays.toString(orderTypes)+" are allowed!");
    	}
    	if(orderType.equals("LOCAL PURCHASE ORDER")) {
    		try {
    		String lpoNo = (grn.getOrderNo());
    		lpo = lpoRepository.findByLpoNo(lpoNo).get();
	    	lpoRepository.save(lpo);
	    	grn.setLpo(lpo);
	    	orderNo = lpoNo;
	    	}catch(Exception e) {
	    		throw new NotFoundException("Fail to process GRN. Local Purchase Order not found");
	    	}
    	}else if(orderType.equals("TRANSFER ORDER")) {
    		try {
        		String toNo = (grn.getOrderNo());
        		//to = toRepository.findByToNo(toNo).get();
    	    	//toRepository.save(to);
    	    	//grn.setTo(to);
    	    	orderNo = toNo;
    	    	String e = orderTypes[4];
    	    	}catch(Exception e) {
    	    		throw new NotFoundException("Fail to process GRN. Transfer Order not found");
    	    	}
    	}
    	try {
    		Long userId = (grn.getCreatedBy().getId());
    		user = userRepository.findById(userId).get();
	    	userRepository.save(user);
	    	grn.setCreatedBy(user);
    	}catch(Exception e) {
    		grn.setCreatedBy(null);
    	} 
    	
    	
    	//save grn, get grn no and id, create grn detail with grn id
    	grn.setStatus("PENDING");
    	if(grnRepository.existsByOrderNo(orderNo) == false) {
    		
    		Day day = dayRepository.findTopByOrderByIdDesc(); 	
	    	LocalDate systemDate = day.getSystemDate();
	    	LocalDate grnDate =grn.getGrnDate();
	    	if(grnDate == null) {
	    		throw new MissingInformationException("Date required");
	    	}else if(!grnDate.equals(systemDate)) {
	    		throw new InvalidEntryException("Date does not match with System date");
	    	}   	
	    	grn.setGrnDate(systemDate);
	    	grn.setGrnNo(String.valueOf(Math.random()));
	    	grnRepository.save(grn);
	    	String serial = grn.getId().toString();
	    	String grnNo = "GRN-"+Formater.formatNine(serial);
	    	grn.setGrnNo(grnNo);
	    	
    		_grn = grnRepository.save(grn);
    	}else {
    		_grn = grnRepository.findByOrderNo(orderNo).get();
    	}
    	
    	// create grn details
    	if(orderType.equals("LOCAL PURCHASE ORDER")) {
    		Lpo _lpo = lpoRepository.findByLpoNo(orderNo).get();
    		List<LpoDetail> _lpoDetails = lpoDetailRepository.findByLpo(_lpo); 
    		for(LpoDetail _lpoDetail : _lpoDetails) {
    			String _lpoNo =  _lpoDetail.getLpo().getLpoNo();
    			String _itemCode = _lpoDetail.getItemCode();
    			String _description = _lpoDetail.getDescription();
    			double _supplierCP = _lpoDetail.getSupplierCostPrice();
    			double _clientCP = _lpoDetail.getClientCostPrice();
    			double _qtyOrdered = _lpoDetail.getQtyOrdered();
    			double _qtyReceived = _lpoDetail.getQtyReceived();
    			if(grnDetailRepository.existsByOrderNoAndItemCode(_lpoNo, _itemCode) == false) {
    				GrnDetail _grnDetail = new GrnDetail();
    				_grnDetail.setGrn(_grn);
    				_grnDetail.setOrderNo(_lpoNo);
    				_grnDetail.setItemCode(_itemCode);
    				_grnDetail.setDescription(_description);
    				_grnDetail.setSupplierCostPrice(_supplierCP);
    				_grnDetail.setClientCostPrice(_clientCP);
    				_grnDetail.setQtyOrdered(_qtyOrdered);
    				_grnDetail.setQtyReceived(_qtyReceived);
    				_grnDetail.setStatus("PENDING");
    				grnDetailRepository.save(_grnDetail);
    			}
    		}
    	}
        return _grn;
    }
    /**
     * Update the grn details upon receipt
     * @param grnDetails
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/grn_details/{grn_id}")
    @ResponseBody
    @Transactional
    public List<GrnDetail> receiveGrn(@Valid @RequestBody List<GrnDetail> grnDetails, @PathVariable(value = "grn_id") Long grnId) {
    	// advice, first check if the items exist, if not throw not found exception if an inexistence item is received:: later to implement this method
    	Grn _grn = null;
    	Lpo _lpo = null;
    	LocalDate _dateOrdered = null;   	
    	LocalDate _dateReceived = null;
    	String _orderNo = null;
    	String _orderType = null;
    	
    	//
    	for(GrnDetail grnDetail : grnDetails) {
    		
    		if(_grn == null) {
    			_grn = grnRepository.findById(grnId).get();
    		}
    		if(_grn == null) {
    			throw new NotFoundException("Could not receive goods. GRN not found");
    		}    		
    		if(_grn.getOrderType().equals("LOCAL PURCHASE ORDER")) {
    			_lpo = lpoRepository.findByLpoNo(_grn.getOrderNo()).get();
    			_dateOrdered = _lpo.getLpoDate();
    			_dateReceived = _grn.getGrnDate();
    		}
    		
    		String status = _lpo.getStatus();
    		
    		if(status.equals("RECEIVED")) {
    			throw new InvalidOperationException("Can not receive this order. Order already received.");
    		}
    		if(!status.equals("PRINTED")) {
    			if(!status.equals("REPRINTED")) {
    				throw new InvalidOperationException("Can not receive this order. Order not printed.");
    			}
    		}   		
    		//validate entries
    		double _supplierCostPrice = grnDetail.getSupplierCostPrice();
    		double _clientCostPrice = grnDetail.getClientCostPrice();
    		double _qtyOrdered = grnDetail.getQtyOrdered();
    		double _qtyReceived = grnDetail.getQtyReceived();
    		LocalDate _expiryDate = grnDetail.getExpiryDate();  // will be used to validate  
    		Date _currentDate = new Date();
    		if(_expiryDate != null && _expiryDate.isBefore(_dateReceived)) {
    			throw new InvalidEntryException("Can not receive goods!\n"+"("+grnDetail.getItemCode()+") "+grnDetail.getDescription()+" has expired");
    		}
    		
    		String _lotNo = grnDetail.getLotNo();  		
    		if((_clientCostPrice - _supplierCostPrice  >= 0.01) || (_clientCostPrice - _supplierCostPrice  <= -0.1) && _qtyReceived > 0) {
    			throw new InvalidEntryException("Can not receive goods!\nThe supplier cost price does not match client cost price on "+"("+grnDetail.getItemCode()+") "+grnDetail.getDescription());
    		}
    		if(_qtyReceived > _qtyOrdered) {
    			throw new InvalidEntryException("Can not receive goods!\nQuantity received exceeds the quantity ordered on "+"("+grnDetail.getItemCode()+") "+grnDetail.getDescription());
    		}
    		if(_qtyReceived < 0 || _supplierCostPrice < 0) {
    			throw new InvalidEntryException("Invalid entries at "+"("+grnDetail.getItemCode()+") "+grnDetail.getDescription());
    		}
    		if(_qtyReceived > 0 && _supplierCostPrice <= 0) {
    			throw new InvalidEntryException("No cost entered at "+"("+grnDetail.getItemCode()+") "+grnDetail.getDescription());
    		}
    		
    	}
    	if(_grn == null) {
    		throw new NotFoundException("Could not receive goods. GRN not found");
    	}
    	// now commit changes
    	_grn.setStatus("RECEIVED");
    	grnRepository.save(_grn);
    	
    	_orderNo = _grn.getOrderNo();
    	_orderType = _grn.getOrderType();
    	
    	boolean receivedOne = false;// to mark whether at least one item has been received
    	double invoiceTotal = 0;
    	for( GrnDetail grnDetail : grnDetails) {
    		String _itemCode = grnDetail.getItemCode();
    		double _qtyReceived = grnDetail.getQtyReceived();
    		double _supplierCP = grnDetail.getSupplierCostPrice();
    		
    		
    		if(_orderType.equals("LOCAL PURCHASE ORDER")) {
    			Long id = grnDetailRepository.findByItemCodeAndOrderNo(grnDetail.getItemCode(), _orderNo).get().getId();
    			grnDetail.setId(id);
    		}
    		if(grnDetail.getQtyReceived() > 0) {
    			grnDetail.setStatus("RECEIVED");
    			receivedOne = true;
    		}else {
    			continue;
    		}
			grnDetail.setGrn(_grn);	
			grnDetail.setOrderNo(_orderNo);
			grnDetailRepository.saveAndFlush(grnDetail);
			
    		Item _item =itemRepository.findByItemCode(_itemCode).get(); 
    		
    		itemRepository.saveAndFlush(
    				new ItemServiceController()
    				.addToStock(_item, _qtyReceived));
    		
    		_item = itemRepository.findByItemCode(_itemCode).get();
    		double _stockBalance =_item.getQuantity();
    		stockCardRepository.saveAndFlush(
    				new StockCardServiceController()
    				.receiveItem(
    						_item, 
    						grnDetail.getQtyOrdered(), 
    						_dateOrdered, 
    						_qtyReceived, 
    						_dateReceived, 
    						_stockBalance, 
    						grnDetail.getLotNo(),
    						grnDetail.getExpiryDate()));
    		
    		if(_orderType.equals("LOCAL PURCHASE ORDER")) {
    			
    			LocalDate _expiryDate = grnDetail.getExpiryDate();
	    		String _status = grnDetail.getStatus();
	    		String _lotNo = grnDetail.getLotNo();
	    		double _supplierCostPrice = grnDetail.getSupplierCostPrice();
    			
    			LpoDetail _lpoDetail = lpoDetailRepository.findByItemCodeAndOrderNo(_itemCode,_orderNo).get();
    			_lpoDetail.setExpiryDate(_expiryDate);
    			_lpoDetail.setStatus(_status);
    			_lpoDetail.setLotNo(_lotNo);
    			_lpoDetail.setSupplierCostPrice(_supplierCostPrice);
    			_lpoDetail.setQtyReceived(_qtyReceived);    			
    			lpoDetailRepository.saveAndFlush(_lpoDetail);
    		}
    		invoiceTotal = invoiceTotal + (_qtyReceived * _supplierCP);
    	}
    	
    	_grn.setInvoiceTotal(invoiceTotal);
    	grnRepository.save(_grn);
    	
    	if(_orderType.equals("LOCAL PURCHASE ORDER")) {
    		if(receivedOne == true) {
    			_lpo = lpoRepository.findByLpoNo(_orderNo).get();
	    		_lpo.setStatus("RECEIVED");
	    		lpoRepository.saveAndFlush(_lpo);
	    		
    		}else {
    			throw new OperationFailedException("Could not receive this order.\nTo receive an order, you must receive at least one item");
    		}
    		
    	}else if(_orderType.equals("any other order type")) {
    		// add other options
    	}
    	
    	return grnDetails;
    }
    
    // Get a Single GRN
    @RequestMapping(method = RequestMethod.GET, value = "/grns/{id}")
    public Grn getGrnById(@PathVariable(value = "id") Long grnId) {
        return grnRepository.findById(grnId)
                .orElseThrow(() -> new NotFoundException("GRN not found"));
    }
    // Get a Single GRN by grn no
    @RequestMapping(method = RequestMethod.GET, value = "/grns/grn_no={grn_no}")
    public Grn getGrnByGrnNo(@PathVariable(value = "grn_no") String grnNo) {
        return grnRepository.findByGrnNo(grnNo)
                .orElseThrow(() -> new NotFoundException("GRN not found"));
    }
    // Update a GRN
    @RequestMapping(method = RequestMethod.PUT, value = "/grns/{id}", produces = "text/html")
    public ResponseEntity<Object> updateGrn(@PathVariable(value = "id") Long grnId,
                                            @Valid @RequestBody Grn grnDetails) {
    	Grn grn = grnRepository.findById(grnId)
                .orElseThrow(() -> new NotFoundException("GRN not found"));
    	grn = grnDetails;
    	try {
    		grnRepository.save(grn);
    		return new ResponseEntity<>("GRN updated", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not update GRN, "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }    
}
