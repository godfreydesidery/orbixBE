/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.orbix_web.models.Class;

/**
 * @author GODFREY
 *
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

}
