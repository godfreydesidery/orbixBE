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
	private double qtyOrdered;
	private LocalDate dateOrdered;
	private double qtyReceived;
	private LocalDate dateReceived;
	String lotNo;
	private LocalDate expiryDate;
	private double qtyReturnToVendor;
	private LocalDate dateReturnToVendor;
	private double qtyReturnByCustomer;
	private LocalDate dateReturnByCustomer;
	private double qtyRemoveFromStock;
	private LocalDateTime dateRemoveFromStock;
	private String reasonRemoveFromStock;
	private double qtySold;
	private LocalDateTime dateSold;
	private double creditSaleQty;
	private LocalDate creditSaleDate;
	private String creditSaleInvoiceNo;
	private double stockBalance;
	
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
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(LocalDate expiryDate) {
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
	/**
	 * @return the qtyReturnToVendor
	 */
	public double getQtyReturnToVendor() {
		return qtyReturnToVendor;
	}
	/**
	 * @param qtyReturnToVendor the qtyReturnToVendor to set
	 */
	public void setQtyReturnToVendor(double qtyReturnToVendor) {
		this.qtyReturnToVendor = qtyReturnToVendor;
	}
	
	/**
	 * @return the qtyReturnByCustomer
	 */
	public double getQtyReturnByCustomer() {
		return qtyReturnByCustomer;
	}
	/**
	 * @param qtyReturnByCustomer the qtyReturnByCustomer to set
	 */
	public void setQtyReturnByCustomer(double qtyReturnByCustomer) {
		this.qtyReturnByCustomer = qtyReturnByCustomer;
	}
	
	
	/**
	 * @return the qtyRemoveFromStock
	 */
	public double getQtyRemoveFromStock() {
		return qtyRemoveFromStock;
	}
	/**
	 * @param qtyRemoveFromStock the qtyRemoveFromStock to set
	 */
	public void setQtyRemoveFromStock(double qtyRemoveFromStock) {
		this.qtyRemoveFromStock = qtyRemoveFromStock;
	}
	
	
	/**
	 * @return the reasonRemoveFromStock
	 */
	public String getReasonRemoveFromStock() {
		return reasonRemoveFromStock;
	}
	/**
	 * @param reasonRemoveFromStock the reasonRemoveFromStock to set
	 */
	public void setReasonRemoveFromStock(String reasonRemoveFromStock) {
		this.reasonRemoveFromStock = reasonRemoveFromStock;
	}
	/**
	 * @return the qtySold
	 */
	public double getQtySold() {
		return qtySold;
	}
	/**
	 * @param qtySold the qtySold to set
	 */
	public void setQtySold(double qtySold) {
		this.qtySold = qtySold;
	}
	/**
	 * @return the dateOrdered
	 */
	public LocalDate getDateOrdered() {
		return dateOrdered;
	}
	/**
	 * @param dateOrdered2 the dateOrdered to set
	 */
	public void setDateOrdered(LocalDate dateOrdered2) {
		this.dateOrdered = dateOrdered2;
	}
	/**
	 * @return the dateReceived
	 */
	public LocalDate getDateReceived() {
		return dateReceived;
	}
	/**
	 * @param _dateReceived the dateReceived to set
	 */
	public void setDateReceived(LocalDate _dateReceived) {
		this.dateReceived = _dateReceived;
	}
	/**
	 * @return the dateReturnToVendor
	 */
	public LocalDate getDateReturnToVendor() {
		return dateReturnToVendor;
	}
	/**
	 * @param dateReturnToVendor the dateReturnToVendor to set
	 */
	public void setDateReturnToVendor(LocalDate dateReturnToVendor) {
		this.dateReturnToVendor = dateReturnToVendor;
	}
	/**
	 * @return the dateReturnByCustomer
	 */
	public LocalDate getDateReturnByCustomer() {
		return dateReturnByCustomer;
	}
	/**
	 * @param dateReturnByCustomer the dateReturnByCustomer to set
	 */
	public void setDateReturnByCustomer(LocalDate dateReturnByCustomer) {
		this.dateReturnByCustomer = dateReturnByCustomer;
	}
	/**
	 * @return the dateRemoveFromStock
	 */
	public LocalDateTime getDateRemoveFromStock() {
		return dateRemoveFromStock;
	}
	/**
	 * @param dateRemoveFromStock the dateRemoveFromStock to set
	 */
	public void setDateRemoveFromStock(LocalDateTime dateRemoveFromStock) {
		this.dateRemoveFromStock = dateRemoveFromStock;
	}
	/**
	 * @return the dateSold
	 */
	public LocalDateTime getDateSold() {
		return dateSold;
	}
	/**
	 * @param dateSold the dateSold to set
	 */
	public void setDateSold(LocalDateTime dateSold) {
		this.dateSold = dateSold;
	}
	/**
	 * @return the creditSaleQty
	 */
	public double getCreditSaleQty() {
		return creditSaleQty;
	}
	/**
	 * @param creditSaleQty the creditSaleQty to set
	 */
	public void setCreditSaleQty(double creditSaleQty) {
		this.creditSaleQty = creditSaleQty;
	}
	/**
	 * @return the creditSaleDate
	 */
	public LocalDate getCreditSaleDate() {
		return creditSaleDate;
	}
	/**
	 * @param creditSaleDate the creditSaleDate to set
	 */
	public void setCreditSaleDate(LocalDate creditSaleDate) {
		this.creditSaleDate = creditSaleDate;
	}
	/**
	 * @return the creditSaleInvoiceNo
	 */
	public String getCreditSaleInvoiceNo() {
		return creditSaleInvoiceNo;
	}
	/**
	 * @param creditSaleInvoiceNo the creditSaleInvoiceNo to set
	 */
	public void setCreditSaleInvoiceNo(String creditSaleInvoiceNo) {
		this.creditSaleInvoiceNo = creditSaleInvoiceNo;
	}
	
	
}
