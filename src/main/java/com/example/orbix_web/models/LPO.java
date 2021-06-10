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
	private LocalDate lpoDate;
    private int validityPeriod;
	private LocalDate validUntil;
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
    private List<LpoDetail> lpoDetails;
	
	

    @Transient
    public int getNumberOfProducts() {
        return this.lpoDetails.size();
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
	 * @return the sum
	 */
	public double getSum() {
		return sum;
	}


	/**
	 * @return the lpoDate
	 */
	public LocalDate getLpoDate() {
		return lpoDate;
	}


	/**
	 * @param systemDate the lpoDate to set
	 */
	public void setLpoDate(LocalDate systemDate) {
		this.lpoDate = systemDate;
	}


	/**
	 * @return the validUntil
	 */
	public LocalDate getValidUntil() {
		return validUntil;
	}


	/**
	 * @param validUntil the validUntil to set
	 */
	public void setValidUntil(LocalDate validUntil) {
		this.validUntil = validUntil;
	}


	/**
	 * @param sum the sum to set
	 */
	public void setSum(double sum) {
		this.sum = sum;
	}


	/**
	 * @return the lpoDetails
	 */
	public List<LpoDetail> getLpoDetails() {
		return lpoDetails;
	}


	/**
	 * @param lpoDetails the lpoDetails to set
	 */
	public void setLpoDetails(List<LpoDetail> lpoDetails) {
		this.lpoDetails = lpoDetails;
	}

}

