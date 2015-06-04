package edu.uwpce.bzbookstore.service;

import java.util.List;

import edu.uwpce.bzbookstore.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("addressService")
public class AddressServiceImpl implements AddressService {
	
	
	@Autowired
	CustomerService customerservice;
	
	
	
	public CustomerService getCustomerservice() {
		return customerservice;
	}

	public void setCustomerservice(CustomerService customerservice) {
		this.customerservice = customerservice;
	}


	@Override
	public List<Address> getAddresses(int customerid) {
		return customerservice.getCustomerById(customerid).getAddresses();
	}
	
	@Override
	public void setAddresses(int customerid, List<Address> addresses) {
		customerservice.getCustomerById(customerid).setAddresses(addresses);
	}


	@Override
	public Address getAddress(int customerid, String type) {
		return customerservice.getCustomerById(customerid).getAddresses().get(0);
	}
	
	@Override
	public void addAddress(int customerid, Address address) {
		customerservice.getCustomerById(customerid).getAddresses().add(address);
	}


	@Override
	public void updateAddress(int customerid, Address address) {
		List<Address> addrlist = customerservice.getCustomerById(customerid).getAddresses();
		for (Address addr : addrlist) {
			if (addr.getAddrtype().equals(address.getAddrtype())) {
				customerservice.getCustomerById(customerid).getAddresses().remove(addr);
			}
		}
			customerservice.getCustomerById(customerid).getAddresses().add(address);
	}

	@Override
	public boolean deleteAddresses(int customerid) {
		boolean deleteSuccess = false;
		customerservice.getCustomerById(customerid).setAddresses(null);
		if (customerservice.getCustomerById(customerid).getAddresses() == null) {
			deleteSuccess = true;
		}
		return deleteSuccess;
	}


	@Override
	public boolean deleteAddress(int customerid, String type) {
		boolean deleteSuccess = false;
		List<Address> checkaddresses = customerservice.getCustomerById(customerid).getAddresses();
		for (Address checkaddr : checkaddresses) {
			if (checkaddr.getAddrtype().equals(type)) {
				checkaddresses.remove(checkaddr);
				deleteSuccess = true;
			}
		}
		customerservice.getCustomerById(customerid).setAddresses(checkaddresses);
		return deleteSuccess;
	}
	
	

}
