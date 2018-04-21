package TS_BL;

import java.util.Date;
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
		return g != null && BlCart.addProduct(g.getCart(), p, amount);
	}

	/**
	 * remove product p from guest cart
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public static boolean removeProductFromCart(Guest g, Product p) {
		return g != null && BlCart.removeProduct(g.getCart(), p);
	}

	/**
	 * replace the amount of product p by the new amount in guest cart
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean editProductInCart(Guest g, Product p, int amount) {
		return g != null && BlCart.editProduct(g.getCart(), p, amount);
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		return g != null && BlCart.editCart(g.getCart(), newCart);
	}

	/**
	 * guest try to puchase his cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public static boolean puchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
		if(g == null || g.getCart().getProducts().isEmpty() || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.legalAddress(buyerAddress))
			return false;
		for (Product p : g.getCart().getProducts().keySet()) {
			pruchaseProduct(g, p, g.getCart().getProducts().get(p), creditCardNumber, buyerAddress);
		}
		BlMain.addCreditCartToMap(creditCardNumber, g);
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
		if(g == null || product == null || amount < 1 || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.legalAddress(buyerAddress))
			return false;
		BlMain.addCreditCartToMap(creditCardNumber, g);
		if(BlMain.purchase(product, g, product.getPrice(), amount)){
			Date date = new Date();
			Map<Product, Integer> purchased = new HashMap<Product, Integer>();
			purchased.put(product, amount);
			Cart c = new Cart();
			c.setProducts(purchased);
			Purchase purchase = new Purchase(date, BlMain.getPurchaseId(), c);
			BlMain.incrementPurchaseId();
			return BlMain.addPurchaseToHistory(product.getStore(), purchase);
		}
		return false;
	}
	
	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address, String phone, String creditCardNumber){
		if(g == null)
			return null;
		if(!BlMain.correctSpelledLettersSpacesNumbers(username) || !BlMain.correctSpelledLettersSpaces(fullName) || !BlMain.correctSpelledLettersSpacesNumbers(address) || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.correctSpelledNumbers(phone))
			return null; //exception spell in user name | full name | address
		if(!BlMain.legalPassword(password))
			return null; //password rules failed.
		if(BlMain.checkIfSubscriberExists(username) != null)
			return null; //user name exists
		Subscriber newSub = new Subscriber(g.getCart(), username, password, fullName, address, phone, creditCardNumber, new LinkedList<Cart>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>()); 
		BlMain.allSubscribers.add(newSub);
		return newSub;
		
	}
	
	public static Subscriber signIn(Guest g, String username, String password){
		if(g == null)
			return null;
		if(!BlMain.correctSpelledLettersSpacesNumbers(username))
			return null; //exception spell in user name
		if(!BlMain.legalPassword(password))
			return null; //password rules failed.
		return BlMain.checkIfSubscriberExists(username);
	}
}
