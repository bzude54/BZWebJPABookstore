package edu.uwpce.bzbookstore.web;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class BZCreditCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8245531127225057547L;
	
	@Size(min=16, max=16)
	private String cardNumber;
	
	@Future(message="This card has expired. The card expiration must be a date in the future.")
	private Date cardExpirationDate;
	
	private String cardType;
	
	@Pattern(regexp = "\\d{3}", message="The card code are the 3 digits on the back of your card.")
	private String cardCode;
	
	@NotEmpty(message="Enter the name as it appears in the card.")
	private String cardOwnerName;
	
	private String cardVendor;
	
	private int cardOwnerId;
	
	
	public BZCreditCard(){};
	
	public BZCreditCard(int cardOwnerId, String cardNumber, String expirationStr, String cardType, String cardCode, String cardVendor, String cardOwnerName) {
		this.cardCode = cardCode;
		this.cardNumber = cardNumber;
		this.cardOwnerId = cardOwnerId;
		this.cardOwnerName = cardOwnerName;
		this.cardType = cardType;
		this.cardVendor = cardVendor;
		this.cardExpirationDate = parseDateString(expirationStr);
		
	}
		

	private Date parseDateString(String expirationStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
		String dateInString = expirationStr;
		Date date = null;
	 
		try {
	 
			date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;		
	}
	

	public int getCardOwnerId() {
		return cardOwnerId;
	}

	public void setCardOwnerId(int cardOwnerId) {
		this.cardOwnerId = cardOwnerId;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getCardExpirationDate() {
		return this.cardExpirationDate;
	}

	public void setCardExpirationDate(String expirationStr) {
		this.cardExpirationDate = this.parseDateString(expirationStr);
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardOwnerName() {
		return this.cardOwnerName;
	}

	public void setCardOwnerName(String name) {
		this.cardOwnerName = name;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String code) {
		this.cardCode = code;
	}


	public String getCardVendor() {
		return this.cardVendor;
	}

	public void setCardVendor(String vendor) {
		this.cardVendor = vendor;
	}

}
