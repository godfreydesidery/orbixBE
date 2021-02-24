/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Clas;
import com.example.orbix_web.models.SubClass;
import com.example.orbix_web.models.Supplier;

/**
 * @author GODFREY
 *
 */
@Repository
public interface SubClassRepository extends JpaRepository<SubClass, Long> {

	/**
	 * @param cls
	 * @return
	 */
	List<SubClass> findByClas(Clas cls);

	/**
	 * @param subClassName
	 * @return
	 */
	Optional<SubClass> findBySubClassName(String subClassName);

}
