/**
 * 
 */
package com.example.orbix_web.models;

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
@Table(name = "selling_price_changes")
@EntityListeners(AuditingEntityListener.class)
public class SellingPriceChange {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private double oldPrice;
	private double newPrice;
	private double priceChange;
	@Temporal(TemporalType.TIMESTAMP)
	Date dateTime;
	String reason;
	String changeType;
	
	@ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY,  optional = true)
    @JoinColumn(name = "item_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
	Item item;

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
	 * @return the oldPrice
	 */
	public double getOldPrice() {
		return oldPrice;
	}

	/**
	 * @param oldPrice the oldPrice to set
	 */
	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}

	/**
	 * @return the newPrice
	 */
	public double getNewPrice() {
		return newPrice;
	}

	/**
	 * @param newPrice the newPrice to set
	 */
	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}

	/**
	 * @return the priceChange
	 */
	public double getPriceChange() {
		return priceChange;
	}

	/**
	 * @param priceChange the priceChange to set
	 */
	public void setPriceChange(double priceChange) {
		this.priceChange = priceChange;
	}

	/**
	 * @return the dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

}
