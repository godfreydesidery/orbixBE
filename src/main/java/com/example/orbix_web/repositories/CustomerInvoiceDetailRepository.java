/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.CustomerInvoiceDetail;

/**
 * @author GODFREY
 *
 */
public interface CustomerInvoiceDetailRepository extends JpaRepository<CustomerInvoiceDetail, Long> {

}
