/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Shelf;
import com.example.orbix_web.models.User;

/**
 * @author GODFREY
 *
 */
@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

}
