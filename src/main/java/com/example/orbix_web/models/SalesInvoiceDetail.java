/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.util.Date;

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
import javax.validation.constraints.NotNull;

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
@Table(name = "sales_invoice_details")
@EntityListeners(AuditingEntityListener.class)
public class SalesInvoiceDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
    private String itemCode;
	@NotBlank
    private String description;
	@NotNull
	private double price;
	private double discount;
	@NotNull
	private double qty;
	private Date returnFirstDate;
	private int returnPeriod;
	private LocalDate returnLastDate;
	
	
	@ManyToOne(targetEntity = SalesInvoice.class, fetch = FetchType.EAGER,  optional = true)
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
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the qty
	 */
	public double getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(double qty) {
		this.qty = qty;
	}

	

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @return the returnFirstDate
	 */
	public Date getReturnFirstDate() {
		return returnFirstDate;
	}

	/**
	 * @param returnFirstDate the returnFirstDate to set
	 */
	public void setReturnFirstDate(Date returnFirstDate) {
		this.returnFirstDate = returnFirstDate;
	}

	/**
	 * @return the returnPeriod
	 */
	public int getReturnPeriod() {
		return returnPeriod;
	}

	/**
	 * @param returnPeriod the returnPeriod to set
	 */
	public void setReturnPeriod(int returnPeriod) {
		this.returnPeriod = returnPeriod;
	}

	/**
	 * @return the returnLastDate
	 */
	public LocalDate getReturnLastDate() {
		return returnLastDate;
	}

	/**
	 * @param returnLastDate2 the returnLastDate to set
	 */
	public void setReturnLastDate(LocalDate returnLastDate2) {
		this.returnLastDate = returnLastDate2;
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
