package TS_SharedClasses;

public class Product {

	private String id;
	private String name;
	private int price;
	private int grading;
	private String category;
	private PurchasePolicy purchasePolicy;
	private Store store;
		
	public Product(String id, String name, int price, int grading, String category, PurchasePolicy purchasePolicy) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.grading = grading;
		this.category = category;
		this.purchasePolicy = purchasePolicy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getGrading() {
		return grading;
	}

	public void setGrading(int grading) {
		this.grading = grading;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
		this.purchasePolicy = purchasePolicy;
	}

	public PurchasePolicy getPurchasePolicy() {
		return purchasePolicy;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
}
