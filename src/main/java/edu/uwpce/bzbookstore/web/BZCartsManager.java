package edu.uwpce.bzbookstore.web;

import java.util.Map;

public interface BZCartsManager {
	
	Map<Integer, BZCart> getAllCarts();
	
	void setAllCarts(Map<Integer, BZCart> cart);
	
	void setCart(BZCart cart);
	
	BZCart getCart(int cartId);
	
	BZCart updateCart(BZCart cart);
	
	boolean deleteCart(int cartid);

	void addCartItem(int cartid, BZCartItem cartitem);
	
	BZCartItem getCartItem(int cartid, String cartitemid);
			
	void updateCartItem(int cartid, BZCartItem cartitem);
	
	boolean deleteCartItem(int cartid, String cartitemid);
			
	double getCartTax(int cartId);
		
	double getCartShippingCost(int cartId);
	

}
