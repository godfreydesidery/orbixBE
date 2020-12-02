/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.orbix_web.models.Class;
import com.example.orbix_web.models.Department;

/**
 * @author GODFREY
 *
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

	/**
	 * @return
	 */
	@Query("select c.className from Class c")
	Iterable<Class> getClassNames();

	/**
	 * @param className
	 * @return
	 */
	Optional<Class> findByClassName(String className);

	/**
	 * @param classCode
	 * @return
	 */
	Optional<Class> findByClassCode(String classCode);

	
	
}
