/**
 * 
 */
package com.example.orbix_web.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Item;

/**
 * @author GODFREY
 *
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	/**
	 * @param primaryBarcode
	 * @return
	 */
	Optional<Item> findByPrimaryBarcode(String primaryBarcode);

	/**
	 * @param itemCode
	 * @return
	 */
	Optional<Item> findByItemCode(String itemCode);

	/**
	 * @param longDescription
	 * @return
	 */
	Optional<Item> findByLongDescription(String longDescription);

	
}

