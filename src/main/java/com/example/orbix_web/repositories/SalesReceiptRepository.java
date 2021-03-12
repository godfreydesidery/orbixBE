/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Receipt;
import com.example.orbix_web.models.SalesReceipt;
import com.example.orbix_web.models.Transaction;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SalesReceiptRepository extends JpaRepository<SalesReceipt, Long> {

	/**
	 * @param receiptNo
	 * @return
	 */
	SalesReceipt findByReceiptNo(String receiptNo);

}