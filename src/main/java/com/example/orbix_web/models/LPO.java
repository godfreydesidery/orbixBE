/**
 * 
 */
package com.example.orbix_web.models;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.example.orbix_web.database.Audit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "lpos")
@EntityListeners(AuditingEntityListener.class)
public class Lpo extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String lpoNo;
	private String createdBy;
	private String approvedBy;
	@Temporal(TemporalType.DATE)
	private Date lpoDate;
    private int validityPeriod;
	@Temporal(TemporalType.DATE)
	private Date validUntil;
	private String status;
	@ManyToOne(targetEntity = Supplier.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "supplier_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Supplier supplier;
	private double sum;
	
    @OneToMany(targetEntity = LpoDetail.class, mappedBy = "lpo", fetch = FetchType.EAGER, orphanRemoval = true)
    @Valid
    @JsonIgnoreProperties("lpo")
    private List<LpoDetail> lpoDetail;
	
	

    @Transient
    public int getNumberOfProducts() {
        return this.lpoDetail.size();
    }
	
	
	//@ManyToMany(cascade = CascadeType.ALL,mappedBy = "lpos")
    //private Set<Payment> payments;
	
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
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}
	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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

	/**
	 * @return the lpoDetail
	 */
	public List<LpoDetail> getLpoDetail() {
		return lpoDetail;
	}

	/**
	 * @param lpoDetail the lpoDetail to set
	 */
	public void setLpoDetail(List<LpoDetail> lpoDetail) {
		this.lpoDetail = lpoDetail;
	}

	/**
	 * @return the sum
	 */
	public double getSum() {
		return sum;
	}


	/**
	 * @return the lpoDate
	 */
	public Date getLpoDate() {
		return lpoDate;
	}


	/**
	 * @param lpoDate the lpoDate to set
	 */
	public void setLpoDate(Date lpoDate) {
		this.lpoDate = lpoDate;
	}

}

