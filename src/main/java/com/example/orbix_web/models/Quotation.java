/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "quotations")
@EntityListeners(AuditingEntityListener.class)
public class Quotation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String quotationNo;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	
	private String customerName;
	private String customerAddress;
	private String customerTelephone;
	private String customerEmail;
	private String customerFax;
	
	private String status;
	
	
	@OneToMany(targetEntity = QuotationDetail.class, mappedBy = "quotation", fetch = FetchType.EAGER, orphanRemoval = true)
    @Valid
    @JsonIgnoreProperties("quotation")
    private List<QuotationDetail> quotationDetails;


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
	 * @return the quotationNo
	 */
	public String getQuotationNo() {
		return quotationNo;
	}


	/**
	 * @param quotationNo the quotationNo to set
	 */
	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
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
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}


	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}


	/**
	 * @return the customerTelephone
	 */
	public String getCustomerTelephone() {
		return customerTelephone;
	}


	/**
	 * @param customerTelephone the customerTelephone to set
	 */
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}


	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}


	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}


	/**
	 * @return the customerFax
	 */
	public String getCustomerFax() {
		return customerFax;
	}


	/**
	 * @param customerFax the customerFax to set
	 */
	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the quotationDetails
	 */
	public List<QuotationDetail> getQuotationDetails() {
		return quotationDetails;
	}


	/**
	 * @param quotationDetails the quotationDetails to set
	 */
	public void setQuotationDetails(List<QuotationDetail> quotationDetails) {
		this.quotationDetails = quotationDetails;
	}

}
