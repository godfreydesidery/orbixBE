/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.GrnDetail;
import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;

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

	/**
	 * @param itemCode
	 * @param orderNo
	 * @return
	 */
	//@Query("SELECT d FROM GrnDetail d where d.itemCode = itemCode and d.orderNo = orderNo")
	//Optional<GrnDetail> findByItemCodeAndOrderNo_(@Param("itemCode") String itemCode, @Param("orderNo") String orderNo);
	Optional<GrnDetail> findByItemCodeAndOrderNo(String itemCode, String orderNo);
	
	

}
