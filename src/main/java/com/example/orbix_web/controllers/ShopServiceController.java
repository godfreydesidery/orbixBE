/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Shop;
import com.example.orbix_web.repositories.ShopRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class ShopServiceController {

    @Autowired
    ShopRepository shopRepository;
    
    // Get All Shops
    @GetMapping("/shops")
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    // Create a new Shop
    @PostMapping(value="/shops")
    @ResponseBody
    public Shop createShop(@Valid @RequestBody Shop shop) {
        return shopRepository.save(shop);
    }

    // Get a Single Shop
    @GetMapping("/shops/{id}")
    public Shop getShopById(@PathVariable(value = "id") Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop", "id", shopId));
    }

    // Update a Shop
    @PutMapping("/shops/{id}")
    public Shop updateNote(@PathVariable(value = "id") Long shopId,
                                            @Valid @RequestBody Shop shopDetails) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop", "id", shopId));

        

        Shop updatedShop = shopRepository.save(shop);
        return updatedShop;
    }

    // Delete a Shop
    @DeleteMapping("/shops/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable(value = "id") Long shopId) {
    	Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop", "id", shopId));

    	shopRepository.delete(shop);

        return ResponseEntity.ok().build();
    }
}
