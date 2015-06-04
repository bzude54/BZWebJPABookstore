package edu.uwpce.bzbookstore.service;

import edu.uwpce.bzbookstore.model.Address;

import java.util.List;

public interface AddressService {
	
	List<Address> getAddresses(int customerid);
	
	void setAddresses(int customerid, List<Address> addresses);
	
	Address getAddress(int customerid, String type);
	
	void addAddress(int customerid, Address address);

	void updateAddress(int customerid, Address address);
	
	boolean deleteAddresses(int customerid);
	
	boolean deleteAddress(int customerid, String type);

}
