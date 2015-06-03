package edu.uwpce.bzbookstore.web;

public interface BZAddressInterface {

	public String getStreetAddress();
	
	void setStreetAddress(String streetAddress);

	String getCity();
	
	void setCity(String city);

	String getState();
	
	void setState(String state);
	
	String getZipcode();
	
	void setZipcode(String zipcode);
	
	String getAddressType();
	
	void setAddressType(String addressType);

}
