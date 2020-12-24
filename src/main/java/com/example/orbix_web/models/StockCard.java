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
@Table(name = "stock_cards")
@EntityListeners(AuditingEntityListener.class)
public class StockCard {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private LocalDate dateOrdered;
	double qtyOrdered;
	private LocalDate dateReceived;
	double qtyReceived;
	String lotNo;
	@Temporal(TemporalType.DATE)
	private Date expiryDate;
	double stockBalance;	
	@ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY,  optional = true)
    @JoinColumn(name = "item_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Item item;
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
	 * @return the dateOrdered
	 */
	public LocalDate getDateOrdered() {
		return dateOrdered;
	}
	/**
	 * @param _dateOdered the dateOrdered to set
	 */
	public void setDateOrdered(LocalDate _dateOdered) {
		this.dateOrdered = _dateOdered;
	}
	
	/**
	 * @return the dateReceived
	 */
	public LocalDate getDateReceived() {
		return dateReceived;
	}
	/**
	 * @param dateReceived the dateReceived to set
	 */
	public void setDateReceived(LocalDate dateReceived) {
		this.dateReceived = dateReceived;
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
	 * @return the lotNo
	 */
	public String getLotNo() {
		return lotNo;
	}
	/**
	 * @param lotNo the lotNo to set
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	/**
	 * @return the stockBalance
	 */
	public double getStockBalance() {
		return stockBalance;
	}
	/**
	 * @param stockBalance the stockBalance to set
	 */
	public void setStockBalance(double stockBalance) {
		this.stockBalance = stockBalance;
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
	
}
