/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.Lpo;

/**
 * @author GODFREY
 *
 */
@Repository
public interface GrnRepository extends JpaRepository<Grn, Long>{

	/**
	 * @param grnNo
	 * @return
	 */
	Optional<Grn> findByGrnNo(String grnNo);

	/**
	 * @param orderNo
	 * @return
	 */
	boolean existsByOrderNo(String orderNo);

	/**
	 * @param orderNo
	 * @return
	 */
	Optional<Grn> findByOrderNo(String orderNo);

}
