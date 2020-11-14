/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Cart;
import com.example.orbix_web.models.SupplierBank;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SupplierBankRepository extends JpaRepository<SupplierBank, Long> {

}
