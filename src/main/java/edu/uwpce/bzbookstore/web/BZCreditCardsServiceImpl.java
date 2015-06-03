package edu.uwpce.bzbookstore.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BZCreditCardsServiceImpl implements BZCreditCardsService {
	
	@Autowired
	BZUsersManager usersManager;
	
	
	
	public BZUsersManager getUsersManager() {
		return usersManager;
	}

	public void setUsersManager(BZUsersManager usersManager) {
		this.usersManager = usersManager;
	}
	
	@Override
	public List<BZCreditCard> getCards(int userid) {
		return usersManager.getSingleUserById(userid).getCards();		
	}
	
	@Override
	public void setCards(int userid, List<BZCreditCard> cards) {
		usersManager.getSingleUserById(userid).setCards(cards);
	}
	
	@Override
	public BZCreditCard getCard(int userid, String cardType) {
		return usersManager.getSingleUserById(userid).getCard(cardType);
	}
	
	@Override
	public void addCard(int userid, BZCreditCard card) {
		usersManager.getSingleUserById(userid).setCard(card);
	}

	@Override
	public void updateCard(int userid, BZCreditCard card) {
		BZCreditCard checkcard = usersManager.getSingleUserById(userid).getCard(card.getCardType());
		if (checkcard != null) {
			usersManager.getSingleUserById(userid).getCards().remove(checkcard);			
		}
		usersManager.getSingleUserById(userid).getCards().add(card);
	}
	
	@Override
	public boolean deleteCard(int userid, String cardType) {
		boolean deleteSuccess = false;
		List<BZCreditCard> checkcards = usersManager.getSingleUserById(userid).getCards();
		for (BZCreditCard checkcard : checkcards) {
			if (checkcard.getCardType().equals(cardType)) {
				checkcards.remove(checkcard);
				deleteSuccess = true;
			}
		}
		usersManager.getSingleUserById(userid).setCards(checkcards);
		return deleteSuccess;		
	}

}
