package edu.uwpce.bzbookstore.web;

import java.util.List;

public class BZCheckoutInfo {
	
	private BZCart cart;
	private BZUserInfo userInfo;
	
	public BZCheckoutInfo(){
		this.cart = new BZCartImpl();
		this.userInfo = new BZUserInfo();
	}
	
	public BZCheckoutInfo(BZCart bzcart, BZUserInfo bzuserinfo) {
		this.userInfo = bzuserinfo;
		this.cart = bzcart;
	}

	
	public BZCart getCart() {
		return cart;
	}

	public void setCart(BZCartImpl cart) {
		this.cart = cart;
	}

	public BZUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(BZUserInfo userinfo) {
		this.userInfo = userinfo;
	}

	public int getCartId() {
		return cart.getCartId();
	}

	public double getCartTotalPrice() {
		return cart.getCartTotal();
	}

	public List<BZAddress> getUserAddresses() {
		return userInfo.getAddresses();
	}
	
	public void setUserAddresses(List<BZAddress> addresses)	{
		this.userInfo.setAddresses(addresses);
	}

	public List<BZCreditCard> getUserCreditCards() {
		return userInfo.getCards();
	}
	
	public void setUserCreditCards(List<BZCreditCard> cards) { 
		this.userInfo.setCards(cards);
	}


}
