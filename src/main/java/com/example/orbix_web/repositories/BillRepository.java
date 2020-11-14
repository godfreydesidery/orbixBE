/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Bill;

/**
 * @author GODFREY
 *
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}
