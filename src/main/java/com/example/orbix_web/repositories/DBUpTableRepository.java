/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.DBUpTable;

/**
 * @author GODFREY
 *
 */
public interface DBUpTableRepository extends JpaRepository<DBUpTable, Long>{
	long count();
}
