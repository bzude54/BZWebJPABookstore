package edu.uwpce.bzbookstore.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BZCartImpl implements BZCart {
	
    private static final Logger logger = LoggerFactory.getLogger(BZCartImpl.class);
    
    private static final int STATE_TAX_PERCENT = 8;
	private static final double SHIPPING_COST_UNDER_THRESHOLD = 5.00;
	private static final double SHIPPING_COST_OVER_THRESHOLD = 15.00;
	private static final int SHIPPING_QTY_THRESHOLD = 5;
    
    List<BZCartItem> cartItems;
	int cartId;

	public BZCartImpl(){
		cartItems = new ArrayList<BZCartItem>();
	}
	
	public BZCartImpl(int userId) {
		cartItems = new ArrayList<BZCartItem>();
		this.cartId = userId;
	}
	
	@Override
	public List<BZCartItem> getCartItems() {
		return cartItems;
	}

	@Override
	public void setCartItems(List<BZCartItem> cartitems) {
		this.cartItems = cartitems;
	}

	@Override
	public int getCartId() {
		return cartId;
	}

	@Override
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	@Override
	public BZCartItem getSingleCartItem(String itemId) {
		logger.info("in getsinglecartitem itemId is: " + itemId);
		BZCartItem cartitem = null;
		for (BZCartItem checkitem : cartItems) {
			if (checkitem.getCartItemId().equals(itemId)) {
				cartitem = checkitem;
			}
		}
		return cartitem;
	}

	@Override
	public void setSingleCartItem(BZCartItem cartitem) {
		int index;
		if (cartItems.contains(cartitem)) {
			index = cartItems.indexOf(cartitem);
			cartItems.get(index).incrementCartItemQty();
		} else {
			cartItems.add(cartitem);
		}
	}

	@Override
	public int getCartItemQty(String itemId) {
		int itemqty = 0;
		for (BZCartItem checkitem : cartItems) {
			if (checkitem.getCartItemId().equals(itemId)) {
				itemqty = checkitem.getCartItemQty();
			}
		}
		return itemqty;
	}

	@Override
	public void setCartItemQty(String itemId, int qty) {
		for (BZCartItem checkitem : cartItems) {
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

	@Override
	public double getCartSubtotal() {
		double cartSubTotal = 0.0;
		for (BZCartItem item : cartItems) {
			cartSubTotal += item.getCartItemTotalPrice();			
		}		
		return cartSubTotal;
	}

	@Override
	public int getCartQty() {
		int cartQty = 0;
		for (BZCartItem item : cartItems) {
			cartQty += item.getCartItemQty();			
		}		
		return cartQty;
	}

	@Override
	public double getCartTax() {
		double cartTax = 0.0;
		double cartSub = this.getCartSubtotal();
		cartTax = cartSub * STATE_TAX_PERCENT / 100;
		return cartTax;
	}

	@Override
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
	
	
	@Override
	public void setCartTax(int taxrate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCartShippingCost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getCartTotal() {
		double cartTotal = this.getCartSubtotal() + this.getCartTax() + this.getCartShippingCost();
		return cartTotal;
	}



}
