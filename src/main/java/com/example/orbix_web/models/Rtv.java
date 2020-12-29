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
@Table(name = "rtvs")
@EntityListeners(AuditingEntityListener.class)
public class Rtv {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	
	@NotBlank
	@Column(unique = true)
    private String rtvNo;
	@Temporal(TemporalType.DATE)
	private Date rtvDate;
	private String status;

	@ManyToOne(targetEntity = Supplier.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "supplier_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Supplier supplier;

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
	 * @return the rtvNo
	 */
	public String getRtvNo() {
		return rtvNo;
	}

	/**
	 * @param rtvNo the rtvNo to set
	 */
	public void setRtvNo(String rtvNo) {
		this.rtvNo = rtvNo;
	}

	/**
	 * @return the rtvDate
	 */
	public Date getRtvDate() {
		return rtvDate;
	}

	/**
	 * @param rtvDate the rtvDate to set
	 */
	public void setRtvDate(Date rtvDate) {
		this.rtvDate = rtvDate;
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
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
}
