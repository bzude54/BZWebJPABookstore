package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uwpce.bzbookstore.web.BZApiMessage.MsgType;

@RestController
@RequestMapping("/api/users/{userid}")
public class BZApiUserCreditCardsController {
   
    
    @Autowired
    private BZCreditCardsService cardsService;

/*    public BZApiUserCreditCardsController() {
    	usersManager = new BZUsersManagerImpl();
    }
*/
   
    @RequestMapping(value="/cards", method=RequestMethod.GET)
    public Object getCards(@PathVariable("userid") int userid){
       	List<BZCreditCard> cardlist = cardsService.getCards(userid);
    	if (cardlist == null) {
            return new BZApiMessage(MsgType.ERROR, "User with id = " + userid + " has no credit cards on file.");
    	} else {
    		return cardlist;
    	}
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.GET)
    public Object getCard(HttpServletResponse response, @PathVariable("userid") int userid,  @PathVariable("cardid") String cardid) {
       	BZCreditCard card = cardsService.getCard(userid, cardid);
    	if (card == null) {
            return new BZApiMessage(MsgType.ERROR, "The card with id = " + cardid + " does not exist.");
    	} else {
    		return card;
    	}
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.POST)
    public Object createCard(@RequestBody BZCreditCard newcard, @PathVariable("userid") int userid, @PathVariable("cardid") String cardid, HttpServletResponse response) {
		if (cardsService.getCard(userid, cardid) != null) {
            return new BZApiMessage(MsgType.ERROR, "Credit card with username= " + cardid + " already exists.");
		} else {
			cardsService.addCard(userid, newcard);	
	        response.setStatus(HttpServletResponse.SC_CREATED);
			return cardsService.getCards(userid);
		}    	    	
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.PUT)
    public Object updateCard(@RequestBody BZCreditCard card, @PathVariable("userid") int userid, @PathVariable("cardid") String cardid){
    	cardsService.updateCard(userid, card);
       	BZCreditCard checkcard = cardsService.getCard(userid, cardid);
    	if (checkcard == null) {
            return new BZApiMessage(MsgType.ERROR, "The card with id = " + cardid + " was not successfully updated.");
    	} else {
    		return cardsService.getCards(userid);
    	}
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.DELETE)
    public Object deleteCard(@PathVariable("userid") int userid, @PathVariable("cardid") String cardid, HttpServletResponse response) {
    	if (cardsService.deleteCard(userid, cardid)){
    		return new BZApiMessage(MsgType.INFO, "Card number: " + cardid + " has been deleted.");
    	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BZApiMessage(MsgType.ERROR, "Card number: " + cardid + " was not found.");
    	}
    }    
}
