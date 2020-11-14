/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.orbix_web.models.SubClass;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SubClassRepository extends JpaRepository<SubClass, Long> {

}
