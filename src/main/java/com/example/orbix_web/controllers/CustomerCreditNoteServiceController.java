/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.models.CustomerCreditNote;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerCreditNoteServiceController {
	
	public CustomerCreditNote newCreditNote(String invoiceNo, String receiptNo, LocalDate date, double initialBalance, double currentBalance){
		CustomerCreditNote crNote = new CustomerCreditNote();
		crNote.setInvoiceNo(invoiceNo);
		crNote.setReceiptNo(receiptNo);
		crNote.setIssueDate(date);
		crNote.setInitialBalance(initialBalance);
		crNote.setCurrentBalance(currentBalance);
		return crNote;
	}

}
