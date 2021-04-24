/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "sales_invoices")
@EntityListeners(AuditingEntityListener.class)
public class SalesInvoice {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String invoiceNo;
	private String createdBy;
	private String approvedBy;
	private LocalDate invoiceDate;
	private String terms;
	private String orderNo;
	@Column(nullable = true)
	private LocalDate dateShipped;
	private String shippedVia;
	@Temporal(TemporalType.DATE)
	private Date invoiceDueDate;
	private String invoiceStatus;
	private Long custId;
	private double invoiceAmount;
	private double invoiceAmountPayed;
	private double invoiceAmountDue;
	
	
	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "customer_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Customer customer;
	
	@OneToMany(targetEntity = SalesInvoiceDetail.class, mappedBy = "salesInvoice", fetch = FetchType.EAGER, orphanRemoval = true)
    @Valid
    @JsonIgnoreProperties("salesInvoice")
    private List<SalesInvoiceDetail> invoiceDetails;
	
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the invoiceDate
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	 * @return the custId
	 */
	public Long getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(Long custId) {
		this.custId = custId;
	}

	

	/**
	 * @return the invoiceAmount
	 */
	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	/**
	 * @param invoiceAmount the invoiceAmount to set
	 */
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	/**
	 * @return the invoiceAmountPayed
	 */
	public double getInvoiceAmountPayed() {
		return invoiceAmountPayed;
	}

	/**
	 * @param invoiceAmountPayed the invoiceAmountPayed to set
	 */
	public void setInvoiceAmountPayed(double invoiceAmountPayed) {
		this.invoiceAmountPayed = invoiceAmountPayed;
	}

	/**
	 * @return the invoiceAmountDue
	 */
	public double getInvoiceAmountDue() {
		return invoiceAmountDue;
	}

	/**
	 * @param invoiceAmountDue the invoiceAmountDue to set
	 */
	public void setInvoiceAmountDue(double invoiceAmountDue) {
		this.invoiceAmountDue = invoiceAmountDue;
	}

	/**
	 * @return the invoiceDueDate
	 */
	public Date getInvoiceDueDate() {
		return invoiceDueDate;
	}

	/**
	 * @param invoiceDueDate the invoiceDueDate to set
	 */
	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

	/**
	 * @return the invoiceStatus
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus the invoiceStatus to set
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return the terms
	 */
	public String getTerms() {
		return terms;
	}

	/**
	 * @param terms the terms to set
	 */
	public void setTerms(String terms) {
		this.terms = terms;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the dateShipped
	 */
	public LocalDate getDateShipped() {
		return dateShipped;
	}

	/**
	 * @param dateShipped the dateShipped to set
	 */
	public void setDateShipped(LocalDate dateShipped) {
		this.dateShipped = dateShipped;
	}

	/**
	 * @return the shippedVia
	 */
	public String getShippedVia() {
		return shippedVia;
	}

	/**
	 * @param shippedVia the shippedVia to set
	 */
	public void setShippedVia(String shippedVia) {
		this.shippedVia = shippedVia;
	}

	/**
	 * @return the invoiceDetails
	 */
	public List<SalesInvoiceDetail> getInvoiceDetails() {
		return invoiceDetails;
	}

	/**
	 * @param invoiceDetails the invoiceDetails to set
	 */
	public void setInvoiceDetails(List<SalesInvoiceDetail> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
}
