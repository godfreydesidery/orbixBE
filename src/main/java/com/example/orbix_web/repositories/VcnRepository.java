/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.Lpo;
import com.example.orbix_web.models.Vcn;

/**
 * @author GODFREY
 *
 */
public interface VcnRepository extends JpaRepository<Vcn, Long> {

}
