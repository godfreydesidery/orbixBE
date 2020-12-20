/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.GrnDetail;

/**
 * @author GODFREY
 *
 */
public interface GrnDetailRepository extends JpaRepository<GrnDetail, Long>{

	/**
	 * @param grn
	 * @param itemCode
	 * @return
	 */
	boolean existsByOrderNoAndItemCode(String orderNo, String itemCode);

}
