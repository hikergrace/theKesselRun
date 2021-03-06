//CARTController works, need to check updates when further along
package com.skilldistillery.swapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.swapp.CartItem;
import com.skilldistillery.swapp.service.CartItemService;
import com.skilldistillery.swapp.service.CartService;
import com.skilldistillery.swapp.service.ItemService;

@CrossOrigin({ "*", "http://localhost:4200" })
@RestController
@RequestMapping("api")
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;

	@RequestMapping(path = "CartItem/ping", method = RequestMethod.GET)
	public String ping() {
		return "pong";
	}

	@RequestMapping(path = "cartitems", method = RequestMethod.GET)
	public List<CartItem> index(HttpServletRequest req, HttpServletResponse res) {
		return cartItemService.index();
	}
	
	@RequestMapping(path = "cartitems/{itemid}/{cartid}", method = RequestMethod.POST)
	public CartItem addingToCart(HttpServletRequest req, HttpServletResponse res, @PathVariable("itemid") int itemId, @PathVariable("cartid") int cartId) {
		CartItem addingCartItem = new CartItem();
		addingCartItem.setCart(cartService.show(cartId));
		addingCartItem.setItem(itemService.show(itemId));
		return cartItemService.addToCart(addingCartItem);
	}

	@RequestMapping(path = "cartitems/{userid}", method = RequestMethod.GET)
	public List<CartItem> show(HttpServletRequest req, HttpServletResponse res, @PathVariable("userid") int userId) {
		return cartItemService.show(userId);
	}

	@RequestMapping(path = "cartitems/{id}", method = RequestMethod.PUT)
	public CartItem update(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int id,
			@RequestBody CartItem CartItem) {
		return cartItemService.update(id, CartItem);
	}
	
	@RequestMapping(path = "cartitems/checkout/{userid}", method = RequestMethod.PUT)
	public CartItem checkout(HttpServletRequest req, HttpServletResponse res, @PathVariable("userid") int userid,
			@RequestBody CartItem CartItem) {
		return cartItemService.checkout(userid, CartItem);
	}

	@RequestMapping(path = "cartitems/{id}", method = RequestMethod.DELETE)
	public void delete(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int id) {
		cartItemService.destroy(id);
	}
}
