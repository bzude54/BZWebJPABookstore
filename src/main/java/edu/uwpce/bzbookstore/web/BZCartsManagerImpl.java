package edu.uwpce.bzbookstore.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BZCartsManagerImpl implements BZCartsManager {

    private static final Logger logger = LoggerFactory.getLogger(BZCartsManagerImpl.class);

    
    private static final int STATE_TAX_PERCENT = 8;
	private static final double SHIPPING_COST_UNDER_THRESHOLD = 5.00;
	private static final double SHIPPING_COST_OVER_THRESHOLD = 15.00;
	private static final int SHIPPING_QTY_THRESHOLD = 5;

	private Map<Integer, BZCart> carts;
	
	public BZCartsManagerImpl() {
		carts = new ConcurrentHashMap<Integer, BZCart>();
	}

	@Override
	public Map<Integer, BZCart> getAllCarts() {
		return this.carts;
	}

	@Override
	public void setAllCarts(Map<Integer, BZCart> carts) {
		this.carts = carts;
	}

	@Override
	public void setCart(BZCart cart) {
		if (cart != null) {
			carts.put(cart.getCartId(), cart);
			logger.info("Successfully added new cart to Cart Manager");
		} else {
			logger.error("The cart sent to the Cart Manager is null!");
		}
	}

	@Override
	public BZCart getCart(int userId) {
		BZCart cart = carts.get(userId);
		if (cart == null) {
			cart = new BZCartImpl(userId);
			carts.put(userId, cart);
		}
		return cart;
	}
	

	@Override
	public BZCart updateCart(BZCart cart) {
		BZCart checkcart = carts.get(cart.getCartId());
		if (checkcart != null) {
			carts.remove(cart.getCartId());
		}
		carts.put(cart.getCartId(), cart);
		return carts.get(cart.getCartId());
	}

	@Override
	public boolean deleteCart(int cartid) {
		boolean deleteSuccess = false;
		BZCart checkcart = carts.get(cartid);
		if (checkcart != null) {
			deleteSuccess = true;
		}
		return deleteSuccess;
	}

	@Override
	public void addCartItem(int cartId, BZCartItem cartItem) {
		BZCart checkcart = carts.get(cartId);
		if (checkcart != null) {
			carts.get(cartId).setSingleCartItem(cartItem);
		} else {
			BZCart newcart = new BZCartImpl(cartId);
			newcart.setSingleCartItem(cartItem);
		}		
	}

	@Override
	public BZCartItem getCartItem(int cartId, String cartItemId) {
		return carts.get(cartId).getSingleCartItem(cartItemId);
	}

	@Override
	public void updateCartItem(int cartId, BZCartItem cartItem) {
		BZCart checkcart = carts.get(cartId);
		if (checkcart != null) {
			carts.get(cartId).setSingleCartItem(cartItem);
		} else {
			BZCart newcart = new BZCartImpl(cartId);
			newcart.setSingleCartItem(cartItem);
		}		
	}

	@Override
	public boolean deleteCartItem(int cartId, String cartItemId) {
		boolean deleteSuccess = false;
		BZCart checkcart = carts.get(cartId);
		if ((checkcart != null) && (checkcart.getSingleCartItem(cartItemId) != null)){
			checkcart.getCartItems().remove(cartItemId);
			deleteSuccess = true;
		}
		return deleteSuccess;
	}



	@Override
	public double getCartTax(int cartId) {
		double cartTax = 0.0;
		double cartSubTotal = 0.0;
		BZCart cart = carts.get(cartId);
		if (cart != null) {
			cartSubTotal = cart.getCartSubtotal();
			cartTax = cartSubTotal * STATE_TAX_PERCENT / 100;			
		} else {
			logger.error("There is no cart with this id!");
		}
		return cartTax;
	}
	
	@Override
	public double getCartShippingCost(int cartId) {
		double shippingCost = 0.0;
		BZCart cart = carts.get(cartId);
			if (cart.getCartQty() >= SHIPPING_QTY_THRESHOLD) {
				shippingCost = SHIPPING_COST_OVER_THRESHOLD;
			} else {
				if (cart.getCartQty() > 0) {
					shippingCost = SHIPPING_COST_UNDER_THRESHOLD;					
				} 
			}
		return shippingCost;
	}



}
