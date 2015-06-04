package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import edu.uwpce.bzbookstore.model.Address;
import edu.uwpce.bzbookstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uwpce.bzbookstore.web.ApiMessage.MsgType;

@RestController
@RequestMapping("/api/customer/{customerid}")
public class ApiCustomerAddressController {
   
    
    @Autowired
    private AddressService addressService;
    
    
    @RequestMapping(value="/addresses", method=RequestMethod.GET)
    public Object getAddresses(@PathVariable("customerid") int customerid){
    	List<Address> addresslist = addressService.getAddresses(customerid);
    	if (addresslist == null) {
            return new ApiMessage(MsgType.ERROR, "The user with id = " + customerid + " has no addresses on file.");
    	} else {
    		return addresslist;
    	}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.GET)
    public Object getAddress(HttpServletResponse response, @PathVariable("customerid") int customerid, @PathVariable("addressid") String addressid) {
    	Address address = addressService.getAddress(customerid, addressid);
    	if (address == null) {
            return new ApiMessage(MsgType.ERROR, "The address with id = " + addressid + " does not exist.");
    	} else {
    		return address;
    	}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.POST)
    public Object createAddress(@RequestBody Address newaddress, @PathVariable("customerid") int customerid, @PathVariable("addressid") String addressid, HttpServletResponse response) {
		if (addressService.getAddress(customerid, addressid) != null) {
            return new ApiMessage(MsgType.ERROR, "Address of type: " + addressid + " already exists.");
		} else {
			addressService.addAddress(customerid, newaddress);
	        response.setStatus(HttpServletResponse.SC_CREATED);
			return addressService.getAddresses(customerid);
		}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.PUT)
    public Object updateAddress(@RequestBody Address newaddress, @PathVariable("customerid") int customerid, @PathVariable("addressid") String addressid){
    	addressService.updateAddress(customerid, newaddress);
    	Address checkaddress = addressService.getAddress(customerid, addressid);
    	if (checkaddress == null) {
            return new ApiMessage(MsgType.ERROR, "The address with id = " + addressid + " was not successfully updated.");
    	} else {
    		return addressService.getAddresses(customerid);
    	}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.DELETE)
    public Object deleteAddress(@PathVariable("customerid") int customerid, @PathVariable("addressid") String addressid, HttpServletResponse response) {
    	if (addressService.deleteAddress(customerid, addressid)){
    		return new ApiMessage(MsgType.INFO, "Address of type: " + addressid + " has been deleted.");
    	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(MsgType.ERROR, "Address of type: " + addressid + " was not found.");
    	}
    }    
    
  
}
