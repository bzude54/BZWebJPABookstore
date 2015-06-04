package edu.uwpce.bzbookstore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class CartItem {
	
    private static final Logger logger = LoggerFactory.getLogger(CartItem.class);
	
	private String cartItemId;
	private int cartItemQty;
	private Book cartItemBook;
	
	public CartItem(){
		this.cartItemQty = 0;
		this.cartItemBook = new Book();
		this.cartItemId = null;
	}

	public CartItem(Book book) {
		this.cartItemQty = 1;
		this.cartItemBook = book;
		this.cartItemId = book.getIsbn();
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
//		return this.cartItemQty * cartItemBook.getPrice();
		return 10.0;
	}


	public Book getCartItemBook() {
		return cartItemBook;
	}

	public void setCartItemBook(Book book) {
		logger.info("in setCartItemBook with book: " + book.getTitle());
		this.cartItemBook = book;
		this.cartItemId = book.getIsbn();
	}


	public void incrementCartItemQty() {
		this.cartItemQty += 1;
	}


}
