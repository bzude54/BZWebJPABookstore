package edu.uwpce.bzbookstore.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cart {
	
    private static final Logger logger = LoggerFactory.getLogger(Cart.class);
    
    private static final int STATE_TAX_PERCENT = 8;
	private static final double SHIPPING_COST_UNDER_THRESHOLD = 5.00;
	private static final double SHIPPING_COST_OVER_THRESHOLD = 15.00;
	private static final int SHIPPING_QTY_THRESHOLD = 5;
    
    List<CartItem> cartItems;
	int cartId;

	public Cart(){
		cartItems = new ArrayList<CartItem>();
	}
	
	public Cart(int userId) {
		cartItems = new ArrayList<CartItem>();
		this.cartId = userId;
	}
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(List<CartItem> cartitems) {
		this.cartItems = cartitems;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public CartItem getSingleCartItem(String itemId) {
		logger.info("in getsinglecartitem itemId is: " + itemId);
		CartItem cartitem = null;
		for (CartItem checkitem : cartItems) {
			if (checkitem.getCartItemId().equals(itemId)) {
				cartitem = checkitem;
			}
		}
		return cartitem;
	}

	public void setSingleCartItem(CartItem cartitem) {
		int index;
		if (cartItems.contains(cartitem)) {
			index = cartItems.indexOf(cartitem);
			cartItems.get(index).incrementCartItemQty();
		} else {
			cartItems.add(cartitem);
		}
	}

	public int getCartItemQty(String itemId) {
		int itemqty = 0;
		for (CartItem checkitem : cartItems) {
			if (checkitem.getCartItemId().equals(itemId)) {
				itemqty = checkitem.getCartItemQty();
			}
		}
		return itemqty;
	}

	public void setCartItemQty(String itemId, int qty) {
		for (CartItem checkitem : cartItems) {
			if (checkitem.getCartItemId().equals(itemId)) {
				if (qty > 0){
					checkitem.setCartItemQty(qty);
				} else {
					cartItems.remove(checkitem);
				}
				return;
			}
		}
	}

	public double getCartSubtotal() {
		double cartSubTotal = 0.0;
		for (CartItem item : cartItems) {
			cartSubTotal += item.getCartItemTotalPrice();			
		}		
		return cartSubTotal;
	}

	public int getCartQty() {
		int cartQty = 0;
		for (CartItem item : cartItems) {
			cartQty += item.getCartItemQty();			
		}		
		return cartQty;
	}

	public double getCartTax() {
		double cartTax = 0.0;
		double cartSub = this.getCartSubtotal();
		cartTax = cartSub * STATE_TAX_PERCENT / 100;
		return cartTax;
	}

	public double getCartShippingCost() {
		double shippingCost = 0.0;
		logger.info("in get CartShippingCost cart qty is: " + this.getCartQty());
		if (this.getCartQty() > 0) {
			if (this.getCartQty() >= SHIPPING_QTY_THRESHOLD) {
				shippingCost = SHIPPING_COST_OVER_THRESHOLD;
			} else {
				shippingCost = SHIPPING_COST_UNDER_THRESHOLD;
			}
		}
		return shippingCost;
	}
	
	
	public void setCartTax(int taxrate) {
		// TODO Auto-generated method stub
		
	}

	public void setCartShippingCost() {
		// TODO Auto-generated method stub
		
	}

	public double getCartTotal() {
		double cartTotal = this.getCartSubtotal() + this.getCartTax() + this.getCartShippingCost();
		return cartTotal;
	}



}
