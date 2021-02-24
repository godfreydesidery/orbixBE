/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.example.orbix_web.exceptions.DuplicateEntryException;
import com.example.orbix_web.exceptions.InvalidEntryException;
import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Clas;
import com.example.orbix_web.models.CostPriceChange;
import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.SellingPriceChange;
import com.example.orbix_web.models.StockCard;
import com.example.orbix_web.models.SubClass;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.ClasRepository;
import com.example.orbix_web.repositories.CostPriceChangeRepository;
import com.example.orbix_web.repositories.DepartmentRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.SellingPriceChangeRepository;
import com.example.orbix_web.repositories.StockCardRepository;
import com.example.orbix_web.repositories.SubClassRepository;
import com.example.orbix_web.repositories.SupplierRepository;


/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemServiceController {
	
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ClasRepository clasRepository;
    @Autowired
    SubClassRepository subClassRepository;
    @Autowired
    StockCardRepository stockCardRepository;
    @Autowired
    SellingPriceChangeRepository sellingPriceChangeRepository;
    @Autowired
    CostPriceChangeRepository costPriceChangeRepository;
    
    /**
     * 
     * @return all items
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    /**
     * 
     * @return array of items' long descriptions
     */
    
    //@Transactional
    @RequestMapping(method = RequestMethod.GET, value="/items/long_descriptions")
    public Iterable<Item> getAllItemsByLongDescription() {
        return itemRepository.getLongDescription();
    }

    /**
     * create a new item
     * @param item
     * @return item created
     * @throws Exception 
     */
    @RequestMapping(method = RequestMethod.POST, value="/items")
    @ResponseBody
    public Item createItem(@Valid @RequestBody Item item ) throws Exception {
    	Supplier supplier;
    	
    	try {
    		String supplierName = (item.getSupplier()).getSupplierName();
    		
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
    		supplier.setSupplierName(supplierName);
	    	supplierRepository.save(supplier);
	    	item.setSupplier(supplier);
    	}catch(Exception e) {
    		item.setSupplier(null);
    	}
    	Department department;
    	try {
    		String departmentName = (item.getDepartment()).getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
    		department.setDepartmentName(departmentName);
	    	departmentRepository.save(department);
	    	item.setDepartment(department);
    	}catch(Exception e) {
    		item.setDepartment(null);
    	}
    	Clas clas;
    	try {
    		String clasName = (item.getClas()).getClasName();
    		clas = clasRepository.findByClasName(clasName).get();
    		clas.setClasName(clasName);
	    	clasRepository.save(clas);
	    	item.setClas(clas);
    	}catch(Exception e) {
    		item.setClas(null);
    	}
    	SubClass subClass;
    	try {
    		String subClassName = (item.getSubClass()).getSubClassName();
    		subClass = subClassRepository.findBySubClassName(subClassName).get();
    		subClass.setSubClassName(subClassName);
    		subClassRepository.save(subClass);
	    	item.setSubClass(subClass);
    	}catch(Exception e) {
    		item.setSubClass(null);
    	}
    	this.validateBarcode(item.getPrimaryBarcode());
    	this.validateItemCode(item.getItemCode());
    	this.validateLongDescription(item.getLongDescription());
    	this.validateInputs(item);
    	item.setUnitCostPrice((long)Math.round((double)item.getUnitCostPrice()*100)/100.00);
    	item.setUnitRetailPrice((long)Math.round((double)item.getUnitRetailPrice()*100)/100.00);
    	
    	itemRepository.save(item);
    	
    	stockCardRepository.saveAndFlush(
    			new StockCardServiceController()
    			.initialStock(item,  item.getQuantity()));
    	
    	return item;
    }
    /**
     * get a single item by id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items/{id}")
    @Transactional
    public Item getItemById(@PathVariable(value = "id") Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }  
    /**
     * get item by barcode
     * @param primaryBarcode
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items/primary_barcode={primary_barcode}")
    @Transactional
    public Item getItemByPrimaryBarcode(@PathVariable(value = "primary_barcode") String primaryBarcode) {
        return itemRepository.findByPrimaryBarcode(primaryBarcode)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }
    /**
     * get item by itemcode
     * @param primaryBarcode
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items/item_code={item_code}")
    @Transactional
    public Item getItemByItemCode(@PathVariable(value = "item_code") String itemCode) {
        return itemRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }
    /**
     * get item by long description
     * @param primaryBarcode
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items/**/long_description")
    @Transactional
    public Item getItemByLongDescription(HttpServletRequest request) {
    	
    	String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String apiPath = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
        String longDescription = apiPath.substring(0, apiPath.length() - "/long_description".length());

    	
        return itemRepository.findByLongDescription(longDescription)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }
    
    
    //this is a template to allow slash in item description
    @RequestMapping(value = "items/**/receipt", method = RequestMethod.GET)
    @ResponseBody
    public String generateReceipt(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String apiPath = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
        String neededPathVariableValue = apiPath.substring(0, apiPath.length() - "/receipt".length());

        return neededPathVariableValue;
    }
    
    /**
     * 
     * @param itemId
     * @param itemDetails
     * @return
     * @throws Exception 
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/items/{id}", produces = "text/html")
    public ResponseEntity<Object> updateItem(@PathVariable(value = "id") Long itemId,
                                            @Valid @RequestBody Item itemDetails) throws Exception {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        double oldSellingPrice =item.getUnitRetailPrice();
        double oldCostPrice =item.getUnitCostPrice();
        
        this.checkItemCode(itemDetails.getItemCode(), item);
        
        item = itemDetails;
        this.validateInputs(item);
    	Supplier supplier;
    	try {
    		String supplierName = (item.getSupplier()).getSupplierName();
    		supplier = supplierRepository.findBySupplierName(supplierName).get();
    		supplier.setSupplierName(supplierName);
	    	supplierRepository.save(supplier);
	    	item.setSupplier(supplier);
    	}catch(Exception e) {
    		item.setSupplier(null);
    	}
    	Department department;
    	try {
    		String departmentName = (item.getDepartment()).getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
    		department.setDepartmentName(departmentName);
	    	departmentRepository.save(department);
	    	item.setDepartment(department);
    	}catch(Exception e) {
    		item.setDepartment(null);
    	}
    	Clas clas;
    	try {
    		String clasName = (item.getClas()).getClasName();
    		clas = clasRepository.findByClasName(clasName).get();
    		clas.setClasName(clasName);
	    	clasRepository.save(clas);
	    	item.setClas(clas);
    	}catch(Exception e) {
    		item.setClas(null);
    	}
    	SubClass subClass;
    	try {
    		String subClassName = (item.getSubClass()).getSubClassName();
    		subClass = subClassRepository.findBySubClassName(subClassName).get();
    		subClass.setSubClassName(subClassName);
    		subClassRepository.save(subClass);
	    	item.setSubClass(subClass);
    	}catch(Exception e) {
    		item.setSubClass(null);
    	}
    	this.validateInputs(item);
    	item.setUnitCostPrice((long)Math.round((double)item.getUnitCostPrice()*100)/100.00);
    	item.setUnitRetailPrice((long)Math.round((double)item.getUnitRetailPrice()*100)/100.00);
    	try {
    		
    		itemRepository.save(item);
    		this.registerSellingPriceChange(item, oldSellingPrice, "Item update");
    		this.registerCostPriceChange(item, oldCostPrice, "Item update");
    		return new ResponseEntity<>("Item Updated successifully", HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Could not update item, "+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    	}
    }
    /**
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/items/{id}", produces = "text/html")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") Long itemId) {
    	Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    	this.checkUsageBeforeDelete(item);
    	try {
    		itemRepository.delete(item);
    		return ResponseEntity.ok().build();
    	}catch(Exception ex) {
    		return new ResponseEntity<>("Could not delete item: "+ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
    	}
    }
    public Item addToStock(Item item, double qty) {
    	item.setQuantity(item.getQuantity() + qty);
    	return item;
    }
    public Item deductFromStock(Item item, double qty) {
    	item.setQuantity(item.getQuantity() - qty);
    	return item;
    }
    
    
    /**
     * Change the selling price of an item
     * @param item
     * @param oldPrice
     * @param newPrice
     */
    public void changeSellingPrice(Item item, double oldPrice, double newPrice) {
    	double priceChange = newPrice - oldPrice;
    	String changeType = "";
    	if(priceChange != 0) {
    		if(priceChange > 0) {
    			changeType = "INCREMENT";   			
    		}else {
    			changeType = "DECREMENT"; 
    		}
    		//register price change here
    	}
    	
    }
    /**
     * validate a supplier against an item
     * returns true if match
     * @param item
     * @param supplier
     * @return
     */
    public boolean validateSupplier(Item item, Supplier supplier) {
    	boolean _valid = false;
    	try {
    		Supplier _supplier = item.getSupplier();
    		if(supplier.equals(_supplier)) {
    			_valid = true;
    		}
    	}catch(Exception e) {
    		_valid = false;
    	}
    	return _valid;		
    }
    private void validateBarcode(String barcode) {
    	/**
    	 * Validate barcode;
    	 * Check for duplicate entries etc
    	 */
    	if(itemRepository.existsByPrimaryBarcode(barcode) && barcode !=null) {
    		throw new DuplicateEntryException("Operation failed.\nDuplicate entry in barcode");
    	}
    	if(barcode.contains("/")) {
    		throw new InvalidEntryException("Operation failed.\n slash is not allowed in barcode");
    	}
    }
    private boolean validateItemCode(String itemCode) {
    	boolean valid = false;
    	/**
    	 * Validate item code;
    	 * Check for duplicate entries etc
    	 */
    	if(itemRepository.existsByItemCode(itemCode)) {
    		throw new DuplicateEntryException("Operation failed.\nDuplicate entry in item code");
    	}
    	if(itemCode.equals("")) {
    		throw new MissingInformationException("Operation failed.\nItem code is required");
    	}
    	if(itemCode.contains("/")) {
    		throw new InvalidEntryException("Operation failed.\n slash is not allowed in item code");
    	}
    	valid = true;
    	return valid;
    }
    private boolean validateLongDescription(String description) {
    	boolean valid = false;
    	/**
    	 * Validate long description;
    	 * Check for duplicate entries etc
    	 */
    	if(itemRepository.existsByLongDescription(description)) {
    		throw new DuplicateEntryException("Operation failed.\nDuplicate entry in Long description");
    	}
    	if(description.equals("")) {
    		throw new MissingInformationException("Operation failed.\nLong description is required");
    	}
    	valid = true;
    	return valid;
    }
    private boolean validateInputs(Item item) throws Exception {
    	boolean valid = true;
    	
        String itemCode = item.getItemCode();
        String primaryBarcode = item.getPrimaryBarcode();
    	String longDescription = item.getLongDescription();
    	String shortDescription = item.getShortDescription();
    	int packSize = item.getPackSize();
    	double unitCostPrice = item.getUnitCostPrice();
    	double unitRetailPrice = item.getUnitRetailPrice();
    	double discount = item.getDiscount();
    	double vat = item.getVat();
    	double profitMargin = item.getProfitMargin();
    	String standardUom = item.getStandardUom();
    	String status = item.getStatus();
    	String ingredients = item.getIngredients();
    	double quantity = item.getQuantity();
        double maximumInventory = item.getMaximumInventory();
        double minimumInventory = item.getMinimumInventory();
        double defaultReOrderLevel = item.getDefaultReOrderLevel();
        double reOrderQuantity = item.getReOrderQuantity();
        String supplierName;
        try {
        	supplierName = item.getSupplier().getSupplierName();
        }catch(Exception e) {
        	supplierName = "";
        }
        try {
        	if(!primaryBarcode.matches("^\\S*$")) {
        		throw new InvalidEntryException("Operation failed. Barcode should not contain space");
	        }
	        if(!itemCode.matches("^\\S*$")) {
	        	throw new InvalidEntryException("Operation failed. Item code should not contain space");
	        }
	        if(packSize%1 != 0 || packSize <=0) {
	        	throw new InvalidEntryException("Operation failed. Pack size should be a whole number and more than 0");
	        }
	        if(unitCostPrice < 0) {
	        	throw new InvalidEntryException("Operation failed. Unit cost price must be more than 0");
	        }
	        if(unitRetailPrice < 0) {
	        	throw new InvalidEntryException("Operation failed. Unit retail price must be more than 0");
	        }
	        if(discount < 0 || discount > 100) {
	        	throw new InvalidEntryException("Operation failed. Discount must be between 0 and 100 inclusive");
	        }
	        if(vat < 0 || vat > 100) {
	        	throw new InvalidEntryException("Operation failed. VAT must be between 0 and 100 inclusive");
	        }
	        if(profitMargin < 0) {
	        	throw new InvalidEntryException("Operation failed. Profit margin must be more than 0 inclusive");
	        }
	        if(maximumInventory < minimumInventory) {
	        	throw new InvalidEntryException("Operation failed. Maximum inventory value must be more than minimum inventory value");
	        }
	        if(defaultReOrderLevel > maximumInventory) {
	        	throw new InvalidEntryException("Operation failed. Default re order level value must be less than minimum inventory value");
	        }
	        if(supplierName.equals("") || supplierName == null) {
	        	throw new MissingInformationException("Could not save. An item must hava a valid supplier");
	        }
        }catch(Exception e) {
        	valid = false;
        }
    	return valid;		
    }
    private boolean checkItemCode(String itemCode, Item item) {
    	/**
    	 * Check whether item code has been changed and whether the change is valid.
    	 * Return true if there is no change or if there is a change on an item 
    	 * which has not been used to make any transaction in the system
    	 */
    	boolean check = false;
    	if(item.getItemCode().equals(itemCode)) {
    		check = true;
    	}else {
    		throw new InvalidOperationException("Change in item code is not allowed");
    	}
    	return check;
    }
    private boolean checkUsageBeforeDelete(Item item) {
    	/**
    	 * Checks whether an item has been used any where
    	 * returns false if it has not been used
    	 * throw invalid operation exception if it has been used
    	 * to prevent deletion
    	 */
    	boolean used = false;
    	//assume it has been used, to be implemented later
    	used = true;
    	//throw exception
    	if(used == true) {
    		throw new InvalidOperationException("Could not delete. Item already in use within the system.");
    	}
    	return used;
    }
    private boolean checkPrice(double price, Item item) {
    	/**
    	 * Check whether price has been changed and whether the change is valid.
    	 * Return true if there is no change or if there is a change on an item 
    	 */
    	boolean change = true;
    	if(item.getUnitRetailPrice() == price) {
    		change = false;
    	}
    	return change;
    }
    private void registerSellingPriceChange(Item item, double oldPrice, String reason) {
    	SellingPriceChange priceChange = new SellingPriceChange();
    	double newPrice =item.getUnitRetailPrice();
    	double change = newPrice - oldPrice;
    	String changeType;
    	if(change > 0) {
    		changeType = "INCREMENT";
    	}else if(change < 0) {
    		changeType = "DECREMENT";
    	}else {
    		changeType = "NO CHANGE";
    	}
    	priceChange.setItem(item);
    	priceChange.setOldPrice(oldPrice);
    	priceChange.setNewPrice(newPrice);
    	priceChange.setPriceChange(change);
    	priceChange.setChangeType(changeType);
    	priceChange.setReason(reason);
    	priceChange.setDateTime(new Date());
    	if(change != 0) {
    		sellingPriceChangeRepository.saveAndFlush(priceChange);
    	}
    }
    private void registerCostPriceChange(Item item, double oldPrice, String reason) {
    	CostPriceChange priceChange = new CostPriceChange();
    	double newPrice =item.getUnitCostPrice();
    	double change = newPrice - oldPrice;
    	String changeType;
    	if(change > 0) {
    		changeType = "INCREMENT";
    	}else if(change < 0) {
    		changeType = "DECREMENT";
    	}else {
    		changeType = "NO CHANGE";
    	}
    	priceChange.setItem(item);
    	priceChange.setOldPrice(oldPrice);
    	priceChange.setNewPrice(newPrice);
    	priceChange.setPriceChange(change);
    	priceChange.setChangeType(changeType);
    	priceChange.setReason(reason);
    	priceChange.setDateTime(new Date());
    	if(change != 0) {
    		costPriceChangeRepository.saveAndFlush(priceChange);
    	}
    }
    
}
