/**
 * 
 */
package com.example.orbix_web.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Long creditLimit;
    private int creditDays;
    private Long amountDue;
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
	 * @return the creditLimit
	 */
	public Long getCreditLimit() {
		return creditLimit;
	}
	/**
	 * @param creditLimit the creditLimit to set
	 */
	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}
	/**
	 * @return the amountDue
	 */
	public Long getAmountDue() {
		return amountDue;
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
	
	
}
