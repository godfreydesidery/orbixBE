/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Sale;
import com.example.orbix_web.models.User;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
