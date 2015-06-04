package edu.uwpce.bzbookstore.service;

import java.util.List;
import java.util.Set;

import edu.uwpce.bzbookstore.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("creditCardService")
public class CreditCardServiceImpl implements CreditCardService {
	
	@Autowired
	CustomerService customerService;
	
	
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Override
	public Set<CreditCard> getCards(int customerid) {
		return customerService.getCustomerById(customerid).getCreditCards();
	}
	
	@Override
	public void setCards(int customerid, Set<CreditCard> cards) {
		customerService.getCustomerById(customerid).setCreditCards(cards);
	}
	
	@Override
	public CreditCard getCard(int customerid, String cardType) {
		Set<CreditCard> cardset = customerService.getCustomerById(customerid).getCreditCards();
		for (CreditCard card : cardset) {
			if (card.getCardType().equals(cardType)) {
				return card;
			}
		}

		return null;
	}
	
	@Override
	public void addCard(int customerid, CreditCard card) {
		customerService.getCustomerById(customerid).getCreditCards().add(card);
	}

	@Override
	public void updateCard(int customerid, CreditCard card) {
		Set<CreditCard> cardset = customerService.getCustomerById(customerid).getCreditCards();
		for (CreditCard checkcard : cardset) {
			if (checkcard.getCardType().equals(getCard(customerid, card.getCardType()))) {
				cardset.remove(checkcard);
				cardset.add(card);
				customerService.getCustomerById(customerid).setCreditCards(cardset);
			}
		}
	}
	
	@Override
	public boolean deleteCard(int customerid, String cardType) {
		boolean deleteSuccess = false;
		Set<CreditCard> checkcards = customerService.getCustomerById(customerid).getCreditCards();
		for (CreditCard checkcard : checkcards) {
			if (checkcard.getCardType().equals(cardType)) {
				checkcards.remove(checkcard);
				deleteSuccess = true;
			}
		}
		customerService.getCustomerById(customerid).setCreditCards(checkcards);
		return deleteSuccess;		
	}

}
