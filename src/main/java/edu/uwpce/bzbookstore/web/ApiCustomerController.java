package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import edu.uwpce.bzbookstore.model.Customer;
import edu.uwpce.bzbookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uwpce.bzbookstore.web.ApiMessage.MsgType;

@RestController
@RequestMapping("/api")
public class ApiCustomerController {
   
	@Autowired
    private CustomerService customerService;
          
    @RequestMapping(value="/customer", method=RequestMethod.GET)
    public List<Customer> getCustomers(){
       	return customerService.getCustomers();
    }
    
    
    @RequestMapping(value="/customer/{customerid}", method=RequestMethod.GET)
    public Object getUser(@PathVariable("customerid") int customerid, HttpServletResponse response) {
    	Customer user = customerService.getCustomerById(customerid);
    	if (user == null) {
            return new ApiMessage(MsgType.ERROR, "User with id = " + customerid + " does not exist.");
    	} else {
    		return user;
    	}
    }
    
    @RequestMapping(value="/customer/{username}", method=RequestMethod.POST)
    public Object createUser(@RequestBody Customer newcustomer, @PathVariable("username") String username, HttpServletResponse response) {
		if (customerService.getCustomerByUsername(username) != null) {
            return new ApiMessage(MsgType.ERROR, "User with username= " + username + " already exists.");
		} else {
			int newcustomerid = customerService.addCustomer(newcustomer);
	        response.setStatus(HttpServletResponse.SC_CREATED);
			return customerService.getCustomerById(newcustomerid);
		}   	    	
    }
    
    
    @RequestMapping(value="/customer/{customerid}", method=RequestMethod.PUT)
    public Object updateUser(@RequestBody Customer customer, @PathVariable("customerid") int customerid){
    	customerService.updateCustomer(customer);
       	Customer user = customerService.getCustomerById(customerid);
    	if (user == null) {
            return new ApiMessage(MsgType.ERROR, "User with id = " + customerid + " not successfully updated.");
    	} else {
    		return user;
    	}
    }
    
    
    @RequestMapping(value="/customer/{customerid}", method=RequestMethod.DELETE)
    public Object deleteUser(@PathVariable("customerid") int customerid, HttpServletResponse response) {
    	if (customerService.deleteCustomer(customerid)){
    		return new ApiMessage(MsgType.INFO, "User with id: " + customerid + " has been deleted.");
    	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(MsgType.ERROR, "User with id: " + customerid + " was not found.");
    	}
    }    
    
  
}
