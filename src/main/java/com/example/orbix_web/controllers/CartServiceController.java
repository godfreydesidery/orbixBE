/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;
import java.util.Optional;

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

import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Cart;
import com.example.orbix_web.models.CartDetail;
import com.example.orbix_web.models.Till;
import com.example.orbix_web.repositories.CartDetailRepository;
import com.example.orbix_web.repositories.CartRepository;
import com.example.orbix_web.repositories.TillRepository;

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
    @Autowired
    CartDetailRepository cartDetailRepository;
    @Autowired
    TillRepository tillRepository;
    
    // Get All Carts
    @GetMapping("/carts")
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // Create a new Cart
    @PostMapping(value="/carts/create")
    @ResponseBody
    public Cart createCart(@Valid @RequestBody Till till) {
    	Cart cart = new Cart();
    	cart.setTill(till);
        return cartRepository.save(cart);
    }

    // Get a Single Cart
    @GetMapping("/carts/{id}")
    public Cart getCartById(@PathVariable(value = "id") Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
    }
    //Check whether a cart is empty
    @GetMapping("/carts/is_empty/till_no={till_no}")
    public boolean checkIfCartEmpty(@PathVariable(value = "till_no") String tillNo) {
    	boolean empty = true;
    	Till till = tillRepository.findByTillNo(tillNo).get();
    	Cart cart = cartRepository.findByTill(till).get();
    	List<CartDetail> cartDetails = cartDetailRepository.findByCart(cart);
    	
    	for(@SuppressWarnings("unused") CartDetail detail : cartDetails) {
    		empty = false;
    		break;
    	}
    	return empty;
    }
 // Destroy a Cart
    @DeleteMapping("/carts/destroy/till_no={till_no}")
    public void destroyCart(@PathVariable(value = "till_no") String tillNo) {
    	Till till = tillRepository.findByTillNo(tillNo).get();
    	Cart cart = cartRepository.findByTill(till)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

    	cartRepository.delete(cart);

        
    }
 

	// Get a Single Cart
    @GetMapping("/carts/till_no={till_no}")
    public Cart getCartByTillNo(@PathVariable(value = "till_no") String tillNo) {
    	Till till = tillRepository.findByTillNo(tillNo).get();
        return cartRepository.findByTill(till)
                .orElseThrow(() -> new NotFoundException("No cart found"));
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
