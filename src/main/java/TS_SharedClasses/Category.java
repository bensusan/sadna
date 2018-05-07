package TS_SharedClasses;

import java.util.List;

public class Category {
	private String name;
	private PurchasePolicy purchasePolicy;
	private List<Product>products;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PurchasePolicy getPurchasePolicy() {
		return purchasePolicy;
	}
	public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
		this.purchasePolicy = purchasePolicy;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	

}
