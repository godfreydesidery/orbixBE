/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Date;

import javax.persistence.Column;
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
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.example.orbix_web.database.Audit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author GODFREY
 *
 */
@Entity
@Component
@Table(name = "sales")
@EntityListeners(AuditingEntityListener.class)
public class Sale extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
	@Temporal(TemporalType.DATE)
    private Date saleDate;
	
	@ManyToOne(targetEntity = CustomerInvoice.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "customer_invoice_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private CustomerInvoice customerInvoice;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "till_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Till till;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bill_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bill bill;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "day_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Day day;
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
	 * @return the customerInvoice
	 */
	public CustomerInvoice getCustomerInvoice() {
		return customerInvoice;
	}
	/**
	 * @param customerInvoice the customerInvoice to set
	 */
	public void setCustomerInvoice(CustomerInvoice customerInvoice) {
		this.customerInvoice = customerInvoice;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the till
	 */
	public Till getTill() {
		return till;
	}
	/**
	 * @param till the till to set
	 */
	public void setTill(Till till) {
		this.till = till;
	}
	/**
	 * @return the bill
	 */
	public Bill getBill() {
		return bill;
	}
	/**
	 * @param bill the bill to set
	 */
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	/**
	 * @return the day
	 */
	public Day getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(Day day) {
		this.day = day;
	}
	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}
	/**
	 * @param saleDate the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
}