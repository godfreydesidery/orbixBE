/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.CompanyProfile;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CompanyProfileRepository extends JpaRepository <CompanyProfile, Long> {

	/**
	 * @param key
	 * @return
	 */
	Optional<CompanyProfile> findByCompanyKey(String key);

	

	
}
