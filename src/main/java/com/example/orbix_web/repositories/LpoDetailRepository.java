/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.LpoDetail;

/**
 * @author GODFREY
 *
 */
@Repository
public interface LpoDetailRepository extends JpaRepository<LpoDetail, Long>{

}
