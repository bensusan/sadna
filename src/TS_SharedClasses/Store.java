package TS_SharedClasses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Store {
	private int storeId;
	private String address;
	private String phone;
	private int gradeing;
	private Map<Product, Integer> products;
	private List<Purchase> purchaseHistory;
	private boolean isOpen;
	private List<StoreOwner> myOwners;
	private List<StoreManager> myManagers;
	private int moneyEarned;
	private PurchasePolicy storePolicy;
	
	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Store(int storeId, String address, String phone, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen) {
		super();
		this.storeId = storeId;
		this.address = address;
		this.phone = phone;
		this.gradeing = gradeing;
		this.products = products;
		if(products == null)
			this.products = new HashMap<Product, Integer>();
		this.purchaseHistory = purchaseHistory;
		if(purchaseHistory == null)
			this.purchaseHistory = new LinkedList<Purchase>();
		this.isOpen=isOpen;
		this.myOwners = new LinkedList<StoreOwner>();
		this.myManagers = new LinkedList<StoreManager>();
		this.moneyEarned = 0;
		this.setStorePolicy(null);
	}
	
	public Store(int storeId, String address, String phone, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen, PurchasePolicy storePolicy) {
		this(storeId, address, phone, gradeing, products, purchaseHistory, isOpen);
		this.setStorePolicy(storePolicy);
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
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

	public List<Purchase> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<Purchase> purchaseHistory) {
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

	public PurchasePolicy getStorePolicy() {
		return storePolicy;
	}

	public void setStorePolicy(PurchasePolicy storePolicy) {
		this.storePolicy = storePolicy;
	}
	
}
