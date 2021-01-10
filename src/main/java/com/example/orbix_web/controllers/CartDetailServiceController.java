/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.CartDetail;
import com.example.orbix_web.repositories.CartDetailRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartDetailServiceController {

    @Autowired
    CartDetailRepository cartDetailRepository;
    
    // Get All CartDetails
    @GetMapping("/cart_details")
    public List<CartDetail> getAllCartDetails() {
        return cartDetailRepository.findAll();
    }

    // Create a new CartDetail
    @PostMapping(value="/cart_details")
    @ResponseBody
    public CartDetail createCartDetail(@Valid @RequestBody CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    // Get a Single CartDetail
    @GetMapping("/cart_details/{id}")
    public CartDetail getCartDetailById(@PathVariable(value = "id") Long cartDetailId) {
        return cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("CartDetail", "id", cartDetailId));
    }

    // Update a CartDetail
    @PutMapping("/cart_details/{id}")
    public CartDetail updateNote(@PathVariable(value = "id") Long cartDetailId,
                                            @Valid @RequestBody CartDetail cartDetailDetails) {

        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("CartDetail", "id", cartDetailId));

        

        CartDetail updatedCartDetail = cartDetailRepository.save(cartDetail);
        return updatedCartDetail;
    }

    // Delete a CartDetail
    @DeleteMapping("/cart_details/{id}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable(value = "id") Long cartDetailId) {
    	CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("CartDetail", "id", cartDetailId));

    	cartDetailRepository.delete(cartDetail);

        return ResponseEntity.ok().build();
    }
}
