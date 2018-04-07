package TS_SharedClasses;

import java.util.List;
import java.util.Map;

public class Store {
	private int storeId;
	private String address;
	private int phone;
	private int gradeing;
	private Map<Product, Integer> products;
	private List<Cart> purchaseHistory;
	public boolean isOpen;
	

	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Store(int storeId, String address, int phone, int gradeing, Map<Product, Integer> products,
			List<Cart> purchaseHistory,boolean isOpen) {
		super();
		this.storeId = storeId;
		this.address = address;
		this.phone = phone;
		this.gradeing = gradeing;
		this.products = products;
		this.purchaseHistory = purchaseHistory;
		this.isOpen=isOpen;
		
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getGradeing() {
		return gradeing;
	}

	public void setGradeing(int gradeing) {
		this.gradeing = gradeing;
	}

	public Map<Product, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<Product, Integer> products) {
		this.products = products;
	}

	public List<Cart> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<Cart> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}
	
	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
}
