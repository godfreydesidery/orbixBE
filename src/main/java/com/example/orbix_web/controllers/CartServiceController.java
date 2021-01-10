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
import com.example.orbix_web.models.Cart;
import com.example.orbix_web.repositories.CartRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartServiceController {

    @Autowired
    CartRepository cartRepository;
    
    // Get All Carts
    @GetMapping("/carts")
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // Create a new Cart
    @PostMapping(value="/carts")
    @ResponseBody
    public Cart createCart(@Valid @RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    // Get a Single Cart
    @GetMapping("/carts/{id}")
    public Cart getCartById(@PathVariable(value = "id") Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
    }

    // Update a Cart
    @PutMapping("/carts/{id}")
    public Cart updateNote(@PathVariable(value = "id") Long cartId,
                                            @Valid @RequestBody Cart cartDetails) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

        

        Cart updatedCart = cartRepository.save(cart);
        return updatedCart;
    }

    // Delete a Cart
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable(value = "id") Long cartId) {
    	Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

    	cartRepository.delete(cart);

        return ResponseEntity.ok().build();
    }
}
