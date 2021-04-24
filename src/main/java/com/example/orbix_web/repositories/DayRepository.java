/**
 * 
 */
package com.example.orbix_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Day;

/**
 * @author GODFREY
 *
 */
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

	/**
	 * @return
	 */
	Day findTopByOrderByIdDesc();

}
