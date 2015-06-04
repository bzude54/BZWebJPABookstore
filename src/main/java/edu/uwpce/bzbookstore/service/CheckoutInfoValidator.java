package edu.uwpce.bzbookstore.service;

import edu.uwpce.bzbookstore.model.Address;
import edu.uwpce.bzbookstore.model.CheckoutInfo;
import edu.uwpce.bzbookstore.model.CreditCard;
import edu.uwpce.bzbookstore.model.Customer;

import java.util.List;
import java.util.Set;

public interface CheckoutInfoValidator {
	
	boolean validate(CheckoutInfo checkoutinfo);
	
	boolean validShippingAddress(List<Address> addresses);
	
	boolean validCreditCard(Set<CreditCard> cardSet);
	
	boolean validAge(Customer customer);
	
	boolean validGiftcard(CheckoutInfo checkoutinfo);
	
	
	

}
