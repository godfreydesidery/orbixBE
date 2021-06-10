/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "customer_credit_notes")
@EntityListeners(AuditingEntityListener.class)
@Embeddable
public class CustomerCreditNote {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String creditNoteNo;
	private String invoiceNo;
	private String receiptNo;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	private double initialBalance;
	private double currentBalance;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return the receiptNo
	 */
	public String getReceiptNo() {
		return receiptNo;
	}
	/**
	 * @param receiptNo the receiptNo to set
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	/**
	 * @return the issueDate
	 */
	public LocalDate getIssueDate() {
		return issueDate;
	}
	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	/**
	 * @return the expiryDate
	 */
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	/**
	 * @return the initialBalance
	 */
	public double getInitialBalance() {
		return initialBalance;
	}
	/**
	 * @param initialBalance the initialBalance to set
	 */
	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}
	/**
	 * @return the currentBalance
	 */
	public double getCurrentBalance() {
		return currentBalance;
	}
	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	/**
	 * @return the creditNoteNo
	 */
	public String getCreditNoteNo() {
		return creditNoteNo;
	}
	/**
	 * @param creditNoteNo the creditNoteNo to set
	 */
	public void setCreditNoteNo(String creditNoteNo) {
		this.creditNoteNo = creditNoteNo;
	}
	
}
