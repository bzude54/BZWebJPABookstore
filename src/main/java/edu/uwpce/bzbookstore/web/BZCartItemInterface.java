package edu.uwpce.bzbookstore.web;

public interface BZCartItemInterface {
	
	
	BZBook getCartItemBook();	

	void setCartItemBook(BZBook book);
	
	String getCartItemId();
	
	void setCartItemId(String itemId);
	
	int getCartItemQty();

	void setCartItemQty(int qty);
	
	double getCartItemTotalPrice();

	void incrementCartItemQty();


}
