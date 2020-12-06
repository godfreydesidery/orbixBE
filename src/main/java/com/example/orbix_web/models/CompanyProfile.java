/**
 * 
 */
package com.example.orbix_web.models;

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
@Table(name = "company_profile")
@EntityListeners(AuditingEntityListener.class)
@Embeddable
public class CompanyProfile extends Audit<String> {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotBlank
    private String companyName;
    private String companyLogo;
    private String tin;
    private String vrn;
    private String postAddress;
    private String postCode;
    private String physicalAddress;
    private String telephone;
    private String mobile;
    private String email;
    private String fax;
    private String bankAccountName;
    private String bankAccountAddress;
    private String bankPostCode;
    private String bankName;
    private String bankAccountNo;
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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the companyLogo
	 */
	public String getCompanyLogo() {
		return companyLogo;
	}
	/**
	 * @param companyLogo the companyLogo to set
	 */
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
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
	 * @return the postAddress
	 */
	public String getPostAddress() {
		return postAddress;
	}
	/**
	 * @param postAddress the postAddress to set
	 */
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return the physicalAddress
	 */
	public String getPhysicalAddress() {
		return physicalAddress;
	}
	/**
	 * @param physicalAddress the physicalAddress to set
	 */
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
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
	
}

