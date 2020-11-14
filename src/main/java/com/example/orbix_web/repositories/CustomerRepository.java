/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Customer;
/*public interface CustomerRepository extends MongoRepository<Customer, String> {
	 public Customer findByFirstName(String firstName);
	 public List<Customer> findByLastName(String lastName);
}*/
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
