/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author GODFREY
 *
 */
@Entity
@Component
@Table(name = "sales_receipts")
@EntityListeners(AuditingEntityListener.class)
public class SalesReceipt {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotBlank
	@Column(unique = true)
	private String receiptNo;
	private String creditNoteNo;
	@NotNull
	private double receiptAmount;
	private double credit;
	private double debit;
	private String transactionType;
	@NotBlank
	private String paymentMode;
	@Column(unique = true)
	private String chequeNo;
	@Temporal(TemporalType.DATE)
    private Date chequeDate;
	private String chequeBank;
	@Temporal(TemporalType.DATE)
    private Date receiptDate;
	@Temporal(TemporalType.DATE)
    private Date date;
		
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;


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
	 * @return the credit
	 */
	public double getCredit() {
		return credit;
	}


	/**
	 * @param credit the credit to set
	 */
	public void setCredit(double credit) {
		this.credit = credit;
	}


	/**
	 * @return the debit
	 */
	public double getDebit() {
		return debit;
	}


	/**
	 * @param debit the debit to set
	 */
	public void setDebit(double debit) {
		this.debit = debit;
	}


	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}


	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}


	/**
	 * @param chequeNo the chequeNo to set
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}


	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}


	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}


	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	/**
	 * @return the chequeDate
	 */
	public Date getChequeDate() {
		return chequeDate;
	}


	/**
	 * @param chequeDate the chequeDate to set
	 */
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
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
	 * @return the receiptDate
	 */
	public Date getReceiptDate() {
		return receiptDate;
	}


	/**
	 * @param receiptDate the receiptDate to set
	 */
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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


	/**
	 * @return the receiptAmount
	 */
	public double getReceiptAmount() {
		return receiptAmount;
	}


	/**
	 * @param receiptAmount the receiptAmount to set
	 */
	public void setReceiptAmount(double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}


	/**
	 * @return the chequeBank
	 */
	public String getChequeBank() {
		return chequeBank;
	}


	/**
	 * @param chequeBank the chequeBank to set
	 */
	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}	
}
