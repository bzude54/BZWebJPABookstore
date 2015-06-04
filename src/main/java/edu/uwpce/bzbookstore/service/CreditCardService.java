package edu.uwpce.bzbookstore.service;

import edu.uwpce.bzbookstore.model.CreditCard;

import java.util.List;
import java.util.Set;

public interface CreditCardService {
	
	Set<CreditCard> getCards(int customerid);
	
	void setCards(int customerid, Set<CreditCard> cards);
	
	CreditCard getCard(int customerid, String cardnum);
	
	void addCard(int customerid, CreditCard card);

	void updateCard(int customerid, CreditCard card);
	
	boolean deleteCard(int customerid, String cardnum);

}
