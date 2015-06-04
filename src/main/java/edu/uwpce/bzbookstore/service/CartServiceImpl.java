package edu.uwpce.bzbookstore.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.uwpce.bzbookstore.model.Cart;
import edu.uwpce.bzbookstore.model.Cart;
import edu.uwpce.bzbookstore.model.CartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("cartService")
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    
    private static final int STATE_TAX_PERCENT = 8;
	private static final double SHIPPING_COST_UNDER_THRESHOLD = 5.00;
	private static final double SHIPPING_COST_OVER_THRESHOLD = 15.00;
	private static final int SHIPPING_QTY_THRESHOLD = 5;

	private Map<Integer, Cart> carts;
	
	public CartServiceImpl() {
		carts = new ConcurrentHashMap<Integer, Cart>();
	}

	@Override
	public Map<Integer, Cart> getAllCarts() {
		return this.carts;
	}

	@Override
	public void setAllCarts(Map<Integer, Cart> carts) {
		this.carts = carts;
	}

	@Override
	public void setCart(Cart cart) {
		if (cart != null) {
			carts.put(cart.getCartId(), cart);
			logger.info("Successfully added new cart to Cart Manager");
		} else {
			logger.error("The cart sent to the Cart Manager is null!");
		}
	}

	@Override
	public Cart getCart(int customerid) {
		Cart cart = carts.get(customerid);
		if (cart == null) {
			cart = new Cart(customerid);
			carts.put(customerid, cart);
		}
		return cart;
	}
	

	@Override
	public Cart updateCart(Cart cart) {
		Cart checkcart = carts.get(cart.getCartId());
		if (checkcart != null) {
			carts.remove(cart.getCartId());
		}
		carts.put(cart.getCartId(), cart);
		return carts.get(cart.getCartId());
	}

	@Override
	public boolean deleteCart(int cartid) {
		boolean deleteSuccess = false;
		Cart checkcart = carts.get(cartid);
		if (checkcart != null) {
			deleteSuccess = true;
		}
		return deleteSuccess;
	}

	@Override
	public void addCartItem(int cartId, CartItem cartItem) {
		Cart checkcart = carts.get(cartId);
		if (checkcart != null) {
			carts.get(cartId).setSingleCartItem(cartItem);
		} else {
			Cart newcart = new Cart(cartId);
			newcart.setSingleCartItem(cartItem);
		}		
	}

	@Override
	public CartItem getCartItem(int cartId, String cartItemId) {
		return carts.get(cartId).getSingleCartItem(cartItemId);
	}

	@Override
	public void updateCartItem(int cartId, CartItem cartItem) {
		Cart checkcart = carts.get(cartId);
		if (checkcart != null) {
			carts.get(cartId).setSingleCartItem(cartItem);
		} else {
			Cart newcart = new Cart(cartId);
			newcart.setSingleCartItem(cartItem);
		}		
	}

	@Override
	public boolean deleteCartItem(int cartId, String cartItemId) {
		boolean deleteSuccess = false;
		Cart checkcart = carts.get(cartId);
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
		Cart cart = carts.get(cartId);
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
		Cart cart = carts.get(cartId);
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
