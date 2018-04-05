package main;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<Product,Integer> products;
	
	public Cart()
	{
		this.products=new HashMap<Product,Integer>();
	}
	public Cart(Map<Product, Integer> products) {
		super();
		this.products = products;
	}
	public Map<Product,Integer> getProducts() {
		return products;
	}
	public void setProducts(Map<Product,Integer> products) {
		this.products = products;
	}
	
	/**
	 * customer try to puchase this cart with given details
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 */
	public boolean puchaseCart(int creditCardNumber,String buyerAddress)
	{
		//TODO missing implementation
		return true;
	}
	/**
	 * add <p,amount> to products
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean addProduct(Product p,int amount)
	{
		//TODO missing implementation
		return true;
	}
	/**
	 * remove product p from products
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public boolean removeProduct(Product p)
	{
		//TODO missing implementation
		return true;
	}
	/**
	 * replace the amount of product p by the new amount
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean editProduct(Product p,int amount)
	{
		//TODO missing implementation
		return true;
	}
	/**
	 * edit products by replaceing the amount of each product with the new amount 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public boolean editCart(Map<Product,Integer> newCart)
	{
		//TODO missing implementation
		return true;
	}
	
}
