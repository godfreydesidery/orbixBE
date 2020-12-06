/**
 * 
 */
package com.example.orbix_web.models;

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
@Table(name = "lpo_details")
@EntityListeners(AuditingEntityListener.class)
public class LpoDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
    private String itemCode;
	@NotBlank
    private String description;
	private double costPrice;
	@NotBlank
	private double qtyOrdered;
	private double qtyReceived;
	
	@ManyToOne(targetEntity = Lpo.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "lpo_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Lpo lpo;

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
	 * @return the costPrice
	 */
	public double getCostPrice() {
		return costPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * @return the qtyOrdered
	 */
	public double getQtyOrdered() {
		return qtyOrdered;
	}

	/**
	 * @param qtyOrdered the qtyOrdered to set
	 */
	public void setQtyOrdered(double qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	/**
	 * @return the qtyReceived
	 */
	public double getQtyReceived() {
		return qtyReceived;
	}

	/**
	 * @param qtyReceived the qtyReceived to set
	 */
	public void setQtyReceived(double qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	/**
	 * @return the lpo
	 */
	public Lpo getLpo() {
		return lpo;
	}

	/**
	 * @param lpo the lpo to set
	 */
	public void setLpo(Lpo lpo) {
		this.lpo = lpo;
	}
}
