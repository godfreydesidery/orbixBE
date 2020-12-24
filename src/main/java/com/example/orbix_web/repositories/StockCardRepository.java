/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.StockCard;

/**
 * @author GODFREY
 *
 */
public interface StockCardRepository extends JpaRepository<StockCard, Long>{
	
}
