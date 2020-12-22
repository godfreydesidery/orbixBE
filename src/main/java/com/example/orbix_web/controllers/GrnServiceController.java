/**
 * 
 */
package com.example.orbix_web.controllers;

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

import com.example.orbix_web.exceptions.InvalidEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.GrnDetail;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.User;
import com.example.orbix_web.repositories.GrnDetailRepository;
import com.example.orbix_web.repositories.GrnRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.LpoDetailRepository;
import com.example.orbix_web.repositories.LpoRepository;
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
    	
    	if(orderType.equals("LPO")) {
    		try {
    		String lpoNo = (grn.getOrderNo());
    		lpo = lpoRepository.findByLpoNo(lpoNo).get();
	    	lpoRepository.save(lpo);
	    	grn.setLpo(lpo);
	    	orderNo = lpoNo;
	    	}catch(Exception e) {
	    		throw new NotFoundException("Fail to process GRN. LPO not found");
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
    	grn.setStatus("NOT RECEIVED");
    	if(grnRepository.existsByOrderNo(orderNo) == false) {
    		_grn = grnRepository.save(grn);
    	}else {
    		_grn = grnRepository.findByOrderNo(orderNo).get();
    	}
    	
    	
    	// create grn details
    	if(orderType.equals("LPO")) {
    		Optional<Lpo> _lpo = lpoRepository.findByLpoNo(orderNo);
    		List<LpoDetail> _lpoDetails = lpoDetailRepository.findByLpo(_lpo.get()); 
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
    				_grnDetail.setStatus("NOT RECEIVED");
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
    	for(GrnDetail grnDetail : grnDetails) {
    		if(_grn == null) {
    			_grn = grnRepository.findById(grnId).get();
    		}
    		if(_grn == null) {
    			throw new NotFoundException("Could not receive goods. GRN not found");
    		}
    		if(_grn.getStatus().equals("RECEIVED")) {
    			throw new InvalidOperationException("Can not receive this order. Order already received.");
    		}
    		//validate
    		double supplierCostPrice = grnDetail.getSupplierCostPrice();
    		double clientCostPrice = grnDetail.getClientCostPrice();
    		double qtyOrdered = grnDetail.getQtyOrdered();
    		double qtyReceived = grnDetail.getQtyReceived();
    		if(supplierCostPrice > clientCostPrice && qtyReceived > 0) {
    			throw new InvalidEntryException("Can not receive goods!\nThe supplier cost price is more than client cost price on "+grnDetail.getDescription());
    		}
    		if(qtyReceived > qtyOrdered) {
    			throw new InvalidEntryException("Can not receive goods!\nQuantity received exceeds the quantity ordered on "+grnDetail.getDescription());
    		}
    		if(qtyReceived < 0 || supplierCostPrice < 0) {
    			throw new InvalidEntryException("Invalid entries at "+grnDetail.getDescription());
    		}
    	}
    	if(_grn == null) {
    		throw new NotFoundException("Could not receive goods. GRN not found");
    	}
    	// now commit changes
    	_grn.setStatus("RECEIVED");
    	grnRepository.save(_grn);
    	String _orderNo = "";
    	if(_grn.getOrderType().equals("LPO")) {
    		_orderNo = _grn.getOrderNo();  
    		Lpo _lpo = lpoRepository.findByLpoNo(_orderNo).get();
    		_lpo.setStatus("RECEIVED");
    		lpoRepository.save(_lpo);
    	}// add other options
    	for( GrnDetail grnDetail : grnDetails) {
    		if(_grn.getOrderType().equals("LPO")) {
    			Long id = grnDetailRepository.findByItemCodeAndOrderNo(grnDetail.getItemCode(), _orderNo).get().getId();
    			grnDetail.setId(id);
    		}
    		if(grnDetail.getQtyReceived() == 0) {
    			grnDetail.setStatus("RECEIVED");
    		}			
			grnDetail.setGrn(_grn);
			grnDetail.setOrderNo(_orderNo);
    		String _itemCode = grnDetail.getItemCode();
    		Item _item = itemRepository.findByItemCode(_itemCode).get();
    		double _qty = grnDetail.getQtyReceived();    		
    		grnDetailRepository.save(grnDetail);
    		// //implement this method later
    		ItemServiceController.addToStock(_item, _qty);
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
