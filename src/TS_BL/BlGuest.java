package TS_BL;

import java.util.Map;

import TS_SharedClasses.*;

public class BlGuest {

	/**
	 * add product to cart amount times
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean addProductToCart(Guest g, Product p,int amount)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * remove product p from guest cart
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public boolean removeProductFromCart(Guest g, Product p)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * replace the amount of product p by the new amount in guest cart
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean editProductInCart(Guest g, Product p,int amount)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * edit products in cart by a new cart 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public boolean editCart(Guest g, Map<Product,Integer> newCart)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * guest try to puchase his cart with given details
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * guest try to puchase one product with given details
	 * @param product
	 * @param amount
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress)
	{
		//TODO missing implementation
				return true;
	}
}
