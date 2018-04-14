package TS_SharedClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Store {
	private int storeId;
	private String address;
	private int phone;
	private int gradeing;
	private Map<Product, Integer> products;
	private List<Cart> purchaseHistory;
	private boolean isOpen;
	private List<StoreOwner> myOwners;
	private List<StoreManager> myManagers;
	private int moneyEarned;

	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Store(int storeId, String address, int phone, int gradeing, Map<Product, Integer> products,
			List<Cart> purchaseHistory, boolean isOpen) {
		super();
		this.storeId = storeId;
		this.address = address;
		this.phone = phone;
		this.gradeing = gradeing;
		this.products = products;
		this.purchaseHistory = purchaseHistory;
		this.isOpen=isOpen;
		this.myOwners = new LinkedList<StoreOwner>();
		this.myManagers = new LinkedList<StoreManager>();
		this.moneyEarned = 0;
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

	public List<StoreOwner> getMyOwners() {
		return myOwners;
	}

	public void setMyOwners(List<StoreOwner> myOwners) {
		this.myOwners = myOwners;
	}

	public List<StoreManager> getMyManagers() {
		return myManagers;
	}

	public void setMyManagers(List<StoreManager> myManagers) {
		this.myManagers = myManagers;
	}

	public int getMoneyEarned() {
		return moneyEarned;
	}

	public void setMoneyEarned(int moneyEarned) {
		this.moneyEarned = moneyEarned;
	}
	
}
