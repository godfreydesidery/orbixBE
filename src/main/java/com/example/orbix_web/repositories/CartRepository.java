/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Cart;
import com.example.orbix_web.models.Customer;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
