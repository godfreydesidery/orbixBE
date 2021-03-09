/**
 * 
 */
package com.example.orbix_web.models;

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
    private double amountDue;
    private double discountRate;
    @Temporal(TemporalType.DATE)
	private Date discountStartDate;
    @Temporal(TemporalType.DATE)
	private Date discountEndDate;
    private double priceMargin;
    @Temporal(TemporalType.DATE)
	private Date priceMarginStartDate;
    @Temporal(TemporalType.DATE)
	private Date priceMarginEndDate;
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
	 * @param amountDue the amountDue to set
	 */
	public void setAmountDue(Long amountDue) {
		this.amountDue = amountDue;
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
	 * @return the amountDue
	 */
	public double getAmountDue() {
		return amountDue;
	}
	/**
	 * @param amountDue the amountDue to set
	 */
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
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
	public Date getDiscountStartDate() {
		return discountStartDate;
	}
	/**
	 * @param discountStartDate the discountStartDate to set
	 */
	public void setDiscountStartDate(Date discountStartDate) {
		this.discountStartDate = discountStartDate;
	}
	/**
	 * @return the discountEndDate
	 */
	public Date getDiscountEndDate() {
		return discountEndDate;
	}
	/**
	 * @param discountEndDate the discountEndDate to set
	 */
	public void setDiscountEndDate(Date discountEndDate) {
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
	public Date getPriceMarginStartDate() {
		return priceMarginStartDate;
	}
	/**
	 * @param priceMarginStartDate the priceMarginStartDate to set
	 */
	public void setPriceMarginStartDate(Date priceMarginStartDate) {
		this.priceMarginStartDate = priceMarginStartDate;
	}
	/**
	 * @return the priceMarginEndDate
	 */
	public Date getPriceMarginEndDate() {
		return priceMarginEndDate;
	}
	/**
	 * @param priceMarginEndDate the priceMarginEndDate to set
	 */
	public void setPriceMarginEndDate(Date priceMarginEndDate) {
		this.priceMarginEndDate = priceMarginEndDate;
	}
	
	
}
