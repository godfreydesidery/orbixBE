/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.SellingPriceChange;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SellingPriceChangeRepository extends JpaRepository<SellingPriceChange, Long>{

}
