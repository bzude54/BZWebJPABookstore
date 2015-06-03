package edu.uwpce.bzbookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BZCartItem {
	
    private static final Logger logger = LoggerFactory.getLogger(BZCartItem.class);
	
	private String cartItemId;
	private int cartItemQty;
	private BZBook cartItemBook;
	
	public BZCartItem(){
		this.cartItemQty = 0;
		this.cartItemBook = new BZBook();
		this.cartItemId = null;
	}

	public BZCartItem(BZBook book) {
		this.cartItemQty = 1;
		this.cartItemBook = book;
		this.cartItemId = book.getISBN();
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String itemId) {
			this.cartItemId = itemId;
			logger.info("in setCartItemId setting id to: " + this.cartItemId);
	}

	public int getCartItemQty() {
		return cartItemQty;
	}

	public void setCartItemQty(int qty) {
		this.cartItemQty = qty;
	}
	
	public double getCartItemTotalPrice() {
		return this.cartItemQty * cartItemBook.getPrice();	
	}


	public BZBook getCartItemBook() {
		return cartItemBook;
	}

	public void setCartItemBook(BZBook book) {
		logger.info("in setCartItemBook with book: " + book.getTitle());
		this.cartItemBook = book;
		this.cartItemId = book.getISBN();
	}


	public void incrementCartItemQty() {
		this.cartItemQty += 1;
	}


}
