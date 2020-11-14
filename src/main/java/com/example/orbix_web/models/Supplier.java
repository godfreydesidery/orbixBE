/**
 * 
 */
package com.example.orbix_web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.orbix_web.database.Audit;

/**
 * @author GODFREY
 *
 */
@Entity
@Audited
@Table(name = "suppliers")
@EntityListeners(AuditingEntityListener.class)
public class Supplier extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique=true)
    private String supplierCode;
	@NotBlank
    private String supplierName;
	@NotBlank
    private String tin;
	@NotBlank
    private String vrn;
	@NotBlank
    private String termsOfPayment;
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
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}
	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return the tin
	 */
	public String getTin() {
		return tin;
	}
	/**
	 * @param tin the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
	}
	/**
	 * @return the vrn
	 */
	public String getVrn() {
		return vrn;
	}
	/**
	 * @param vrn the vrn to set
	 */
	public void setVrn(String vrn) {
		this.vrn = vrn;
	}
	/**
	 * @return the termsOfPayment
	 */
	public String getTermsOfPayment() {
		return termsOfPayment;
	}
	/**
	 * @param termsOfPayment the termsOfPayment to set
	 */
	public void setTermsOfPayment(String termsOfPayment) {
		this.termsOfPayment = termsOfPayment;
	}
}

