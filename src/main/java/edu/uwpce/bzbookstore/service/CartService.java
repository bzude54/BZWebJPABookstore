package edu.uwpce.bzbookstore.service;

import edu.uwpce.bzbookstore.model.Cart;
import edu.uwpce.bzbookstore.model.CartItem;

import java.util.Map;

public interface CartService {
	
	Map<Integer, Cart> getAllCarts();
	
	void setAllCarts(Map<Integer, Cart> cart);
	
	void setCart(Cart cart);
	
	Cart getCart(int cartId);
	
	Cart updateCart(Cart cart);
	
	boolean deleteCart(int cartid);

	void addCartItem(int cartid, CartItem cartitem);
	
	CartItem getCartItem(int cartid, String cartitemid);
			
	void updateCartItem(int cartid, CartItem cartitem);
	
	boolean deleteCartItem(int cartid, String cartitemid);
			
	double getCartTax(int cartId);
		
	double getCartShippingCost(int cartId);
	

}
