package edu.uwpce.bzbookstore.model;

import java.util.List;
import java.util.Set;

public class CheckoutInfo {
	
	private Cart cart;
	private Customer customer;
	
	public CheckoutInfo(){
		this.cart = new Cart();
		this.customer = new Customer();
	}
	
	public CheckoutInfo(Cart Cart, Customer Customer) {
		this.customer = Customer;
		this.cart = Cart;
	}

	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getCartId() {
		return cart.getCartId();
	}

	public double getCartTotalPrice() {
		return cart.getCartTotal();
	}

	public List<Address> getCustomerAddresses() {
		return customer.getAddresses();
	}
	
	public void setCustomerAddresses(List<Address> addresses)	{
		this.customer.setAddresses(addresses);
	}

	public Set<CreditCard> getCustomerCreditCards() {
		return customer.getCreditCards();
	}
	
/*	public void setCustomerCreditCards(Set<CreditCard> cards) {
		this.setCards(cards);
	}*/


}
