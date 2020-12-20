/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;

/**
 * @author GODFREY
 *
 */
@Repository
public interface LpoDetailRepository extends JpaRepository<LpoDetail, Long>{	
	@Query("SELECT d FROM LpoDetail d where d.lpo = lpo and d.itemCode = itemCode")
	Optional<LpoDetail> getLpoAndItemCode(@Param("lpo") Object lpo, @Param("itemCode") String itemCode);
	boolean existsByLpoAndItemCode(Object lpo, String itemCode);
	/**
	 * @param orderNo
	 * @return
	 */
	//@Query("SELECT d FROM LpoDetail d where d.lpo = lpo")
	//List<LpoDetail> findByLpo(@Param("lpo") Object lpo);
	
	
	List<LpoDetail> findByLpo(Object lpo);
}
