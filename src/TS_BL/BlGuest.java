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
	static boolean addImmediatelyProduct(Guest g, Product p, int amount, int discountCode) {
		return g != null && BlCart.addImmediatelyProduct(g.getCart(), p, amount, discountCode);
	}
	
	static boolean addLotteryProduct(Guest g, Product p, int money) {
		return g != null && BlCart.addLotteryProduct(g.getCart(), p, money);
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
	static boolean editProductAmount(Guest g, Product p, int amount) {
		return g != null && BlCart.editProductAmount(g.getCart(), p, amount);
	}
	
	static boolean editProductDiscount(Guest g, Product p, int discountCode) {
		return g != null && BlCart.editProductDiscount(g.getCart(), p, discountCode);
	}
	
	static boolean editProductPrice(Guest g, Product p, int money) {
		return g != null && BlCart.editProductPrice(g.getCart(), p, money);
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	static boolean editCart(Guest g, Cart newCart) {
		return g != null && BlCart.editCart(g.getCart(), newCart);
	}

	/**
	 * guest try to puchase his cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	
	//TODO - CreditCard
	static boolean puchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
		if(g == null || g.getCart().getProducts().isEmpty() || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.legalAddress(buyerAddress))
			return false;
//		boolean isExistLotteryPurchase = false;
		Cart notPurchased = new Cart();
		for (ProductInCart pic : g.getCart().getProducts()) {
			if(BlPurchaseType.purchase(pic, g) && BlStore.payToStore(pic.getMyProduct().getStore(), pic.getPrice(), creditCardNumber))
				notPurchased.getProducts().add(pic);
//			else if(BlMain.isLotteryPurchase(pic.getMyProduct()))
//					isExistLotteryPurchase = true;
		}
		g.getCart().getProducts().removeAll(notPurchased.getProducts());
		//g.getCart() now has all the products that purchased.
		if(!sendTheProducts(g, buyerAddress))
			return false;
		g.setCart(notPurchased);
		//TODO
//		if(isExistLotteryPurchase)
//			BlMain.addCreditCardToMap(creditCardNumber, g);
		return true;
	}

	
	/**
	 * g has in cart all products that purchased already.
	 * need to find all products that can be send to the customer - i.e. ImmediatelyPurchase and may be more.
	 * then need to send all those products to the buyerAddress
	 * @param g - Guest, buyerAddress - destination address.
	 * @return true for success. false for fail.
	 */
	static boolean sendTheProducts(Guest g, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
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
