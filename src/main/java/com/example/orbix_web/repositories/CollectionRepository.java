/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Bill;
import com.example.orbix_web.models.Collection;

/**
 * @author GODFREY
 *
 */
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
