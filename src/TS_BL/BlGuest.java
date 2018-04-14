package TS_BL;

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
		// TODO missing implementation
		return true;
	}

	/**
	 * remove product p from guest cart
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public static boolean removeProductFromCart(Guest g, Product p) {
		// TODO missing implementation
		return true;
	}

	/**
	 * replace the amount of product p by the new amount in guest cart
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean editProductInCart(Guest g, Product p, int amount) {
		// TODO missing implementation
		return true;
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		// TODO missing implementation
		return true;
	}

	/**
	 * guest try to puchase his cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public static boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress) {
		// TODO missing implementation
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
	public static boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress) {
		// TODO missing implementation
		return true;
	}
	
	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address, int phone, int creditCardNumber){
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
