/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Day;
import com.example.orbix_web.models.Lpo;

/**
 * @author GODFREY
 *
 */
@Repository
public interface LpoRepository extends JpaRepository<Lpo, Long> {

	/**
	 * @param lpoNo
	 * @return
	 */
	Optional<Lpo> findByLpoNo(String lpoNo);

}
