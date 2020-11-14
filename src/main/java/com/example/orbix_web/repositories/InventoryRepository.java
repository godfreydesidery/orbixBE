/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Inventory;
import com.example.orbix_web.models.Till;

/**
 * @author GODFREY
 *
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
