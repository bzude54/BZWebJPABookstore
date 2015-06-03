package edu.uwpce.bzbookstore.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BZCheckoutInfoValidatorImpl implements BZCheckoutInfoValidator {
	
    private static final Logger logger = LoggerFactory.getLogger(BZCheckoutInfoValidatorImpl.class);


	@Override
	public boolean validate(BZCheckoutInfo checkoutinfo) {
		logger.debug("in checkoutinfovalidator validatemethod.");
		boolean validCheckoutInfo = false;
		validCheckoutInfo = validShippingAddress(checkoutinfo.getUserAddresses());
		validCheckoutInfo = validCreditCard(checkoutinfo.getUserCreditCards());
		return validCheckoutInfo;
	}

	@Override
	public boolean validShippingAddress(List<BZAddress> addresses) {
		logger.debug("in checkoutinfovalidator validate shipping address method.");
		boolean validShipAddr = false;
		String street;
		String city;
		String state;
		String zip;
		for (BZAddress checkaddr : addresses) {
			if (checkaddr.getAddressType().equals("shipping")) {
				street = checkaddr.getStreetAddress();
				city = checkaddr.getCity();
				state = checkaddr.getState();
				zip = checkaddr.getZipcode();
				if (!street.isEmpty() && !city.isEmpty() && !state.isEmpty() && !zip.isEmpty()) {
					validShipAddr = true;			
				}
			}
		}
		logger.debug("in checkoutinfovalidator validate shipping address method. result is: " + validShipAddr);
		return validShipAddr;
	}

	@Override
	public boolean validCreditCard(List<BZCreditCard> cards) {
		logger.debug("in checkoutinfovalidator validate credit card method.");
		boolean validCreditCard = false;
		for (BZCreditCard checkcard : cards)
		{
			if (checkcard.getCardType().equals("primary")) {
				if (!checkcard.getCardNumber().isEmpty()) {
					validCreditCard = true;
				}			
			}
		}
		logger.debug("in checkoutinfovalidator validate card method. result is: " + validCreditCard);
		return validCreditCard;
	}


	@Override
	public boolean validAge(BZUserInfo userinfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validGiftcard(BZCheckoutInfo checkoutinfo) {
		// TODO Auto-generated method stub
		return false;
	}


}
