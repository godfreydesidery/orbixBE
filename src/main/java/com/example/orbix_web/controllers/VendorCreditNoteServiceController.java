/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.models.CustomerCreditNote;
import com.example.orbix_web.models.VendorCreditNote;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendorCreditNoteServiceController {
	
	public VendorCreditNote newCreditNote(String rtvNo, LocalDate date, double initialBalance, double currentBalance){
		VendorCreditNote crNote = new VendorCreditNote();
		crNote.setRtvNo(rtvNo);
		crNote.setIssueDate(date);
		crNote.setInitialBalance(initialBalance);
		crNote.setCurrentBalance(currentBalance);
		return crNote;
	}

}
