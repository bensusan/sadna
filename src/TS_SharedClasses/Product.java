package TS_SharedClasses;

public class Product {

	private static int nextID = 0;
	private int id;
	private String name;
	private int price;
	private int grading;
	private String category;
	private PurchasePolicy purchasePolicy;
	private Store store;
		
	public Product(String name, int price, int grading, String category, PurchasePolicy purchasePolicy) {
		this.id = nextID++;
		this.name = name;
		this.price = price;
		this.grading = grading;
		this.category = category;
		this.purchasePolicy = purchasePolicy;
	}

	public Product(Product oldProduct) {
		this.id=oldProduct.id;
		this.name=oldProduct.name;
		this.price=oldProduct.price;
		this.grading=oldProduct.grading;
		this.category=oldProduct.category;
		this.purchasePolicy=oldProduct.purchasePolicy;
		this.store=oldProduct.store;
	}

	public int getId() {
		return id;
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
		if(purchasePolicy == null && this.store != null)
			return this.store.getStorePolicy();
		return purchasePolicy;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return this.id == other.id;
//		if (category == null) {
//			if (other.category != null)
//				return false;
//		} else if (!category.equals(other.category))
//			return false;
//		if (grading != other.grading)
//			return false;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (price != other.price)
//			return false;
//		if (purchasePolicy == null) {
//			if (other.purchasePolicy != null)
//				return false;
//		} else if (!purchasePolicy.equals(other.purchasePolicy))
//			return false;
//		if (store == null) {
//			if (other.store != null)
//				return false;
//		} else if (!store.equals(other.store))
//			return false;
//		return true;
	}
	
	
}
