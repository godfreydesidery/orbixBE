/**
 * 
 */
package com.example.orbix_web.models;

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
@Table(name = "tills")
@EntityListeners(AuditingEntityListener.class)
public class Till extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique=true)
    private String tillNo;
	@NotBlank
	@Column(unique=true)
    private String tillName;
	private String status;
	
	private double cash;
	private double voucher;
	private double deposit;
	private double loyalty;
	private double crCard;
	private double cheque;
	private double cap;
	private double invoice;
	private double crNote;
	private double mobile;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "shop_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Shop shop;

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
	 * @return the tillNo
	 */
	public String getTillNo() {
		return tillNo;
	}

	/**
	 * @param tillNo the tillNo to set
	 */
	public void setTillNo(String tillNo) {
		this.tillNo = tillNo;
	}

	/**
	 * @return the tillName
	 */
	public String getTillName() {
		return tillName;
	}

	/**
	 * @param tillName the tillName to set
	 */
	public void setTillName(String tillName) {
		this.tillName = tillName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the cash
	 */
	public double getCash() {
		return cash;
	}

	/**
	 * @param cash the cash to set
	 */
	public void setCash(double cash) {
		this.cash = cash;
	}

	/**
	 * @return the voucher
	 */
	public double getVoucher() {
		return voucher;
	}

	/**
	 * @param voucher the voucher to set
	 */
	public void setVoucher(double voucher) {
		this.voucher = voucher;
	}

	/**
	 * @return the deposit
	 */
	public double getDeposit() {
		return deposit;
	}

	/**
	 * @param deposit the deposit to set
	 */
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	/**
	 * @return the loyalty
	 */
	public double getLoyalty() {
		return loyalty;
	}

	/**
	 * @param loyalty the loyalty to set
	 */
	public void setLoyalty(double loyalty) {
		this.loyalty = loyalty;
	}

	/**
	 * @return the crCard
	 */
	public double getCrCard() {
		return crCard;
	}

	/**
	 * @param crCard the crCard to set
	 */
	public void setCrCard(double crCard) {
		this.crCard = crCard;
	}

	/**
	 * @return the cheque
	 */
	public double getCheque() {
		return cheque;
	}

	/**
	 * @param cheque the cheque to set
	 */
	public void setCheque(double cheque) {
		this.cheque = cheque;
	}

	/**
	 * @return the cap
	 */
	public double getCap() {
		return cap;
	}

	/**
	 * @param cap the cap to set
	 */
	public void setCap(double cap) {
		this.cap = cap;
	}

	/**
	 * @return the invoice
	 */
	public double getInvoice() {
		return invoice;
	}

	/**
	 * @param invoice the invoice to set
	 */
	public void setInvoice(double invoice) {
		this.invoice = invoice;
	}

	/**
	 * @return the crNote
	 */
	public double getCrNote() {
		return crNote;
	}

	/**
	 * @param crNote the crNote to set
	 */
	public void setCrNote(double crNote) {
		this.crNote = crNote;
	}

	/**
	 * @return the mobile
	 */
	public double getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(double mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * @param shop the shop to set
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
}
