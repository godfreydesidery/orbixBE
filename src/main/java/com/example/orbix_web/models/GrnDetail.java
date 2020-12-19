/**
 * 
 */
package com.example.orbix_web.models;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "grn_details")
@EntityListeners(AuditingEntityListener.class)
public class GrnDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotBlank
    private String itemCode;
	@NotBlank
    private String description;
	@NotBlank
    private Long qtyOrdered;
	@NotBlank
    private Long qtyReceived;
    private Long supplierCp;
    private Long clientCp;
    @ManyToOne(targetEntity = Lpo.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "grn_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Grn grn;
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
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the qtyOrdered
	 */
	public Long getQtyOrdered() {
		return qtyOrdered;
	}
	/**
	 * @param qtyOrdered the qtyOrdered to set
	 */
	public void setQtyOrdered(Long qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}
	/**
	 * @return the qtyReceived
	 */
	public Long getQtyReceived() {
		return qtyReceived;
	}
	/**
	 * @param qtyReceived the qtyReceived to set
	 */
	public void setQtyReceived(Long qtyReceived) {
		this.qtyReceived = qtyReceived;
	}
	/**
	 * @return the supplierCp
	 */
	public Long getSupplierCp() {
		return supplierCp;
	}
	/**
	 * @param supplierCp the supplierCp to set
	 */
	public void setSupplierCp(Long supplierCp) {
		this.supplierCp = supplierCp;
	}
	/**
	 * @return the clientCp
	 */
	public Long getClientCp() {
		return clientCp;
	}
	/**
	 * @param clientCp the clientCp to set
	 */
	public void setClientCp(Long clientCp) {
		this.clientCp = clientCp;
	}
	/**
	 * @return the grn
	 */
	public Grn getGrn() {
		return grn;
	}
	/**
	 * @param grn the grn to set
	 */
	public void setGrn(Grn grn) {
		this.grn = grn;
	}
    
}
