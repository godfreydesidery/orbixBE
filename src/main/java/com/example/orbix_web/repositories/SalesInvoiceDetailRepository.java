/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.SalesInvoiceDetail;

/**
 * @author GODFREY
 *
 */
public interface SalesInvoiceDetailRepository extends JpaRepository<SalesInvoiceDetail, Long> {

}
