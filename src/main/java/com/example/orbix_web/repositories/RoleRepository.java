/**
 * 
 */
package com.example.orbix_web.repositories;
import java.util.Optional;

/**
 * @author GODFREY
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.orbix_web.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * @param roleName
	 * @return
	 */
	Optional<Role> findByRoleName(String roleName);

	/**
	 * @return
	 */
	@Query("select r.roleName from Role r")
	Iterable<Role> getRoleName();

}
