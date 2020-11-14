/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.SupplierBank;
import com.example.orbix_web.models.SupplierContact;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SupplierContactRepository extends JpaRepository<SupplierContact, Long> {

}
