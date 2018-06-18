package TS_SharedClasses;

import java.io.Serializable;

import com.google.gson.Gson;

import TS_BL.BlMain;

public class Product  {

//	private static int nextID = 0;
	private int id;
	private String name;
	private int price;
	private int grading;
	private PurchasePolicy purchasePolicy;
	private transient Store store;
	private Category category;
	private PurchaseType type;
	
	public Product() throws Exception{
		this.id = /*nextID++;*/ BlMain.dalRef.getNextProductId();
		this.name = "";
		this.price = 0;
		this.grading = 0;
		this.category = null;
		this.purchasePolicy = null;
		this.type=null;
	}
	public Product(String name, int price, int grading, PurchasePolicy purchasePolicy,PurchaseType type) throws Exception {
		this.id = /*nextID++;*/ BlMain.dalRef.getNextProductId();
		this.name = name;
		this.price = price;
		this.grading = grading;
		this.category = null;
		this.purchasePolicy = purchasePolicy;
		this.type=type;
	}

	public Product(Product oldProduct) {
		this.id=oldProduct.id;
		this.name=oldProduct.name;
		this.price=oldProduct.price;
		this.grading=oldProduct.grading;
		this.category=oldProduct.category;
		this.purchasePolicy=oldProduct.purchasePolicy;
		this.type=oldProduct.type;
		
	}
	
	
	//WARNING FOR DAL!!!!!!!!
	public Product(int id, String name, int price, int grading, PurchasePolicy purchasePolicy, Store store,
			Category category, PurchaseType type) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.grading = grading;
		this.purchasePolicy = purchasePolicy;
		this.store = store;
		this.category = category;
		this.type = type;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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

	public PurchaseType getType() {
		return type;
	}
	public void setType(PurchaseType type) {
		this.type = type;
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
	
	public String toString(){
		String toRet = "Id: " + this.getId() + ", Name: " + this.getName() + ", Price: " + this.getPrice() + ", Grade: " +
						this.getGrading() + ", Category: " + this.getCategory().getName() + ", Policy: " + 
						this.getPurchasePolicy().toString() + ", Type: " + this.getType().toString();
		return toRet;
	}
	
	
}
