/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Day;
import com.example.orbix_web.models.LPO;

/**
 * @author GODFREY
 *
 */
@Repository
public interface LPORepository extends JpaRepository<LPO, Long> {

}
