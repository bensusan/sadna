package TS_BL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import TS_SharedClasses.*;

public class BlGuest {

	/**
	 * add product to cart amount times
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean addProductToCart(Guest g, Product p, int amount) {
		Cart c = g.getCart();
		Map<Product, Integer> toRet = c.getProducts();
		toRet.put(p, amount);
		c.setProducts(toRet);
		g.setCart(c);
		return true;
	}

	/**
	 * remove product p from guest cart
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public static boolean removeProductFromCart(Guest g, Product p) {
		if (g.getCart().getProducts().containsKey(p)) {
			Cart c = g.getCart();
			Map<Product, Integer> toRet = c.getProducts();
			toRet.remove(p);
			c.setProducts(toRet);
			g.setCart(c);
			return true;
		}
		return false;
	}

	/**
	 * replace the amount of product p by the new amount in guest cart
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean editProductInCart(Guest g, Product p, int amount) {
		Cart c = g.getCart();
		Map<Product, Integer> toRet = c.getProducts();
		if (!c.getProducts().containsKey(p)) {
			return false; // the p product doesn't exist in the cart, therefore
			// you need to add it
		}
		toRet.put(p, amount);
		c.setProducts(toRet);
		g.setCart(c);
		return true;
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		Cart c = g.getCart();
		c.setProducts(newCart);
		g.setCart(c);
		return true;
	}

	/**
	 * guest try to puchase his cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public static boolean puchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
		for (Product p : g.getCart().getProducts().keySet()) {
			pruchaseProduct(g, p, g.getCart().getProducts().get(p), creditCardNumber, buyerAddress);
		}
		return true;
	}

	/**
	 * guest try to puchase one product with given details
	 * 
	 * @param product
	 * @param amount
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public static boolean pruchaseProduct(Guest g, Product product, int amount, String creditCardNumber, String buyerAddress) {
		Map <Product, Integer> prods = new HashMap<Product, Integer>();
		prods.put(product, amount);
		Cart c = new Cart(prods);
		return BlMain.buyProduct(product.getStore(), product, amount) && BlMain.payToStore(product.getStore(), product.getPrice())
				&& BlMain.addPurchaseToHistory(product.getStore(), c) 
				&& BlPurchasePolicy.purchase(product.getPurchasePolicy(), g, product.getPrice(), amount); // true = call for paying system
	}
	
	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address, int phone, String creditCardNumber){
		if(BlMain.misspelled(username) || BlMain.misspelled(fullName) || BlMain.misspelled(address))
			return null; //exception spell in user name | full name | address
		if(!BlMain.legalPassword(password))
			return null; //password rules failed.
		if(BlMain.checkIfSubscriberExists(username) != null)
			return null; //user name exists
		return new Subscriber(g.getCart(), username, password, fullName, address, phone, creditCardNumber, new LinkedList<Cart>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>());
		
	}
	
	public static Subscriber signIn(Guest g, String username, String password){
		if(BlMain.misspelled(username))
			return null; //exception spell in user name
		if(!BlMain.legalPassword(password))
			return null; //password rules failed.
		return BlMain.checkIfSubscriberExists(username);
	}
}
