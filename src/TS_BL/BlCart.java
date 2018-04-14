package TS_BL;

import java.util.Map;

import TS_SharedClasses.*;

public class BlCart {

	/**
	 * customer try to puchase this cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public static boolean puchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
		// TODO missing implementation
		return true;
	}

	/**
	 * add <p,amount> to products
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean addProduct(Cart c, Product p, int amount) {
		// TODO missing implementation
		return true;
	}

	/**
	 * remove product p from products
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public static boolean removeProduct(Cart c, Product p) {
		// TODO missing implementation
		return true;
	}

	/**
	 * replace the amount of product p by the new amount
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean editProduct(Cart c, Product p, int amount) {
		// TODO missing implementation
		return true;
	}

	/**
	 * edit products by replaceing the amount of each product with the new
	 * amount
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public static boolean editCart(Cart c, Map<Product, Integer> newCart) {
		// TODO missing implementation
		return true;
	}

}
