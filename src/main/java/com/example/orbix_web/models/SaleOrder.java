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

/**
 * @author GODFREY
 *
 */
@Entity
@Audited
@Table(name = "sale_orders")
@EntityListeners(AuditingEntityListener.class)
public class SaleOrder extends Order{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String saleOrderNo;
    private int validityPeriod;
	@NotBlank
	@Temporal(TemporalType.DATE)
	private Date validUntil;
	private String status;
	
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "lpos")
    private Set<Payment> payments;
	
	
}
