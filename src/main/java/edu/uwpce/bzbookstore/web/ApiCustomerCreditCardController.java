package edu.uwpce.bzbookstore.web;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import edu.uwpce.bzbookstore.model.CreditCard;
import edu.uwpce.bzbookstore.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uwpce.bzbookstore.web.ApiMessage.MsgType;

@RestController
@RequestMapping("/api/customer{customerid}")
public class ApiCustomerCreditCardController {
   
    
    @Autowired
    private CreditCardService creditCardService;

   
    @RequestMapping(value="/cards", method=RequestMethod.GET)
    public Object getCards(@PathVariable("customerid") int customerid){
       	Set<CreditCard> cardlist = creditCardService.getCards(customerid);
    	if (cardlist == null) {
            return new ApiMessage(MsgType.ERROR, "User with id = " + customerid + " has no credit cards on file.");
    	} else {
    		return cardlist;
    	}
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.GET)
    public Object getCard(HttpServletResponse response, @PathVariable("customerid") int customerid,  @PathVariable("cardid") String cardid) {
       	CreditCard card = creditCardService.getCard(customerid, cardid);
    	if (card == null) {
            return new ApiMessage(MsgType.ERROR, "The card with id = " + cardid + " does not exist.");
    	} else {
    		return card;
    	}
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.POST)
    public Object createCard(@RequestBody CreditCard newcard, @PathVariable("customerid") int customerid, @PathVariable("cardid") String cardid, HttpServletResponse response) {
		if (creditCardService.getCard(customerid, cardid) != null) {
            return new ApiMessage(MsgType.ERROR, "Credit card with username= " + cardid + " already exists.");
		} else {
			creditCardService.addCard(customerid, newcard);
	        response.setStatus(HttpServletResponse.SC_CREATED);
			return creditCardService.getCards(customerid);
		}    	    	
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.PUT)
    public Object updateCard(@RequestBody CreditCard card, @PathVariable("customerid") int customerid, @PathVariable("cardid") String cardid){
		creditCardService.updateCard(customerid, card);
       	CreditCard checkcard = creditCardService.getCard(customerid, cardid);
    	if (checkcard == null) {
            return new ApiMessage(MsgType.ERROR, "The card with id = " + cardid + " was not successfully updated.");
    	} else {
    		return creditCardService.getCards(customerid);
    	}
    }
    
    
    @RequestMapping(value="/cards/{cardid}", method=RequestMethod.DELETE)
    public Object deleteCard(@PathVariable("customerid") int customerid, @PathVariable("cardid") String cardid, HttpServletResponse response) {
    	if (creditCardService.deleteCard(customerid, cardid)){
    		return new ApiMessage(MsgType.INFO, "Card number: " + cardid + " has been deleted.");
    	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(MsgType.ERROR, "Card number: " + cardid + " was not found.");
    	}
    }    
}
