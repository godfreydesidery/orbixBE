/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Rtv;

/**
 * @author GODFREY
 *
 */
@Repository
public interface RtvRepository extends JpaRepository<Rtv, Long>{

	/**
	 * @param rtvNo
	 * @return
	 */
	Optional<Rtv> findByRtvNo(String rtvNo);

}
