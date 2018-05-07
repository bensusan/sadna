package TS_SharedClasses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TS_BL.BlMain;

public class Store {
	
	private static int nextID = 0;
	
	private int storeId;
	private String name;
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
	private Map<Category,PurchasePolicy> categoryDiscounts;

	public Store(String name, String address, String phone, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen) {
		super();
		this.storeId = nextID++;
		this.name = name;
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
		this.categoryDiscounts=new HashMap<Category,PurchasePolicy>();
	}
	
	public Store(String name, String address, String phone, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen, PurchasePolicy storePolicy,Map<Category,PurchasePolicy> categoryDiscounts) {
		this(name, address, phone, gradeing, products, purchaseHistory, isOpen);
		this.setStorePolicy(storePolicy);
		this.categoryDiscounts=categoryDiscounts;
	}

	public int getStoreId() {
		return storeId;
	}
	
	public String getStoreName(){
		return name;
	}
	
	public void setStoreName(String name){
		this.name = name;
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

	public Map<Category, PurchasePolicy> getCategoryDiscounts() {
		return categoryDiscounts;
	}

	public void setCategoryDiscounts(Map<Category, PurchasePolicy> categoryDiscounts) {
		this.categoryDiscounts = categoryDiscounts;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		return this.storeId == other.storeId;
//		if (address == null) {
//			if (other.address != null)
//				return false;
//		} else if (!address.equals(other.address))
//			return false;
//		if (gradeing != other.gradeing)
//			return false;
//		if (isOpen != other.isOpen)
//			return false;
//		if (moneyEarned != other.moneyEarned)
//			return false;
//		if(!BlMain.equalsLists(myManagers, other.myManagers))
//			return false;
//		if(!BlMain.equalsLists(myOwners, other.myOwners))
//			return false;
//		if (phone == null) {
//			if (other.phone != null)
//				return false;
//		} else if (!phone.equals(other.phone))
//			return false;
//		if(!BlMain.equalsMaps(products, other.products))
//			return false;
//		if(!BlMain.equalsLists(this.purchaseHistory, other.purchaseHistory))
//				return false;
//		if (storeId != other.storeId)
//			return false;
//		if (storePolicy == null) {
//			if (other.storePolicy != null)
//				return false;
//		} else if (!storePolicy.equals(other.storePolicy))
//			return false;
//		return true;
	}
	
	
}
