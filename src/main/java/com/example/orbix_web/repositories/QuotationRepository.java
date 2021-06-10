/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Allocation;
import com.example.orbix_web.models.Quotation;

/**
 * @author GODFREY
 *
 */
public interface QuotationRepository extends JpaRepository<Quotation, Long>{

	/**
	 * @param quotationNo
	 * @return
	 */
	Quotation findByQuotationNo(String quotationNo);

}
