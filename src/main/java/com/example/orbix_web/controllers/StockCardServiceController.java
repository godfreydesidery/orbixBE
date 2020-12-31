/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.StockCard;
import com.example.orbix_web.repositories.StockCardRepository;

/**
 * @author GODFREY
 *
 */
public class StockCardServiceController {
	
	@Autowired
	private StockCardRepository stockCardRepository;
	
	
	
	public StockCard receiveItem(Item item, double qtyOrdered, Date dateOrdered, double qtyReceived, Date dateReceived, double stockBalance, String lotNo, Date expiryDate) {
		StockCard stockCard = new StockCard();
		stockCard.setItem(item);
		stockCard.setQtyOrdered(qtyOrdered);
		stockCard.setDateOrdered(dateOrdered);
		stockCard.setQtyReceived(qtyReceived);
		stockCard.setDateReceived(dateReceived);
		stockCard.setStockBalance(stockBalance);
		stockCard.setLotNo(lotNo);
		stockCard.setExpiryDate(expiryDate);
		return stockCard;
	}
	public void removeFromStock() {
		StockCard stockCard = new StockCard();
		
	}
	public StockCard returnToVendor(Item item, double qty, Date date, double stockBalance) {
		StockCard stockCard = new StockCard();
		stockCard.setItem(item);
		stockCard.setQtyReturnToVendor(qty);
		stockCard.setDateReturnToVendor(date);
		stockCard.setStockBalance(stockBalance);
		
		return stockCard;
	}
	public void returnByCustomer() {
		
	}
	public void adjustStock() {
		
	}
	public void sellItem() {
		
	}

}
