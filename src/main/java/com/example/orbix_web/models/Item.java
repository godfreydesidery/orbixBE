/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
public class Item extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String itemCode;
	@Column(unique = true)
    private String primaryBarcode;
	private String longDescription;
	private String shortDescription;
	private int packSize;
	private double unitCostPrice;
	private double unitRetailPrice;
	private double discount;
	private double vat;
	private double profitMargin;
	private String standardUom;
	private String status;
	private String ingredient;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "shops_items", joinColumns = {
        @JoinColumn(name = "item_id", nullable = false, updatable = false) },
        inverseJoinColumns = { @JoinColumn(name = "shop_id",
            nullable = false, updatable = false) 
        })
	private Set<Shop> shops;
	
	
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
	 * @return the primaryScanCode
	 */
	public String getPrimaryBarcode() {
		return primaryBarcode;
	}
	/**
	 * @param primaryScanCode the primaryScanCode to set
	 */
	public void setPrimaryBarcode(String primaryBarcode) {
		this.primaryBarcode = primaryBarcode;
	}
	/**
	 * @return the longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}
	/**
	 * @param longDescription the longDescription to set
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	/**
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}
	/**
	 * @param shortDescription the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	/**
	 * @return the packSize
	 */
	public int getPackSize() {
		return packSize;
	}
	/**
	 * @param packSize the packSize to set
	 */
	public void setPackSize(int packSize) {
		this.packSize = packSize;
	}
	/**
	 * @return the unitCostPrice
	 */
	public double getUnitCostPrice() {
		return unitCostPrice;
	}
	/**
	 * @param unitCostPrice the unitCostPrice to set
	 */
	public void setUnitCostPrice(double unitCostPrice) {
		this.unitCostPrice = unitCostPrice;
	}
	/**
	 * @return the unitRetalPrice
	 */
	public double getUnitRetailPrice() {
		return unitRetailPrice;
	}
	/**
	 * @param unitRetalPrice the unitRetalPrice to set
	 */
	public void setUnitRetailPrice(double unitRetailPrice) {
		this.unitRetailPrice = unitRetailPrice;
	}
	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	/**
	 * @return the vat
	 */
	public double getVat() {
		return vat;
	}
	/**
	 * @param vat the vat to set
	 */
	public void setVat(double vat) {
		this.vat = vat;
	}
	/**
	 * @return the margin
	 */
	public double getProfitMargin() {
		return profitMargin;
	}
	/**
	 * @param margin the margin to set
	 */
	public void setProfitMargin(double profitMargin) {
		this.profitMargin = profitMargin;
	}
	/**
	 * @return the uom
	 */
	public String getStandardUom() {
		return standardUom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setStandardUom(String standardUom) {
		this.standardUom = standardUom;
	}
	/**
	 * @return the discontinued
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the ingredients
	 */
	public String getIngredient() {
		return ingredient;
	}
	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
}
