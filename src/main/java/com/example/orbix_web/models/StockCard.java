/**
 * 
 */
package com.example.orbix_web.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	private LocalDate date;
	private Date dateTime;
	private String reference;
	double qtyIn;
	double qtyOut;
	double balance;
	
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
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	/**
	 * @return the qtyIn
	 */
	public double getQtyIn() {
		return qtyIn;
	}
	/**
	 * @param qtyIn the qtyIn to set
	 */
	public void setQtyIn(double qtyIn) {
		this.qtyIn = qtyIn;
	}
	/**
	 * @return the qtyOut
	 */
	public double getQtyOut() {
		return qtyOut;
	}
	/**
	 * @param qtyOut the qtyOut to set
	 */
	public void setQtyOut(double qtyOut) {
		this.qtyOut = qtyOut;
	}
	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
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
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	/**
	 * @return the dateTime
	 */
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
	
	
	
}
