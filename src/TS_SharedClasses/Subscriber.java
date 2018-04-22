package TS_SharedClasses;

import java.util.LinkedList;
import java.util.List;

public class Subscriber extends Guest {
	
	private String username;
	private String password;
	private String fullName;
	private String address;
	private String phone;
	private String creditCardNumber;
	private List<Purchase> purchaseHistory;
	private List<StoreManager> manager;
	private List<StoreOwner> owner;

	public Subscriber(String username, String password, String fullName, String address, String phone,
			String creditCardNumber,List<Purchase> purchaseHistory,List<StoreManager> manager,List<StoreOwner> owner) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.address = address;
		this.phone = phone;
		this.creditCardNumber = creditCardNumber;
		this.purchaseHistory = purchaseHistory;
		if(purchaseHistory == null)
			this.purchaseHistory = new LinkedList<Purchase>();
		this.manager=manager;
		if(manager == null)
			this.manager = new LinkedList<StoreManager>();
		this.owner=owner;
		if(owner == null)
			this.owner = new LinkedList<StoreOwner>();
	}
	
	public Subscriber(Cart cart, String username, String password, String fullName, String address, String phone,
			String creditCardNumber,List<Purchase> purchaseHistory,List<StoreManager> manager,List<StoreOwner> owner) {
		super(cart);
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.address = address;
		this.phone = phone;
		this.creditCardNumber = creditCardNumber;
		this.purchaseHistory = purchaseHistory;
		if(purchaseHistory == null)
			this.purchaseHistory = new LinkedList<Purchase>();
		this.manager=manager;
		if(manager == null)
			this.manager = new LinkedList<StoreManager>();
		this.owner=owner;
		if(owner == null)
			this.owner = new LinkedList<StoreOwner>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	public List<Purchase> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<Purchase> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}

	public List<StoreManager> getManager() {
		return manager;
	}

	public void setManager(List<StoreManager> manager) {
		this.manager = manager;
	}

	public List<StoreOwner> getOwner() {
		return owner;
	}

	public void setOwner(List<StoreOwner> owner) {
		this.owner = owner;
	}
	
}
