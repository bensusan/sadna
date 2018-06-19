package TS_SharedClasses;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import TS_BL.BlMain;

public class Subscriber extends Guest {
	
	protected String username;
	protected String password;
	protected String fullName;
	protected String address;
	protected String phone;
	protected String creditCardNumber;
	protected transient List<Purchase> purchaseHistory;
	protected List<StoreManager> manager;
	protected List<StoreOwner> owner;
	protected boolean isAdmin;

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
		isAdmin = false;
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
		isAdmin = false;
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
	
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscriber other = (Subscriber) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if(!BlMain.equalsLists(manager, other.manager))
			return false;
		if(!BlMain.equalsLists(owner, other.owner))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if(!BlMain.equalsLists(purchaseHistory, other.purchaseHistory))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
