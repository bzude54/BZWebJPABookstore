package edu.uwpce.bzbookstore.web;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class BZAddress implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2087906995512809531L;

	@NotEmpty
	private String streetAddress;
	
	@NotEmpty
	private String city;
	
	@Size(min=2, max=2, message="state must be 2 letter abbreviation.")
	@Pattern(regexp = "\\w{2}")
	private String state;
	
    @Pattern(regexp = "\\d{5}",
            message="must be a valid zipcode")
	private String zipcode;
    
	private String addressType;
	
	
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
