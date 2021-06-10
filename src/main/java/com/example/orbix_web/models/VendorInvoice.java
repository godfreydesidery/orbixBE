/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
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
@Entity
@Component
@Table(name = "vendor_invoices")
@EntityListeners(AuditingEntityListener.class)
public class VendorInvoice {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String invoiceNo;
	private LocalDate invoiceDate;
	private LocalDate bookingDate;
	private double invoiceTotal;
	private double amountPaid;
	private String invoiceComments;
	
	@ManyToOne(targetEntity = Supplier.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "supplier_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Supplier supplier;
	
	@OneToMany(targetEntity = VendorInvoiceHistory.class, mappedBy = "invoice", fetch = FetchType.EAGER, orphanRemoval = true)
    @Valid
    @JsonIgnoreProperties("invoice")
    private List<VendorInvoiceHistory> vendorInvoiceHistory;
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
	 * @return the bookingDate
	 */
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @return the invoiceTotal
	 */
	public double getInvoiceTotal() {
		return invoiceTotal;
	}
	/**
	 * @param invoiceTotal the invoiceTotal to set
	 */
	public void setInvoiceTotal(double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}
	/**
	 * @return the amountPaid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}
	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}	
	/**
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	/**
	 * @return the vendorInvoiceHistory
	 */
	public List<VendorInvoiceHistory> getVendorInvoiceHistory() {
		return vendorInvoiceHistory;
	}
	/**
	 * @param vendorInvoiceHistory the vendorInvoiceHistory to set
	 */
	public void setVendorInvoiceHistory(List<VendorInvoiceHistory> vendorInvoiceHistory) {
		this.vendorInvoiceHistory = vendorInvoiceHistory;
	}
	/**
	 * @return the invoiceComments
	 */
	public String getInvoiceComments() {
		return invoiceComments;
	}
	/**
	 * @param invoiceComments the invoiceComments to set
	 */
	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}
	

}
