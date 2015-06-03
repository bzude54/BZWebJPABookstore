package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uwpce.bzbookstore.web.BZApiMessage.MsgType;

@RestController
@RequestMapping("/api/users/{userid}")
public class BZApiUserAddressesController {
   
    
    @Autowired
    private BZAddressesService addressesService;
     	

 /*   public BZApiUserAddressesController() {
    	usersManager = new BZUsersManagerImpl();
    }
 */    
    
    
    @RequestMapping(value="/addresses", method=RequestMethod.GET)
    public Object getAddresses(@PathVariable("userid") int userid){
    	List<BZAddress> addresslist = addressesService.getAddresses(userid);
    	if (addresslist == null) {
            return new BZApiMessage(MsgType.ERROR, "The user with id = " + userid + " has no addresses on file.");
    	} else {
    		return addresslist;
    	}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.GET)
    public Object getAddress(HttpServletResponse response, @PathVariable("userid") int userid, @PathVariable("addressid") String addressid) {
    	BZAddress address = addressesService.getAddress(userid, addressid);
    	if (address == null) {
            return new BZApiMessage(MsgType.ERROR, "The address with id = " + addressid + " does not exist.");
    	} else {
    		return address;
    	}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.POST)
    public Object createAddress(@RequestBody BZAddress newaddress, @PathVariable("userid") int userid, @PathVariable("addressid") String addressid, HttpServletResponse response) {
		if (addressesService.getAddress(userid, addressid) != null) {
            return new BZApiMessage(MsgType.ERROR, "Address of type: " + addressid + " already exists.");
		} else {
			addressesService.addAddress(userid, newaddress);
	        response.setStatus(HttpServletResponse.SC_CREATED);
			return addressesService.getAddresses(userid);
		}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.PUT)
    public Object updateAddress(@RequestBody BZAddress newaddress, @PathVariable("userid") int userid, @PathVariable("addressid") String addressid){
    	addressesService.updateAddress(userid, newaddress);
    	BZAddress checkaddress = addressesService.getAddress(userid, addressid);
    	if (checkaddress == null) {
            return new BZApiMessage(MsgType.ERROR, "The address with id = " + addressid + " was not successfully updated.");
    	} else {
    		return addressesService.getAddresses(userid);
    	}
    }
    
    
    @RequestMapping(value="/addresses/{addressid}", method=RequestMethod.DELETE)
    public Object deleteAddress(@PathVariable("userid") int userid, @PathVariable("addressid") String addressid, HttpServletResponse response) {
    	if (addressesService.deleteAddress(userid, addressid)){
    		return new BZApiMessage(MsgType.INFO, "Address of type: " + addressid + " has been deleted.");
    	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BZApiMessage(MsgType.ERROR, "Address of type: " + addressid + " was not found.");
    	}
    }    
    
  
}
