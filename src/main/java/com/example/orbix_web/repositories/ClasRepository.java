/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Clas;
import com.example.orbix_web.models.Department;

/**
 * @author GODFREY
 *
 */
@Repository
public interface ClasRepository extends JpaRepository<Clas, Long> {

	/**
	 * @return
	 */
	@Query("select c.clasName from Clas c")
	Iterable<Clas> getClasNames();

	/**
	 * @param className
	 * @return
	 */
	Optional<Clas> findByClasName(String clasName);

	/**
	 * @param classCode
	 * @return
	 */
	Optional<Clas> findByClasCode(String clasCode);

	/**
	 * @param dept
	 * @return
	 */
	List<Clas> findByDepartment(Department dept);

}
