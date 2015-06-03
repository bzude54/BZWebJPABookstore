package edu.uwpce.bzbookstore.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BZCartController {

	private static final Logger logger = LoggerFactory
			.getLogger(BZCartController.class);

	@Autowired
	private BZCartsManager cartManager;
	
	@Autowired
	private BZBooksManager bookManager;
	
	private BZCart bzcart = new BZCartImpl();

/*	@Resource(name = "bookManager")
	public void setBookManager(BZBookManager bookManager) {
		this.bookManager = bookManager;
	}
	
	@Resource(name="cartManager")
	public void setCartManager(BZCartManager cartmanager) {
		this.cartManager = cartmanager;
	}

*/
	@RequestMapping(value = "/addtocart/{bookid}", method = RequestMethod.GET)
	public String addItemToCart(HttpSession session,
			@PathVariable("bookid") String bookid) {

//		logger.info("addItemTOCart bookid: " + bookid);
		int userid = (Integer) session.getAttribute("userid");
//		logger.info("userid is: " + userid);
		bzcart = cartManager.getCart(userid);
		BZCartItem cartItem = bzcart.getSingleCartItem(bookid);
		if (cartItem != null) {
			cartItem.incrementCartItemQty();
		} else {
//			logger.info("cartItem IS null!");
			cartItem = new BZCartItem(bookManager.getSingleBook(bookid));
//			logger.info("cartItem has an id: " + cartItem.getCartItemId());
			bzcart.setSingleCartItem(cartItem);
		}

		logger.info("cartItem - " + cartItem.toString());
/*		logger.info("added book to cart for: " + userid
				+ " belonging to: " + bzcart.getCartId());
*/		
		session.setAttribute("addcartitem", cartItem);
		session.setAttribute("numCartItems", bzcart.getCartQty());

		return "redirect:/confirmcartadd";
	}

	@RequestMapping(value = "/confirmcartadd", method = RequestMethod.GET)
	public ModelAndView showConfirmation(HttpSession session) {

		BZCartItem cartItem = (BZCartItem) session.getAttribute("addcartitem");
		return new ModelAndView("bzconfirmcartadd", "itemBookTitle", cartItem
				.getCartItemBook().getTitle());
	}

	@RequestMapping(value = "/bzcart", method = RequestMethod.GET)
	public ModelAndView showCart(HttpSession session) {

		int userid = (Integer) session.getAttribute("userid");
		BZCartImpl bzcart = (BZCartImpl) cartManager.getCart(userid);
/*		if (bzcart != null) {
			logger.info("cartID in showcart is: " + bzcart.getCartId());
			for (BZCartItem item : bzcart.getCartItems()) {
				logger.info("itemID in showcart is: " + item.getCartItemId());
			}
		}
*/
		session.setAttribute("bzcart", bzcart);
		session.setAttribute("numCartItems", bzcart.getCartQty());


		return new ModelAndView("bzcart", "bZCartImpl", bzcart);
	}

	@RequestMapping(value = "/bzcart", method = RequestMethod.POST)
	public String updateCart(HttpSession session,
			@ModelAttribute BZCartImpl bzcart) {

//		logger.info("returning from cart");
		BZCartImpl checkcart = (BZCartImpl) session.getAttribute("bzcart");
//		logger.info("checkcart: " + checkcart.getCartId());
		for (BZCartItem item : bzcart.getCartItems()) {
/*			logger.info("book ISBN in cart: "
					+ item.getCartItemId() + " Total price: " + item.getCartItemTotalPrice() + " qty: "
					+ item.getCartItemQty());
*/            
			if (item.getCartItemQty() > 0) {
                checkcart.getSingleCartItem(item.getCartItemId()).setCartItemQty(item.getCartItemQty());
            } else {
                checkcart.setCartItemQty(item.getCartItemId(), 0);
            }
		}

		cartManager.setCart(checkcart);
	    session.setAttribute("bzcart", checkcart);
		session.setAttribute("numCartItems", checkcart.getCartQty());
	        
		return "redirect:bzcart";
	}

}
