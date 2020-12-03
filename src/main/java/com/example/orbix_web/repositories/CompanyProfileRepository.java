/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.CompanyProfile;
import com.example.orbix_web.models.CorporateCustomer;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CompanyProfileRepository extends JpaRepository <CompanyProfile, Long> {

	
}
