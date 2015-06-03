package edu.uwpce.bzbookstore.web;

import javax.servlet.http.HttpSession;

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
public class BZCheckoutController {
	
	   private static final Logger logger = LoggerFactory.getLogger(BZCheckoutController.class);
		
		
		@Autowired
		private BZUsersManager userManager;
		
		@Autowired
		private BZCartsManager cartManager;
		
		@Autowired
		private BZCheckoutInfoValidator checkoutValidator;

/*		@Resource(name="userManager")
		public void setUserManager(BZUserManager usermanager) {
			this.userManager = usermanager;
		}

		@Resource(name="cartManager")
		public void setCartManager(BZCartManager cartmanager) {
			this.cartManager = cartmanager;
		}

		@Resource(name="checkoutValidator")
		public void setCheckouotValidator(BZCheckoutInfoValidator infovalidator) {
			this.checkoutValidator = infovalidator;
		}
*/
		
		
		@RequestMapping(value = "/bzcheckout/{cartid}", method = RequestMethod.GET)
		public ModelAndView showCheckout(HttpSession session, @PathVariable("cartid") int cartid, Model model ){
			
//			logger.info("in showcheckout, cartid is: " + cartid);
			String username = (String) session.getAttribute("username");
//			logger.info("in showcheckout, username is: " + username);
			int userid = (Integer) session.getAttribute("userid");
			BZCart bzcart = (BZCartImpl) session.getAttribute("bzcart");
//			logger.info("in showcheckout, bzcart id is: " + bzcart.getCartId() + " and qty is: " + bzcart.getCartQty());
			BZUserInfo bzuserinfo = userManager.getSingleUserById(userid);
//			logger.info("in showcheckout, bzuserinfo has username: " + bzuserinfo.getUserName());
			BZCheckoutInfo bzcheckoutinfo = new BZCheckoutInfo(bzcart, bzuserinfo);
//			logger.info("bzcheckoutinfo has username: " + bzcheckoutinfo.getUserInfo().getUserName());
			session.setAttribute("checkout", true);
			session.setAttribute("numCartItems", bzcart.getCartQty());
			session.setAttribute("bzcheckoutinfo", bzcheckoutinfo);

			return new ModelAndView("bzcheckout", "bZCheckoutInfo", bzcheckoutinfo);
		}

		@RequestMapping(value = "/bzcheckout/{cartid}", method = RequestMethod.POST)
		public String processCheckout(HttpSession session, @ModelAttribute BZCheckoutInfo bZCheckoutInfo, @PathVariable("cartid") int cartid, Model model) {

			String username = (String) session.getAttribute("username");
//			logger.info("bzcheckoutinfo has username: " + bzcheckoutinfo.getUserInfo().getUserName());
			int userid = (Integer) session.getAttribute("userid");
			BZCheckoutInfo checkoutinfo = (BZCheckoutInfo) session.getAttribute("bzcheckoutinfo");
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

			if (!(checkoutValidator.validate(checkoutinfo))) {
				return "redirect:/bzaccountinfo/" + userid;
			} else {
				model.addAttribute("bZCheckoutInfo", checkoutinfo);
				return "redirect:/bzcheckout/" + userid;
			}			
	
		}
		
		
		@RequestMapping(value = "/bzthankyou", method = RequestMethod.GET)
		public String confirmCheckout(HttpSession session) {
			
			int userid = (Integer) session.getAttribute("userid");
				return "bzthankyou";
		}

}
