/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;
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
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.DepartmentRepository;
import com.example.orbix_web.repositories.ItemRepository;
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
    @RequestMapping(method = RequestMethod.GET, value="/items/long_descriptions")
    public Iterable<Item> getAllItemsByLongDescription() {
        return itemRepository.getLongDescription();
    }

    /**
     * create a new item
     * @param item
     * @return item created
     */
    @RequestMapping(method = RequestMethod.POST, value="/items")
    @ResponseBody
    public Item createItem(@Valid @RequestBody Item item ) {
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
    	return itemRepository.save(item);
    }
    /**
     * get a single item by id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items/{id}")
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
    public Item getItemByItemCode(@PathVariable(value = "item_code") String itemCode) {
        return itemRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }
    /**
     * get item by long description
     * @param primaryBarcode
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/items/long_description={long_description}")
    public Item getItemByLongDescription(@PathVariable(value = "long_description") String longDescription) {
        return itemRepository.findByLongDescription(longDescription)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    } 
    /**
     * 
     * @param itemId
     * @param itemDetails
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/items/{id}", produces = "text/html")
    public ResponseEntity<Object> updateItem(@PathVariable(value = "id") Long itemId,
                                            @Valid @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        item = itemDetails;        
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
    	try {
    		itemRepository.save(item);
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
    	try {
    		itemRepository.delete(item);
    		return ResponseEntity.ok().build();
    	}catch(Exception ex) {
    		return new ResponseEntity<>("Could not delete item: "+ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
    	}
    }
    /**
     * Add items in stock
     * @param item
     * @param qty
     */
    public static void addStock(Object item, double qty) {
    	//implement this method
    }
    /**
     * Deduct items in stock
     * @param item
     * @param qty
     */
    public static void deductStock(Object item, double qty) {
    	//implement this method
    }
    /**
     * Change the selling price of an item
     * @param item
     * @param oldPrice
     * @param newPrice
     */
    public static void changeSellingPrice(Item item, double oldPrice, double newPrice) {
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
}
