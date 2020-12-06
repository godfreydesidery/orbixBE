/**
 * 
 */
package com.example.orbix_web.models;

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
@Table(name = "inventory")
@EntityListeners(AuditingEntityListener.class)
public class Inventory extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
    private double quantity;
    private double maximumInventory;
    private double minimumInventory;
    private double defaultReOrderLevel;
    private double reOrderQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
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
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the maximumInventory
	 */
	public double getMaximumInventory() {
		return maximumInventory;
	}

	/**
	 * @param maximumInventory the maximumInventory to set
	 */
	public void setMaximumInventory(double maximumInventory) {
		this.maximumInventory = maximumInventory;
	}

	/**
	 * @return the minimumInventory
	 */
	public double getMinimumInventory() {
		return minimumInventory;
	}

	/**
	 * @param minimumInventory the minimumInventory to set
	 */
	public void setMinimumInventory(double minimumInventory) {
		this.minimumInventory = minimumInventory;
	}

	/**
	 * @return the defaultReOrderLevel
	 */
	public double getDefaultReOrderLevel() {
		return defaultReOrderLevel;
	}

	/**
	 * @param defaultReOrderLevel the defaultReOrderLevel to set
	 */
	public void setDefaultReOrderLevel(double defaultReOrderLevel) {
		this.defaultReOrderLevel = defaultReOrderLevel;
	}

	/**
	 * @return the reOrderQuantity
	 */
	public double getReOrderQuantity() {
		return reOrderQuantity;
	}

	/**
	 * @param reOrderQuantity the reOrderQuantity to set
	 */
	public void setReOrderQuantity(double reOrderQuantity) {
		this.reOrderQuantity = reOrderQuantity;
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

}
