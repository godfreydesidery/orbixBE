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
@Table(name = "sales_return_details")
@EntityListeners(AuditingEntityListener.class)
public class SalesReturnDetail {
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
	private double qtyReturned;
	private String reason;
	
	
	
	@ManyToOne(targetEntity = SalesInvoice.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "sales_return_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private SalesReturn salesReturn;



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
	 * @return the qtyReturned
	 */
	public double getQtyReturned() {
		return qtyReturned;
	}



	/**
	 * @param qtyReturned the qtyReturned to set
	 */
	public void setQtyReturned(double qtyReturned) {
		this.qtyReturned = qtyReturned;
	}



	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}



	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}



	/**
	 * @return the salesReturn
	 */
	public SalesReturn getSalesReturn() {
		return salesReturn;
	}



	/**
	 * @param salesReturn the salesReturn to set
	 */
	public void setSalesReturn(SalesReturn salesReturn) {
		this.salesReturn = salesReturn;
	}

}
