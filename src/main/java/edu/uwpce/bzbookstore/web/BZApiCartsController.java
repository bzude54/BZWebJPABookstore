package edu.uwpce.bzbookstore.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}")
public class BZApiCartsController {
    
    @Autowired
    private BZBooksManager booksManager;
	
	@Autowired
    private BZCartsManager cartsManager;
    
    @RequestMapping(value="/carts", method=RequestMethod.GET)
    public Object getCart(@PathVariable("userId") int userId) {
        BZCart cart = cartsManager.getCart(userId);
        if (cart.getCartQty() > 0) {
            return cart;
        } else {
    		return new BZApiMessage(BZApiMessage.MsgType.INFO, "Your cart is empty.");
        }
    }
    
    
    @RequestMapping(value="/carts", method=RequestMethod.PUT)
    public BZCart updateCart(@RequestBody BZCartImpl cart, @PathVariable("userId") int userId) {
    	cartsManager.deleteCart(userId);
    	cartsManager.setCart(cart);
    	return cartsManager.getCart(userId);
    }


    @RequestMapping(value="/carts", method=RequestMethod.DELETE)
    public Object deleteCart(@PathVariable("userId") int userId, HttpServletResponse response) {
    	if (cartsManager.deleteCart(userId)) {
    		return new BZApiMessage(BZApiMessage.MsgType.INFO, "Cart with id: " + userId + " has been deleted");
       	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Cart with id: " + userId + " was not found.");
    	}
    }
   
    @RequestMapping(value="/carts/{itemId}", method=RequestMethod.GET)
    public Object getCartItem(@PathVariable("userId") int userId, @PathVariable("itemId") String itemId) {
        BZCartItem cartitem = cartsManager.getCart(userId).getSingleCartItem(itemId);
        if (cartitem != null) {
            return cartitem;
        } else {
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Cart item with item id: " + itemId + " does not exist.");
        }
    }

    
    @RequestMapping(value="/carts", method=RequestMethod.POST)
    public Object createCartItem(@RequestBody BZBook book, @PathVariable("userId") int userId, HttpServletResponse response) {
        if (cartsManager.getCart(userId).getSingleCartItem(book.getISBN()) != null) {
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Cart item with id: " + book.getISBN() + " already exists.");
        }
        BZCartItem newCartItem = new BZCartItem(book);
        cartsManager.getCart(userId).setSingleCartItem(newCartItem);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return cartsManager.getCart(userId).getSingleCartItem(newCartItem.getCartItemId());
    }
    
    
    @RequestMapping(value="/carts/{itemId}", method=RequestMethod.PUT)
    public BZCartItem updateCartItem(@RequestBody BZBook book, @PathVariable("userId") int userId, @PathVariable("itemId") String itemId) {
        BZCartItem newCartItem = new BZCartItem(book);
    	cartsManager.updateCartItem(userId, newCartItem);
    	return cartsManager.getCartItem(userId, itemId);
    }
    
    
    @RequestMapping(value="/carts/{itemId}", method=RequestMethod.DELETE)
    public Object deleteCartItem(@PathVariable("itemId") String itemId, @PathVariable("userId") int userId, HttpServletResponse response) {
    	if (cartsManager.deleteCartItem(userId, itemId)) {
    		return new BZApiMessage(BZApiMessage.MsgType.INFO, "Cart item with id: " + itemId + " has been deleted");
       	} else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Cart item with id: " + itemId + " was not found.");
    	}
    }
    
    
}
