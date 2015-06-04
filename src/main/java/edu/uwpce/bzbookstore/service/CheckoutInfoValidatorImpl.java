package edu.uwpce.bzbookstore.service;

import java.util.List;
import java.util.Set;

import edu.uwpce.bzbookstore.model.Address;
import edu.uwpce.bzbookstore.model.CheckoutInfo;
import edu.uwpce.bzbookstore.model.CreditCard;
import edu.uwpce.bzbookstore.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("checkoutInfoValidator")
public class CheckoutInfoValidatorImpl implements CheckoutInfoValidator {
	
    private static final Logger logger = LoggerFactory.getLogger(CheckoutInfoValidatorImpl.class);


	@Override
	public boolean validate(CheckoutInfo checkoutinfo) {
		logger.debug("in checkoutinfovalidator validatemethod.");
		boolean validCheckoutInfo = false;
		validCheckoutInfo = validShippingAddress(checkoutinfo.getCustomerAddresses());
		validCheckoutInfo = validCreditCard(checkoutinfo.getCustomerCreditCards());
		return validCheckoutInfo;
	}

	@Override
	public boolean validShippingAddress(List<Address> addresses) {
		logger.debug("in checkoutinfovalidator validate shipping address method.");
		boolean validShipAddr = false;
		String street;
		String city;
		String state;
		String zip;
		for (Address checkaddr : addresses) {
			if (checkaddr.getAddrtype().equals("shipping")) {
				street = checkaddr.getStreet();
				city = checkaddr.getCity();
				state = checkaddr.getState();
				zip = checkaddr.getZip();
				if (!street.isEmpty() && !city.isEmpty() && !state.isEmpty() && !zip.isEmpty()) {
					validShipAddr = true;			
				}
			}
		}
		logger.debug("in checkoutinfovalidator validate shipping address method. result is: " + validShipAddr);
		return validShipAddr;
	}

	@Override
	public boolean validCreditCard(Set<CreditCard> cardset) {
		logger.debug("in checkoutinfovalidator validate credit card method.");
		boolean validCreditCard = false;
		for (CreditCard checkcard : cardset)
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
	public boolean validAge(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validGiftcard(CheckoutInfo checkoutinfo) {
		// TODO Auto-generated method stub
		return false;
	}


}
