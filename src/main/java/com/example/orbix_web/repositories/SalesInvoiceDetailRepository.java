/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Customer;
import com.example.orbix_web.models.SalesInvoice;
import com.example.orbix_web.models.SalesInvoiceDetail;

/**
 * @author GODFREY
 *
 */
public interface SalesInvoiceDetailRepository extends JpaRepository<SalesInvoiceDetail, Long> {

	/**
	 * @param invoice
	 * @return
	 */
	List<SalesInvoiceDetail> findBySalesInvoice(SalesInvoice invoice);

	/**
	 * @param invoice
	 * @param itemCode
	 * @return
	 */
	SalesInvoiceDetail findBySalesInvoiceAndItemCode(SalesInvoice invoice, String itemCode);

}
