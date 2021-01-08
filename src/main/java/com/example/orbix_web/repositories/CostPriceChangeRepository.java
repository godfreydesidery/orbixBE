/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.CostPriceChange;
import com.example.orbix_web.models.Lpo;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CostPriceChangeRepository extends JpaRepository<CostPriceChange, Long>{

}
