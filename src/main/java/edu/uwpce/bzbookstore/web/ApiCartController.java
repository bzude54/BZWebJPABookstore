package edu.uwpce.bzbookstore.web;

import javax.servlet.http.HttpServletResponse;

import edu.uwpce.bzbookstore.model.Book;
import edu.uwpce.bzbookstore.model.Cart;
import edu.uwpce.bzbookstore.model.CartItem;
import edu.uwpce.bzbookstore.service.BookService;
import edu.uwpce.bzbookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/{customerid}")
public class ApiCartController {
    
    @Autowired
    private BookService bookService;
	
	@Autowired
    private CartService cartService;
    
    @RequestMapping(value="/carts", method=RequestMethod.GET)
    public Object getCart(@PathVariable("customerid") int customerid) {
        Cart cart = cartService.getCart(customerid);
        if (cart.getCartQty() > 0) {
            return cart;
        } else {
    		return new ApiMessage(ApiMessage.MsgType.INFO, "Your cart is empty.");
        }
    }
    
    
    @RequestMapping(value="/carts", method=RequestMethod.PUT)
    public Cart updateCart(@RequestBody Cart cart, @PathVariable("customerid") int customerid) {
    	cartService.deleteCart(customerid);
    	cartService.setCart(cart);
    	return cartService.getCart(customerid);
    }


    @RequestMapping(value="/carts", method=RequestMethod.DELETE)
    public Object deleteCart(@PathVariable("customerid") int customerid, HttpServletResponse response) {
    	if (cartService.deleteCart(customerid)) {
    		return new ApiMessage(ApiMessage.MsgType.INFO, "Cart with id: " + customerid + " has been deleted");
       	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Cart with id: " + customerid + " was not found.");
    	}
    }
   
    @RequestMapping(value="/carts/{itemId}", method=RequestMethod.GET)
    public Object getCartItem(@PathVariable("customerid") int customerid, @PathVariable("itemId") String itemId) {
        CartItem cartitem = cartService.getCart(customerid).getSingleCartItem(itemId);
        if (cartitem != null) {
            return cartitem;
        } else {
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Cart item with item id: " + itemId + " does not exist.");
        }
    }

    
    @RequestMapping(value="/carts", method=RequestMethod.POST)
    public Object createCartItem(@RequestBody Book book, @PathVariable("customerid") int customerid, HttpServletResponse response) {
        if (cartService.getCart(customerid).getSingleCartItem(book.getIsbn()) != null) {
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Cart item with id: " + book.getIsbn() + " already exists.");
        }
        CartItem newCartItem = new CartItem(book);
        cartService.getCart(customerid).setSingleCartItem(newCartItem);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return cartService.getCart(customerid).getSingleCartItem(newCartItem.getCartItemId());
    }
    
    
    @RequestMapping(value="/carts/{itemId}", method=RequestMethod.PUT)
    public CartItem updateCartItem(@RequestBody Book book, @PathVariable("customerid") int customerid, @PathVariable("itemId") String itemId) {
        CartItem newCartItem = new CartItem(book);
    	cartService.updateCartItem(customerid, newCartItem);
    	return cartService.getCartItem(customerid, itemId);
    }
    
    
    @RequestMapping(value="/carts/{itemId}", method=RequestMethod.DELETE)
    public Object deleteCartItem(@PathVariable("itemId") String itemId, @PathVariable("customerid") int customerid, HttpServletResponse response) {
    	if (cartService.deleteCartItem(customerid, itemId)) {
    		return new ApiMessage(ApiMessage.MsgType.INFO, "Cart item with id: " + itemId + " has been deleted");
       	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Cart item with id: " + itemId + " was not found.");
    	}
    }
    
    
}
