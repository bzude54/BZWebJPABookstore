package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import edu.uwpce.bzbookstore.model.Customer;
import edu.uwpce.bzbookstore.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	
	@Autowired
	private CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView displayLoginForm() {
    	
    	
        return new ModelAndView("login", "loginCustomer", new Customer());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(HttpSession session,
                               @ModelAttribute("Customer") Customer loginCustomerInfo) {
    	
    	String username = loginCustomerInfo.getUserName();
    	int customerid = 0;
    	session.setAttribute("checkout", false);
    	session.setAttribute("numCartItems", 0);

 //   	logger.info("in bzlogin");
  
    	List<Customer> customers = customerService.getCustomers();
    	
    	if (customers != null) {
    	   	for (Customer customer: customers) {
        		if (customer.getUserName().equals(username)) {
        			customerid = customer.getId();
        		}
           	}    		
    	}
    	
    	if (customerid != 0) {
            session.setAttribute("username", customers.get(customerid).getUserName());
            session.setAttribute("customerid", customers.get(customerid).getId());
//            logger.info("userid after login is: " + customers.get(userid).getUserId());
           return "redirect:/books";
    		
    	} else {

    		return "redirect:/register";
    	}
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView displayRegisterForm() {

        // This is a shortcut if we only have one attribute to put in our model
        return new ModelAndView("registerForm", "customer", new Customer());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(HttpSession session,
                               @ModelAttribute @Valid Customer customer, Errors errors) {

        if (errors.hasErrors()) {
            return "registerForm";
        }
      
    	customerService.addCustomer(customer);
        session.setAttribute("username", customer.getUserName());
        session.setAttribute("customerid", customer.getId());
       logger.info("new customerid after register is: " + customer.getId());
        return "redirect:/login";
    }
    
    @RequestMapping(value = "/accountinfo/{customerid}", method = RequestMethod.GET)
    public ModelAndView displayAccountInfoForm(HttpSession session, Model model, @PathVariable("customerid") int customerid) {
    	
    	Customer customer = customerService.getCustomerById(customerid);
//    	 logger.info("in bzacctinfo GET, userid is: " + userid);
//     	 logger.info("in bzacctinfo GET, username is: " + customer.getUserName());
     	 if (customer != null) {
    	       session.setAttribute("customer", customer);
    	} else {
    		int newuserid =	customerService.addCustomer(new Customer());
    		customer = customerService.getCustomerById(newuserid);
    	}
        return new ModelAndView("accountinfo", "customer", customer);
    	
    }
    
    
    @RequestMapping(value = "/accountinfo/{customerid}", method = RequestMethod.POST)
    public String processAccountInfoForm(HttpSession session, @ModelAttribute @Valid Customer customer, Errors errors) {
    	
    	
        if (errors.hasErrors()) {
        	return "accountinfo";
        }
      

    	int customerid = customer.getId();
//		logger.info("in bzacctinfo, userid is: " + userid);
    	String username = customer.getUserName();
//    	 logger.info("in bzacctinfo, username is: " + username);
    	
    	
		boolean update = false;
		
		
/*		if (user != null) {
			logger.info("in bzacctinfo, username back from usermananger is: " + user.getUserName());
			user.setFirstName(customer.getFirstName());
			user.setLastName(customer.getLastName());
			user.setUserName(customer.getUserName());
			user.setPassword(customer.getPassword());
			session.setAttribute("password", user.getPassword());
			user.setEmailAddress(customer.getEmailAddress());
			session.setAttribute("emailaddress", user.getEmailAddress());
			user.setMailingStreetAddress(customer.getMailingStreetAddress());
			session.setAttribute("mailingstreet",
					customer.getMailingStreetAddress());
			user.setMailingCity(customer.getMailingCity());
			session.setAttribute("mailingcity", user.getMailingCity());
			user.setMailingState(customer.getMailingState());
			session.setAttribute("mailingstate", user.getMailingState());
			user.setMailingZip(customer.getMailingZip());
			session.setAttribute("mailingzip", user.getMailingZip());    	
			user.setShippingStreetAddress(customer.getShippingStreetAddress());
			session.setAttribute("shippingstreet",
					customer.getShippingStreetAddress());
			user.setShippingCity(customer.getShippingCity());
			session.setAttribute("shippingcity", user.getShippingCity());
			user.setShippingState(customer.getShippingState());
			session.setAttribute("shippingstate", user.getShippingState());
			user.setShippingZip(customer.getShippingZip());
			session.setAttribute("shippingzip", user.getShippingZip());    	
			user.setPhoneNumber1(customer.getPhoneNumber1());
			session.setAttribute("phonenumber1", user.getPhoneNumber1());
			user.setPhoneNumber2(customer.getPhoneNumber2());
			session.setAttribute("phonenumber2", user.getPhoneNumber2());
			user.setCreditCard1(customer.getCreditCard1());
			session.setAttribute("creditcard1", user.getCreditCard1());
			user.setCreditCard2(customer.getCreditCard2());
			session.setAttribute("creditcard2", user.getCreditCard2());
*/
			Customer user = customerService.getCustomerById(customerid);
			customerService.updateCustomer(customer);
			return "redirect:/accountinfo/" + customerid;
		
		
    }
     
    

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        session.removeAttribute("username");
        return "redirect:";
    }
}
