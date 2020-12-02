/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.Supplier;

/**
 * @author GODFREY
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	/**
	 * @return
	 */
	@Query("select d.departmentName from Department d")
	Iterable<Department> getDepartmentNames();

	/**
	 * @param departmentName
	 * @return
	 */
	Optional<Department> findByDepartmentName(String departmentName);

	/**
	 * @param departmentCode
	 * @return
	 */
	Optional<Department> findByDepartmentCode(String departmentCode);
}