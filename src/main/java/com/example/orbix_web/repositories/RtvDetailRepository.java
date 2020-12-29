/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Rtv;
import com.example.orbix_web.models.RtvDetail;

/**
 * @author GODFREY
 *
 */
public interface RtvDetailRepository extends JpaRepository<RtvDetail, Long>{

	/**
	 * @return
	 */
	List<RtvDetail> findByRtv(Rtv rtv);

}
