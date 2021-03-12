/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Customer;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * @return
	 */
	@Query("select c.customerName from Customer c")
	Iterable<Customer> getCustomerNames();

	/**
	 * @param customerName
	 * @return
	 */
	Optional<Customer> findByCustomerName(String customerName);

	/**
	 * @param customerNo
	 * @return
	 */
	Optional<Customer> findByCustomerNo(String customerNo);
	
}
