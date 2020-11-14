/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.TillPettyCash;

/**
 * @author GODFREY
 *
 */
@Repository
public interface TillPettyCashRepository extends JpaRepository<TillPettyCash, Long> {

}