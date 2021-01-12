/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Cart;
import com.example.orbix_web.models.CartDetail;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

	/**
	 * @param cart
	 * @return
	 */
	List<CartDetail> findByCart(Cart cart);

	

}
