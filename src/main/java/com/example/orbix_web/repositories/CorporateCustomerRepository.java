/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.CorporateCustomer;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, Long> {

	/**
	 * @return
	 */
	@Query("select c.customerName from CorporateCustomer c")
	Iterable<CorporateCustomer> getCorporateCustomerNames();

	/**
	 * @param customerName
	 * @return
	 */
	Optional<CorporateCustomer> findByCustomerName(String customerName);

	/**
	 * @param customerNo
	 * @return
	 */
	Optional<CorporateCustomer> findByCustomerNo(String customerNo);

	
}
