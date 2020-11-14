/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.orbix_web.database.Audit;

/**
 * @author GODFREY
 *
 */
@Entity
@Audited
@Table(name = "lpos")
@EntityListeners(AuditingEntityListener.class)
public class LPO extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String lpoNo;
    private int validityPeriod;
	@NotBlank
	@Temporal(TemporalType.DATE)
	private Date validUntil;
	private String status;
	
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "lpos")
    private Set<Payment> payments;
	
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
	 * @return the lpoNo
	 */
	public String getLpoNo() {
		return lpoNo;
	}
	/**
	 * @param lpoNo the lpoNo to set
	 */
	public void setLpoNo(String lpoNo) {
		this.lpoNo = lpoNo;
	}
	/**
	 * @return the validityPeriod
	 */
	public int getValidityPeriod() {
		return validityPeriod;
	}
	/**
	 * @param validityPeriod the validityPeriod to set
	 */
	public void setValidityPeriod(int validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	/**
	 * @return the validUntil
	 */
	public Date getValidUntil() {
		return validUntil;
	}
	/**
	 * @param validUntil the validUntil to set
	 */
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
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
}

