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
    private String primaryScanCode;
	private String longDescription;
	private String shortDescription;
	private int packSize;
	private double unitCostPrice;
	private double unitRetalPrice;
	private double discount;
	private double vat;
	private double margin;
	private String uom;
	private String discontinued;
	private String ingredients;
	
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
	public String getPrimaryScanCode() {
		return primaryScanCode;
	}
	/**
	 * @param primaryScanCode the primaryScanCode to set
	 */
	public void setPrimaryScanCode(String primaryScanCode) {
		this.primaryScanCode = primaryScanCode;
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
	public double getUnitRetalPrice() {
		return unitRetalPrice;
	}
	/**
	 * @param unitRetalPrice the unitRetalPrice to set
	 */
	public void setUnitRetalPrice(double unitRetalPrice) {
		this.unitRetalPrice = unitRetalPrice;
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
	public double getMargin() {
		return margin;
	}
	/**
	 * @param margin the margin to set
	 */
	public void setMargin(double margin) {
		this.margin = margin;
	}
	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}
	/**
	 * @return the discontinued
	 */
	public String getDiscontinued() {
		return discontinued;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	/**
	 * @return the ingredients
	 */
	public String getIngredients() {
		return ingredients;
	}
	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
}
