package edu.uwpce.bzbookstore.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BZAddressesServiceImpl implements BZAddressesService {
	
	
	@Autowired
	BZUsersManager usersManager;
	
	
	
	public BZUsersManager getUsersManager() {
		return usersManager;
	}

	public void setUsersManager(BZUsersManager usersManager) {
		this.usersManager = usersManager;
	}


	@Override
	public List<BZAddress> getAddresses(int userid) {
		return usersManager.getSingleUserById(userid).getAddresses();
	}
	
	@Override
	public void setAddresses(int userid, List<BZAddress> addresses) {
		usersManager.getSingleUserById(userid).setAddresses(addresses);
	}


	@Override
	public BZAddress getAddress(int userid, String type) {
		return usersManager.getSingleUserById(userid).getAddress(type);
	}
	
	@Override
	public void addAddress(int userid, BZAddress address) {
		usersManager.getSingleUserById(userid).setAddress(address);
	}


	@Override
	public void updateAddress(int userid, BZAddress address) {
		BZAddress addr = usersManager.getSingleUserById(userid).getAddress(address.getAddressType());
		if (addr != null) {
			usersManager.getSingleUserById(userid).getAddresses().remove(address);
		}
			usersManager.getSingleUserById(userid).setAddress(address);
	}

	@Override
	public boolean deleteAddresses(int userid) {
		boolean deleteSuccess = false;
		usersManager.getSingleUserById(userid).setAddresses(null);
		if (usersManager.getSingleUserById(userid).getAddresses() == null) {
			deleteSuccess = true;
		}
		return deleteSuccess;
	}


	@Override
	public boolean deleteAddress(int userid, String type) {
		boolean deleteSuccess = false;
		List<BZAddress> checkaddresses = usersManager.getSingleUserById(userid).getAddresses();
		for (BZAddress checkaddr : checkaddresses) {
			if (checkaddr.getAddressType().equals(type)) {
				checkaddresses.remove(checkaddr);
				deleteSuccess = true;
			}
		}
		usersManager.getSingleUserById(userid).setAddresses(checkaddresses);
		return deleteSuccess;
	}
	
	

}
