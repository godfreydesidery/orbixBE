/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Receipt;
import com.example.orbix_web.models.Transaction;

/**
 * @author GODFREY
 *
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

}