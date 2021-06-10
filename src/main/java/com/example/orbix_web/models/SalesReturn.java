/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "sales_returns")
@EntityListeners(AuditingEntityListener.class)
public class SalesReturn {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private LocalDate returnDate;
	
	@ManyToOne(targetEntity = SalesInvoice.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "sales_invoice_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private SalesInvoice salesInvoice;
	
	@OneToMany(targetEntity = SalesReturnDetail.class, mappedBy = "salesReturn", fetch = FetchType.LAZY, orphanRemoval = true)
    @Valid
    @JsonIgnoreProperties("salesReturn")
    private List<SalesReturnDetail> salesReturnDetails;

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
	 * @return the returnDate
	 */
	public LocalDate getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
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

	/**
	 * @return the salesReturnDetails
	 */
	public List<SalesReturnDetail> getSalesReturnDetails() {
		return salesReturnDetails;
	}

	/**
	 * @param salesReturnDetails the salesReturnDetails to set
	 */
	public void setSalesReturnDetails(List<SalesReturnDetail> salesReturnDetails) {
		this.salesReturnDetails = salesReturnDetails;
	}

	

	
}
