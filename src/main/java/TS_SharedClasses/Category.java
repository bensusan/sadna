package TS_SharedClasses;

import java.util.LinkedList;
import java.util.List;

public class Category {
	private String name;
	
//	private transient List<Product>products;
	
	public Category(String name){
		this.name=name;
//		this.products=new LinkedList<Product>();
	}
	
	
	//WARNING FOR DAL!!!!!
//	public Category(String name/*, PurchasePolicy purchasePolicy, List<Product> products*/) {
//		super();
//		this.name = name;
//		this.purchasePolicy = purchasePolicy;
//		this.products = products;
//	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	public List<Product> getProducts() {
//		return products;
//	}
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}
}
