/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.example.orbix_web.database.Audit;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@Embeddable
public class Customer extends Audit<String> {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank
	@Column(unique = true)
    private String customerNo;
	@NotBlank
	@Column(unique = true)
    private String customerName;
	//@NotBlank
    private String contactName;
	@NotBlank
    private String address;
    private String telephone;
    private String vatNo;
    private double creditLimit;
    private double invoiceLimit;
    private int creditDays;
    private double outstandingBalance;
    private double amountUnallocated;
    private double discountRate;
	private LocalDate discountStartDate;
	private LocalDate discountEndDate;
    private double priceMargin;
	private LocalDate priceMarginStartDate;
	private LocalDate priceMarginEndDate;
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
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the vatNo
	 */
	public String getVatNo() {
		return vatNo;
	}
	/**
	 * @param vatNo the vatNo to set
	 */
	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}
	
	
	/**
	 * @return the creditDays
	 */
	public int getCreditDays() {
		return creditDays;
	}
	/**
	 * @param creditDays the creditDays to set
	 */
	public void setCreditDays(int creditDays) {
		this.creditDays = creditDays;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the creditLimit
	 */
	public double getCreditLimit() {
		return creditLimit;
	}
	/**
	 * @param creditLimit the creditLimit to set
	 */
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	/**
	 * @return the invoiceLimit
	 */
	public double getInvoiceLimit() {
		return invoiceLimit;
	}
	/**
	 * @param invoiceLimit the invoiceLimit to set
	 */
	public void setInvoiceLimit(double invoiceLimit) {
		this.invoiceLimit = invoiceLimit;
	}
	/**
	 * @return the discountRate
	 */
	public double getDiscountRate() {
		return discountRate;
	}
	/**
	 * @param discountRate the discountRate to set
	 */
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	/**
	 * @return the discountStartDate
	 */
	public LocalDate getDiscountStartDate() {
		return discountStartDate;
	}
	/**
	 * @param discountStartDate the discountStartDate to set
	 */
	public void setDiscountStartDate(LocalDate discountStartDate) {
		this.discountStartDate = discountStartDate;
	}
	/**
	 * @return the discountEndDate
	 */
	public LocalDate getDiscountEndDate() {
		return discountEndDate;
	}
	/**
	 * @param discountEndDate the discountEndDate to set
	 */
	public void setDiscountEndDate(LocalDate discountEndDate) {
		this.discountEndDate = discountEndDate;
	}
	/**
	 * @return the priceMargin
	 */
	public double getPriceMargin() {
		return priceMargin;
	}
	/**
	 * @param priceMargin the priceMargin to set
	 */
	public void setPriceMargin(double priceMargin) {
		this.priceMargin = priceMargin;
	}
	/**
	 * @return the priceMarginStartDate
	 */
	public LocalDate getPriceMarginStartDate() {
		return priceMarginStartDate;
	}
	/**
	 * @param priceMarginStartDate the priceMarginStartDate to set
	 */
	public void setPriceMarginStartDate(LocalDate priceMarginStartDate) {
		this.priceMarginStartDate = priceMarginStartDate;
	}
	/**
	 * @return the priceMarginEndDate
	 */
	public LocalDate getPriceMarginEndDate() {
		return priceMarginEndDate;
	}
	/**
	 * @param priceMarginEndDate the priceMarginEndDate to set
	 */
	public void setPriceMarginEndDate(LocalDate priceMarginEndDate) {
		this.priceMarginEndDate = priceMarginEndDate;
	}
	/**
	 * @return the amountUnallocated
	 */
	public double getAmountUnallocated() {
		return amountUnallocated;
	}
	/**
	 * @param amountUnallocated the amountUnallocated to set
	 */
	public void setAmountUnallocated(double amountUnallocated) {
		this.amountUnallocated = amountUnallocated;
	}
	/**
	 * @return the outstandingBalance
	 */
	public double getOutstandingBalance() {
		return outstandingBalance;
	}
	/**
	 * @param outstandingBalance the outstandingBalance to set
	 */
	public void setOutstandingBalance(double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	
	
}
