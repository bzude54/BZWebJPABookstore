package edu.uwpce.bzbookstore.web;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
public class BZUserInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(BZUserInfoController.class);

	
	@Autowired
	private BZUsersManager userManager;
	
/*	@Resource(name="userManager")
	public void setUserManager(BZUserManager usermanager) {
		this.userManager = usermanager;
	}
*/
    @RequestMapping(value = "/bzlogin", method = RequestMethod.GET)
    public ModelAndView displayLoginForm() {
    	
    	
        return new ModelAndView("bzlogin", "loginUserInfo", new BZUserInfo()); 
    }

    @RequestMapping(value = "/bzlogin", method = RequestMethod.POST)
    public String processLogin(HttpSession session,
                               @ModelAttribute("BZUserInfo") BZUserInfo loginUserInfo) {
    	
    	String username = loginUserInfo.getUserName();
    	int userid = 0;
    	session.setAttribute("checkout", false);
    	session.setAttribute("numCartItems", 0);

 //   	logger.info("in bzlogin");
  
    	Map<Integer, BZUserInfo> users = userManager.getUsers();
    	
    	if (users != null) {
    	   	for (BZUserInfo user: users.values()) {
        		if (user.getUserName().equals(username)) {
        			userid = user.getUserId();
        		}
           	}    		
    	}
    	
    	if (userid != 0) {
            session.setAttribute("username", users.get(userid).getUserName());
            session.setAttribute("userid", users.get(userid).getUserId());
//            logger.info("userid after login is: " + users.get(userid).getUserId());
           return "redirect:/bzbooks";
    		
    	} else {

    		return "redirect:/bzregister";
    	}
    }

    @RequestMapping(value = "/bzregister", method = RequestMethod.GET)
    public ModelAndView displayRegisterForm() {

        // This is a shortcut if we only have one attribute to put in our model
        return new ModelAndView("bzregisterForm", "bZUserInfo", new BZUserInfo()); 
    }

    @RequestMapping(value = "/bzregister", method = RequestMethod.POST)
    public String processRegister(HttpSession session,
                               @ModelAttribute @Valid BZUserInfo bZUserInfo, Errors errors) {

        if (errors.hasErrors()) {
            return "bzregisterForm";
        }
      
    	userManager.setSingleUser(bZUserInfo);
        session.setAttribute("username", bZUserInfo.getUserName());
        session.setAttribute("userid", bZUserInfo.getUserId());
       logger.info("new userid after register is: " + bZUserInfo.getUserId());
        return "redirect:/bzlogin";
    }
    
    @RequestMapping(value = "/bzaccountinfo/{userid}", method = RequestMethod.GET)
    public ModelAndView displayAccountInfoForm(HttpSession session, Model model, @PathVariable("userid") int userid) {
    	
    	BZUserInfo accountUserInfo = userManager.getSingleUserById(userid);
//    	 logger.info("in bzacctinfo GET, userid is: " + userid);
//     	 logger.info("in bzacctinfo GET, username is: " + accountUserInfo.getUserName());
     	 if (accountUserInfo != null) {
    	       session.setAttribute("bzuserinfo", accountUserInfo);   	
    	} else {
    		int newuserid =	userManager.setSingleUser(new BZUserInfo());
    		accountUserInfo = userManager.getSingleUserById(newuserid);
    	}
        return new ModelAndView("bzaccountinfo", "bZUserInfo", accountUserInfo);    	
    	
    }
    
    
    @RequestMapping(value = "/bzaccountinfo/{userid}", method = RequestMethod.POST)
    public String processAccountInfoForm(HttpSession session, @ModelAttribute @Valid BZUserInfo accountUserInfo, Errors errors) {
    	
    	
        if (errors.hasErrors()) {
        	return "bzaccountinfo";
        }
      

    	int userid = accountUserInfo.getUserId();
//		logger.info("in bzacctinfo, userid is: " + userid);
    	String username = accountUserInfo.getUserName();
//    	 logger.info("in bzacctinfo, username is: " + username);
    	
    	
		boolean update = false;
		
		
/*		if (user != null) {
			logger.info("in bzacctinfo, username back from usermananger is: " + user.getUserName());
			user.setFirstName(accountUserInfo.getFirstName());
			user.setLastName(accountUserInfo.getLastName());
			user.setUserName(accountUserInfo.getUserName());
			user.setPassword(accountUserInfo.getPassword());
			session.setAttribute("password", user.getPassword());
			user.setEmailAddress(accountUserInfo.getEmailAddress());
			session.setAttribute("emailaddress", user.getEmailAddress());
			user.setMailingStreetAddress(accountUserInfo.getMailingStreetAddress());
			session.setAttribute("mailingstreet",
					accountUserInfo.getMailingStreetAddress());
			user.setMailingCity(accountUserInfo.getMailingCity());
			session.setAttribute("mailingcity", user.getMailingCity());
			user.setMailingState(accountUserInfo.getMailingState());
			session.setAttribute("mailingstate", user.getMailingState());
			user.setMailingZip(accountUserInfo.getMailingZip());
			session.setAttribute("mailingzip", user.getMailingZip());    	
			user.setShippingStreetAddress(accountUserInfo.getShippingStreetAddress());
			session.setAttribute("shippingstreet",
					accountUserInfo.getShippingStreetAddress());
			user.setShippingCity(accountUserInfo.getShippingCity());
			session.setAttribute("shippingcity", user.getShippingCity());
			user.setShippingState(accountUserInfo.getShippingState());
			session.setAttribute("shippingstate", user.getShippingState());
			user.setShippingZip(accountUserInfo.getShippingZip());
			session.setAttribute("shippingzip", user.getShippingZip());    	
			user.setPhoneNumber1(accountUserInfo.getPhoneNumber1());
			session.setAttribute("phonenumber1", user.getPhoneNumber1());
			user.setPhoneNumber2(accountUserInfo.getPhoneNumber2());
			session.setAttribute("phonenumber2", user.getPhoneNumber2());
			user.setCreditCard1(accountUserInfo.getCreditCard1());
			session.setAttribute("creditcard1", user.getCreditCard1());
			user.setCreditCard2(accountUserInfo.getCreditCard2());
			session.setAttribute("creditcard2", user.getCreditCard2());
*/
			BZUserInfo user = userManager.getSingleUserById(userid);
			userManager.updateUser(accountUserInfo);
			return "redirect:/bzaccountinfo/" + userid;
		
		
    }
     
    

    @RequestMapping(value = "/bzlogout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        session.removeAttribute("username");
        return "redirect:";
    }
}
