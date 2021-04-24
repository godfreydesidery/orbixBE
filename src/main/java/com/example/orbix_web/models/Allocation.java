/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "allocations")
@EntityListeners(AuditingEntityListener.class)
public class Allocation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String allocationNo;
	private LocalDate allocationDate;
	private double allocationAmount;
	
	
	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "customer_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Customer customer;
	
	@ManyToOne(targetEntity = SalesInvoice.class, fetch = FetchType.LAZY,  optional = true)
    @JoinColumn(name = "sales_invoice_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private SalesInvoice salesInvoice;


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
	 * @return the allocationNo
	 */
	public String getAllocationNo() {
		return allocationNo;
	}


	/**
	 * @param allocationNo the allocationNo to set
	 */
	public void setAllocationNo(String allocationNo) {
		this.allocationNo = allocationNo;
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
	 * @return the allocationDate
	 */
	public LocalDate getAllocationDate() {
		return allocationDate;
	}


	/**
	 * @param allocationDate the allocationDate to set
	 */
	public void setAllocationDate(LocalDate allocationDate) {
		this.allocationDate = allocationDate;
	}


	/**
	 * @return the allocationAmount
	 */
	public double getAllocationAmount() {
		return allocationAmount;
	}


	/**
	 * @param allocationAmount the allocationAmount to set
	 */
	public void setAllocationAmount(double allocationAmount) {
		this.allocationAmount = allocationAmount;
	}


	/**
	 * @return the salesInvoice
	 */
	public SalesInvoice getSalesInvoice() {
		return salesInvoice;
	}


	/**
	 * @param salesInvoice the salesInvoice to set
	 */
	public void setSalesInvoice(SalesInvoice salesInvoice) {
		this.salesInvoice = salesInvoice;
	}


	
}
