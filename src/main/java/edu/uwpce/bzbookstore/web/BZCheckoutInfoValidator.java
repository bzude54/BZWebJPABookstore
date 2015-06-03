package edu.uwpce.bzbookstore.web;

import java.util.List;

public interface BZCheckoutInfoValidator {
	
	boolean validate(BZCheckoutInfo checkoutinfo);
	
	boolean validShippingAddress(List<BZAddress> addresses);
	
	boolean validCreditCard(List<BZCreditCard> cards);
	
	boolean validAge(BZUserInfo userinfo);
	
	boolean validGiftcard(BZCheckoutInfo checkoutinfo);
	
	
	

}
