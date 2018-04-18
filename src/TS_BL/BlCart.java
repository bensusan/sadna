package TS_BL;

import java.util.Map;

import TS_SharedClasses.*;

public class BlCart {

//	/**
//	 * customer try to puchase this cart with given details
//	 * 
//	 * @param creditCardNumber
//	 * @param buyerAddress
//	 * @return true if succseed false otherwise
//	 */
//	public static boolean puchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
//		// TODO missing implementation
//		return true;
//	}

	/**
	 * add <p,amount> to products
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean addProduct(Cart c, Product p, int amount) {
		if(c.equals(null) || p.equals(null) || amount < 1)
			return false;
		Map<Product, Integer> toRet = c.getProducts();
		toRet.put(p, amount);
		c.setProducts(toRet);
		return true;
	}

	/**
	 * remove product p from products
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public static boolean removeProduct(Cart c, Product p) {
		if(c.equals(null) || p.equals(null))
			return false;
		if (c.getProducts().containsKey(p)) {
			Map<Product, Integer> toRet = c.getProducts();
			toRet.remove(p);
			c.setProducts(toRet);
			return true;
		}
		return false;
	}

	/**
	 * replace the amount of product p by the new amount
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean editProduct(Cart c, Product p, int amount) {
		if(c.equals(null) || p.equals(null) || amount < 1)
			return false;
		if(c.getProducts().containsKey(p)){
			Map<Product, Integer> toRet = c.getProducts();
			toRet.put(p, amount);
			c.setProducts(toRet);
			return true;
		}
		return false;
	}

	/**
	 * edit products by replaceing the amount of each product with the new
	 * amount
	 * if there is a product in newcart which doesn't exist in c it will return
	// false
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public static boolean editCart(Cart c, Map<Product, Integer> newCart) {
		if(c.equals(null) || newCart.equals(null))
			return false;
		Map<Product, Integer> toRet = c.getProducts();
		for (Product p : newCart.keySet()) {
			if (!c.getProducts().containsKey(p)) {
				return false; // the p product doesn't exist in the cart,
							  // therefore you need to add it
			}
			toRet.put(p, newCart.get(p));
		}
		c.setProducts(toRet);
		return true;
	}

}
