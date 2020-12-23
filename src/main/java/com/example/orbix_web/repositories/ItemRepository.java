/**
 * 
 */
package com.example.orbix_web.repositories;


import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	/**
	 * @return
	 */
	@Query("select i.longDescription from Item i")
	Iterable<Item> getLongDescription();
	
	//add items to stock
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Item i SET i.quantity = i.quantity + ?1 WHERE i =?2")
    int addToStock(double qty, Item item);
	//deduct items from stock
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Item i SET i.quantity = i.quantity - ?1 WHERE i =?2")
    int deductFromStock(double qty, Item item);

}

