/**
 * 
 */
package com.example.orbix_web.repositories;
/**
 * @author GODFREY
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.orbix_web.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
