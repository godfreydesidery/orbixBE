/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Supplier;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	/**
	 * @param supplierName
	 * @return
	 */
	Optional<Supplier> findBySupplierName(String supplierName);

	/**
	 * @return
	 */
	@Query("select s.supplierName from Supplier s")
	Iterable<Supplier> getSupplierNames();

	

}
