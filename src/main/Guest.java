package main;

import java.util.Map;

public class Guest {
	private Cart cart;
	
	public Guest(Cart cart) {
		super();
		this.cart = cart;
	}
	
	
	public Guest() {
		super();
		this.cart=new Cart();
	}
	
	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}

	

	/**
	 * add product to cart amount times
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean addProductToCart(Product p,int amount)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * remove product p from guest cart
	 * @param p
	 * @return true if succseed false otherwise
	 */
	public boolean removeProductFromCart(Product p)
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
	public boolean editProductInCart(Product p,int amount)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * edit products in cart by a new cart 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	public boolean editCart(Map<Product,Integer> newCart)
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
	public boolean puchaseCart(int creditCardNumber,String buyerAddress)
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
	public boolean pruchaseProduct(Product product,int amount,int creditCardNumber,String buyerAddress)
	{
		//TODO missing implementation
				return true;
	}
}
