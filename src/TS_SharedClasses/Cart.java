package TS_SharedClasses;

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
	
}
