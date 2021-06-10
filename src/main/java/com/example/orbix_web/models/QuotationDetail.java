/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.util.Date;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "quotation_details")
@EntityListeners(AuditingEntityListener.class)
public class QuotationDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String barcode;
	@NotBlank
    private String itemCode;
	@NotBlank
    private String description;
	@NotNull
	private double price;
	private double discount;
	private double tax;
	@NotNull
	private double qty;
	
	@ManyToOne(targetEntity = Quotation.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "quotation_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Quotation quotation;

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
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
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
	 * @return the quotation
	 */
	public Quotation getQuotation() {
		return quotation;
	}

	/**
	 * @param quotation the quotation to set
	 */
	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
