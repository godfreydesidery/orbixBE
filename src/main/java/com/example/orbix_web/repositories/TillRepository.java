/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Role;
import com.example.orbix_web.models.Till;

/**
 * @author GODFREY
 *
 */
@Repository
public interface TillRepository extends JpaRepository<Till, Long> {

	/**
	 * @param tillNo
	 * @return
	 */
	Optional<Till> findByTillNo(String tillNo);

}
