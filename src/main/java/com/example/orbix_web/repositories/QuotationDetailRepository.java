/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Allocation;
import com.example.orbix_web.models.QuotationDetail;

/**
 * @author GODFREY
 *
 */
public interface QuotationDetailRepository extends JpaRepository<QuotationDetail, Long>{

}
