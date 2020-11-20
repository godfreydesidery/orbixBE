/**
 * 
 */
package com.example.orbix_web.controllers;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.HttpMethod;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import org.springframework.web.client.RestTemplate;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.repositories.ItemRepository;
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
    RestTemplate restTemplate;
    
    // Get All Items
    @GetMapping(value="/items",produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Item> getAllItems() {
    	
    	List<Item> list = new ArrayList<>();
        Iterable<Item> items = itemRepository.findAll();
     
        items.forEach(list::add);
        return list;
    	
        //return itemRepository.findAll();
    }

    // Create a new Item
    @PostMapping(value="/items")
    @ResponseBody
    public Item createItem(@Valid @RequestBody Item item) {
    	System.out.println("Success");
        return itemRepository.save(item);
    }

    // Get a Single Item
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable(value = "id") Long itemId) {
    	
    	System.out.println("Success");
    	
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
    }
    
 // Get a Single Item
    @GetMapping("/items/primary_barcode={primary_barcode}")
    public Item getItemByPrimaryBarcode(@PathVariable(value = "primary_barcode") String primaryBarcode) {
    	
    	
        return itemRepository.findByPrimaryBarcode(primaryBarcode)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "primary_barcode", primaryBarcode));
    }
    
 // Get a Single Item
    @GetMapping("/items/item_code={item_code}")
    public Item getItemByItemCode(@PathVariable(value = "item_code") String itemCode) {
    	
    	
        return itemRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "item_code", itemCode));
    }
    
 // Get a Single Item
    @GetMapping("/items/long_description={long_description}")
    public Item getItemByLongDescription(@PathVariable(value = "long_description") String longDescription) {
    	
    	
        return itemRepository.findByLongDescription(longDescription)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "long_description", longDescription));
    }
    
    // Update a Item
    @PutMapping("/items/{id}")
    public Item updateNote(@PathVariable(value = "id") Long itemId,
                                            @Valid @RequestBody Item itemDetails) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        item = itemDetails;

        Item updatedItem = itemRepository.save(item);
        return updatedItem;
    }

    // Delete a Item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") Long itemId) {
    	Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

    	itemRepository.delete(item);

        return ResponseEntity.ok().build();
    }
}
