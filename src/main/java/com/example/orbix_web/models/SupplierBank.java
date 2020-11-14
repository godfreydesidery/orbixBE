/**
 * 
 */
package com.example.orbix_web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.orbix_web.database.Audit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author GODFREY
 *
 */
@Entity
@Audited
@Table(name = "supplier_banks")
@EntityListeners(AuditingEntityListener.class)
public class SupplierBank extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String bankAccountName;
    private String bankAccountAddress;
    private String bankPostCode;
    private String bankName;
    private String bankAccountNo;
	private String bankStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Supplier supplier;
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
	 * @return the bankAccountName
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}
	/**
	 * @param bankAccountName the bankAccountName to set
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	/**
	 * @return the bankAccountAddress
	 */
	public String getBankAccountAddress() {
		return bankAccountAddress;
	}
	/**
	 * @param bankAccountAddress the bankAccountAddress to set
	 */
	public void setBankAccountAddress(String bankAccountAddress) {
		this.bankAccountAddress = bankAccountAddress;
	}
	/**
	 * @return the bankPostCode
	 */
	public String getBankPostCode() {
		return bankPostCode;
	}
	/**
	 * @param bankPostCode the bankPostCode to set
	 */
	public void setBankPostCode(String bankPostCode) {
		this.bankPostCode = bankPostCode;
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the bankAccountNo
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	/**
	 * @param bankAccountNo the bankAccountNo to set
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	/**
	 * @return the bankStatus
	 */
	public String getBankStatus() {
		return bankStatus;
	}
	/**
	 * @param bankStatus the bankStatus to set
	 */
	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}
}
