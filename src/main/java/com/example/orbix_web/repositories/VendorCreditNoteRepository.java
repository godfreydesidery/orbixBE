/**
 * 
 */
package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orbix_web.models.CustomerCreditNote;
import com.example.orbix_web.models.VendorCreditNote;

/**
 * @author GODFREY
 *
 */
public interface VendorCreditNoteRepository extends JpaRepository<VendorCreditNote, Long>{

}
