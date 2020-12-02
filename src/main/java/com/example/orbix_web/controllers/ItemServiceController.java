/**
 * 
 */
package com.example.orbix_web.controllers;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.Valid;
import javax.ws.rs.HttpMethod;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.DepartmentRepository;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.SupplierRepository;
import com.fasterxml.jackson.core.JsonParser;


/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemServiceController {
	
	
	@RequestMapping(value="/test",method=RequestMethod.POST)
	public void test(@Valid @RequestBody Item item) {
		
		
		
		itemRepository.save(item);
		System.out.println(item.getItemCode());
		System.out.println("Testing...");
		///return "Success";
	}

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
    @GetMapping(value="/items")
    public List<Item> getAllItems() {
    	
        return itemRepository.findAll();
    }
    
    /**
     * 
     * @return array of items' long descriptions
     */
    @GetMapping(value="/items/long_descriptions")
    public Iterable<Item> getAllItemsByLongDescription() {
    	
        return itemRepository.getLongDescription();
    }

    /**
     * create a new item
     * @param item
     * @return item created
     */
    @PostMapping(value="/items")
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
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable(value = "id") Long itemId) {
    	
    	
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
    }
    
    /**
     * get item by barcode
     * @param primaryBarcode
     * @return
     */
    @GetMapping("/items/primary_barcode={primary_barcode}")
    public Item getItemByPrimaryBarcode(@PathVariable(value = "primary_barcode") String primaryBarcode) {
    	
    	
        return itemRepository.findByPrimaryBarcode(primaryBarcode)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "primary_barcode", primaryBarcode));
    }
    
    /**
     * get item by itemcode
     * @param primaryBarcode
     * @return
     */
    @GetMapping("/items/item_code={item_code}")
    public Item getItemByItemCode(@PathVariable(value = "item_code") String itemCode) {
    	
    	
        return itemRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "item_code", itemCode));
    }
    
    /**
     * get item by long description
     * @param primaryBarcode
     * @return
     */
    @GetMapping("/items/long_description={long_description}")
    public Item getItemByLongDescription(@PathVariable(value = "long_description") String longDescription) {
    	
    	
        return itemRepository.findByLongDescription(longDescription)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "long_description", longDescription));
    }
    
    /**
     * 
     * @param itemId
     * @param itemDetails
     * @return
     */
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable(value = "id") Long itemId,
                                            @Valid @RequestBody Item itemDetails) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

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
	    	
        return itemRepository.save(item);
    }

    /**
     * 
     * @param itemId
     * @return
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") Long itemId) {
    	Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

    	itemRepository.delete(item);

        return ResponseEntity.ok().build();
    }
}
