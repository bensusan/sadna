package TS_BL;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import TS_SharedClasses.*;

public class BlGuest {
	private final static String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
	
	/**
	 * add product to cart amount times
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean addProductToCart(Guest g, Product p, int amount) {
		return g != null && BlCart.addProduct(g.getCart(), p, amount);
	}

	/**
	 * remove product p from guest cart
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 */
	static boolean removeProductFromCart(Guest g, Product p) {
		return g != null && BlCart.removeProduct(g.getCart(), p);
	}

	/**
	 * replace the amount of product p by the new amount in guest cart
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean editProductInCart(Guest g, Product p, int amount) {
		return g != null && BlCart.editProduct(g.getCart(), p, amount);
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		return g != null && BlCart.editCart(g.getCart(), newCart);
	}

	/**
	 * guest try to puchase his cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	static boolean puchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
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
	static boolean pruchaseProduct(Guest g, Product product, int amount, String creditCardNumber, String buyerAddress) {
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
	
	static Subscriber signUp(Guest g, String username, String password, String fullName, String address, String phone, String creditCardNumber){
		if(g == null)
			return null;
		if(!BlMain.correctSpelledLettersSpacesNumbers(username) || !BlMain.correctSpelledLettersSpaces(fullName) || !BlMain.correctSpelledLettersSpacesNumbers(address) || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.correctSpelledNumbers(phone))
			return null; //exception spell in user name | full name | address
		if(!BlMain.legalPassword(password))
			return null; //password rules failed.
		if(BlMain.checkIfSubscriberExists(username) != null)
			return null; //user name exists
		password = md5Hash(password);
		Subscriber newSub = new Subscriber(g.getCart(), username, password, fullName, address, phone, creditCardNumber, new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>()); 
		BlMain.allSubscribers.add(newSub);
		return newSub;
		
	}
	
	static Subscriber signIn(Guest g, String username, String password){
		if(g == null)
			return null;
		if(!BlMain.correctSpelledLettersSpacesNumbers(username))
			return null; //exception spell in user name
		if(!BlMain.legalPassword(password))
			return null; //password rules failed.
		Subscriber ans = BlMain.checkIfSubscriberExists(username);
		if(ans != null && ans.getPassword() == md5Hash(password))
			return ans;
		
		return null;
	}
	
	//Takes a string, and converts it to md5 hashed string.
    private static String md5Hash(String message) {
        String md5 = "";
        if(null == message) 
            return null;
      //adding a salt to the string before it gets hashed, to get better security
        message = message + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");//Create MessageDigest object for MD5
            digest.update(message.getBytes(), 0, message.length());//Update input string in message digest
            md5 = new BigInteger(1, digest.digest()).toString(16);//Converts message digest value in base 16 (hex)
  
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
