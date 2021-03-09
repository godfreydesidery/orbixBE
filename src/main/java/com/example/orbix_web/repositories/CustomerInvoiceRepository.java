/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.CustomerInvoice;

/**
 * @author GODFREY
 *
 */
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {

	/**
	 * @param invoiceNo
	 * @return
	 */
	CustomerInvoice findByInvoiceNo(String invoiceNo);

	
	

}
