/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.Transaction;

/**
 * @author GODFREY
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
