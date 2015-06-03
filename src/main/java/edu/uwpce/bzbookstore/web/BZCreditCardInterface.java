package edu.uwpce.bzbookstore.web;

import java.io.Serializable;
import java.util.Date;

public interface BZCreditCardInterface extends Serializable {
	
	String getCardNumber();
	
	void setCardNumber(String cardnumber);
	
	String getCardExpirationDate();
	
	void setCardExpirationDate(String expiration);
	
	String getCardType();
	
	void setCardType(String cardtype);
	
	String getCardOwnerName();
	
	void setCardOwnerName(String name);
	
	String getCardCode();
	
	void setCardCode(String code);

	int getCardOwnerId();

	void setCardOwnerId(int cardOwnerId);	
	
	String getCardVendor();
	
	void setCardVendor(String vendor);

}
