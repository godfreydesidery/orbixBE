/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Cart;
import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.Till;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	/**
	 * @param till
	 * @return
	 */
	Optional<Cart> findByTill(Till till);

}
