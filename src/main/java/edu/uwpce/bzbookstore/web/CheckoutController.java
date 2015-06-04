package edu.uwpce.bzbookstore.web;

import javax.servlet.http.HttpSession;

import edu.uwpce.bzbookstore.model.Cart;
import edu.uwpce.bzbookstore.model.CheckoutInfo;
import edu.uwpce.bzbookstore.model.Customer;
import edu.uwpce.bzbookstore.service.CartService;
import edu.uwpce.bzbookstore.service.CheckoutInfoValidator;
import edu.uwpce.bzbookstore.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckoutController {
	
	   private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
		
		
		@Autowired
		private CustomerService customerService;
		
		@Autowired
		private CartService cartService;
		
		@Autowired
		private CheckoutInfoValidator checkoutInfoValidator;
		
		
		@RequestMapping(value = "/bzcheckout/{cartid}", method = RequestMethod.GET)
		public ModelAndView showCheckout(HttpSession session, @PathVariable("cartid") int cartid, Model model ){
			
//			logger.info("in showcheckout, cartid is: " + cartid);
			String username = (String) session.getAttribute("username");
//			logger.info("in showcheckout, username is: " + username);
			int customerid = (Integer) session.getAttribute("customerid");
			Cart cart = (Cart) session.getAttribute("bzcart");
//			logger.info("in showcheckout, bzcart id is: " + bzcart.getCartId() + " and qty is: " + bzcart.getCartQty());
			Customer customer = customerService.getCustomerById(customerid);
//			logger.info("in showcheckout, bzuserinfo has username: " + bzuserinfo.getUserName());
			CheckoutInfo bzcheckoutinfo = new CheckoutInfo(cart, customer);
//			logger.info("bzcheckoutinfo has username: " + bzcheckoutinfo.getUserInfo().getUserName());
			session.setAttribute("checkout", true);
			session.setAttribute("numCartItems", cart.getCartQty());
			session.setAttribute("bzcheckoutinfo", bzcheckoutinfo);

			return new ModelAndView("bzcheckout", "bZCheckoutInfo", bzcheckoutinfo);
		}

		@RequestMapping(value = "/bzcheckout/{cartid}", method = RequestMethod.POST)
		public String processCheckout(HttpSession session, @ModelAttribute CheckoutInfo bZCheckoutInfo, @PathVariable("cartid") int cartid, Model model) {

			String username = (String) session.getAttribute("username");
//			logger.info("bzcheckoutinfo has username: " + bzcheckoutinfo.getUserInfo().getUserName());
			int userid = (Integer) session.getAttribute("customerid");
			CheckoutInfo checkoutinfo = (CheckoutInfo) session.getAttribute("bzcheckoutinfo");
//			if (checkoutinfo != null) {
//				checkoutinfo.getUserAddresses().get(0).(bZCheckoutInfo.getUserAddresses().get(0).getStreetAddress());
//				checkoutinfo.setUserCity(bZCheckoutInfo.getUserCity());
//				checkoutinfo.setUserState(bZCheckoutInfo.getUserState());
//				checkoutinfo.setUserZip(bZCheckoutInfo.getUserZip());
//				checkoutinfo.setUserCreditCard(bZCheckoutInfo.getUserCreditCard());
//			}
//			logger.info("shipping address is: " + checkoutManager.getCheckoutinfo().getUserStreetAddress());
//			logger.info("credit card1 is: " + checkoutManager.getCheckoutinfo().getUserCreditCard());
//			logger.info("validshippingaddress is: " + checkoutManager.validShippingAddress());
//			logger.info("validcreditcard is: " + checkoutManager.validCreditCard());
			
//			session.setAttribute("numCartItems", bzcart.getCartQty());

			if (!(checkoutInfoValidator.validate(checkoutinfo))) {
				return "redirect:/bzaccountinfo/" + userid;
			} else {
				model.addAttribute("bZCheckoutInfo", checkoutinfo);
				return "redirect:/bzcheckout/" + userid;
			}			
	
		}
		
		
		@RequestMapping(value = "/bzthankyou", method = RequestMethod.GET)
		public String confirmCheckout(HttpSession session) {
			
			int userid = (Integer) session.getAttribute("customerid");
				return "bzthankyou";
		}

}
