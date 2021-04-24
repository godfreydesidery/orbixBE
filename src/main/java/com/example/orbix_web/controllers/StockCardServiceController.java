/**
 * 
 */
package com.example.orbix_web.controllers;

import java.time.LocalDate;
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
	
	
	
	public StockCard receiveItem(Item item, double qtyOrdered, LocalDate dateOrdered, double qtyReceived, LocalDate _dateReceived, double stockBalance, String lotNo, LocalDate expiryDate) {
		StockCard stockCard = new StockCard();
		stockCard.setItem(item);
		stockCard.setQtyOrdered(qtyOrdered);
		stockCard.setDateOrdered(dateOrdered);
		stockCard.setQtyReceived(qtyReceived);
		stockCard.setDateReceived(_dateReceived);
		stockCard.setStockBalance(stockBalance);
		stockCard.setLotNo(lotNo);
		stockCard.setExpiryDate(expiryDate);
		return stockCard;
	}
	public void removeFromStock() {
		StockCard stockCard = new StockCard();
		
	}
	public StockCard returnToVendor(Item item, double qty, LocalDate date, double stockBalance) {
		StockCard stockCard = new StockCard();
		stockCard.setItem(item);
		stockCard.setQtyReturnToVendor(qty);
		stockCard.setDateReturnToVendor(date);
		stockCard.setStockBalance(stockBalance);
		
		return stockCard;
	}
	public StockCard initialStock(Item item, double stockBalance) {
		StockCard stockCard = new StockCard();
		stockCard.setItem(item);
		stockCard.setStockBalance(stockBalance);
		return stockCard;
	}
	public void returnByCustomer() {
		
	}
	public void adjustStock() {
		
	}
	public void sellItem() {
		
	}
	public StockCard creditSale(Item item, LocalDate localDate, double qty, String invoiceNo, double stockBalance) {
		StockCard stockCard = new StockCard();
		stockCard.setItem(item);
		stockCard.setCreditSaleDate(localDate);
		stockCard.setCreditSaleQty(qty);
		stockCard.setCreditSaleInvoiceNo(invoiceNo);
		stockCard.setStockBalance(stockBalance);
		return stockCard;
	}

}
