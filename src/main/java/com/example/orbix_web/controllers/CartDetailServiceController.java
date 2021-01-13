/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.example.orbix_web.models.CartDetail;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.repositories.CartDetailRepository;
import com.example.orbix_web.repositories.CartRepository;
import com.example.orbix_web.repositories.ItemRepository;

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
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ItemRepository itemRepository;
    
    // Get All CartDetails
    @Transactional
    @GetMapping("/cart_details")
    public List<CartDetail> getAllCartDetails() {
        return cartDetailRepository.findAll();
    }
 // Get Details of a specific cart
    @Transactional
    @GetMapping("/cart_details/cart_id={cart_id}")
    public List<CartDetail> getAllCartDetails(@PathVariable(value = "cart_id") Long cartId) {
    	Cart cart = cartRepository.findById(cartId).get();
        return cartDetailRepository.findByCart(cart);
    }

    // Create a new CartDetail
    @Transactional
    @PostMapping(value="/cart_details")
    @ResponseBody
    public CartDetail createCartDetail(@Valid @RequestBody CartDetail cartDetail) {
    	String itemCode = cartDetail.getItemCode();
    	Item item = itemRepository.findByItemCode(itemCode).get();
    	itemRepository.save(item);
    	cartDetail.setItem(item);
    	
    	Cart cart = cartDetail.getCart();
    	CartDetail oldDetail = cartDetailRepository.findByCartAndItemCode(cart, itemCode);
    	if(oldDetail == null) {
    		
    	}else {
    		double qtyEntered = cartDetail.getQuantity();
    		double qtyAvailable =oldDetail.getQuantity();
    		cartDetail = oldDetail;
    		cartDetail.setQuantity(qtyAvailable + qtyEntered);
    	}
        return cartDetailRepository.saveAndFlush(cartDetail);
    }

    // Get a Single CartDetail
    @Transactional
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
