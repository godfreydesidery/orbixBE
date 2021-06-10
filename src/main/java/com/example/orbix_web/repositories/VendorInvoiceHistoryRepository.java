/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.VendorInvoiceHistory;

/**
 * @author GODFREY
 *
 */
public interface VendorInvoiceHistoryRepository extends JpaRepository<VendorInvoiceHistory, Long> {

}
