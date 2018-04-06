package main;

import java.util.List;

public class Subscriber extends Guest {
	
	private String username;
	private String password;
	private String fullName;
	private String address;
	private int phone;
	private int creditCardNumber;
	private List<Cart> purchaseHistory;
	private List<StoreManager> manager;
	private List<StoreOwner> owner;
	
	public Subscriber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subscriber(Cart cart) {
		super(cart);
		// TODO Auto-generated constructor stub
	}

	public Subscriber(Cart cart, String username, String password, String fullName, String address, int phone,
			int creditCardNumber,List<Cart> purchaseHistory,List<StoreManager> manager,List<StoreOwner> owner) {
		super(cart);
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.address = address;
		this.phone = phone;
		this.creditCardNumber = creditCardNumber;
		this.purchaseHistory=purchaseHistory;
		this.manager=manager;
		this.owner=owner;
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

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public List<Cart> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<Cart> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}

	
	/**
	 * subscriber try to open a new store
	 * the address of the store will be the address of the subscriber
	 * the phone of the store will be the phone of the subscriber
	 * the id of the store will choose by the system 
	 * @param storeName
	 * @param Description
	 * @return new Store if succseed null otherwise
	 */
	public Store openStore(String storeName,String Description)
	{
		//address and phone
		//TODO missing implementation
				return null;
	}
	
	/**
	 * add cart to history
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	public boolean addPurchaseToHistory(Cart cart)
	{
		//TODO missing implementation
		return false;
	}
	
	/**
	 * add new ownership
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public boolean addOwner(StoreOwner owner)
	{
		//TODO missing implementation
				return false;
	}
	/**
	 * add new manager responsibility
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public boolean addManager(StoreManager manager)
	{
		//TODO missing implementation
				return false;
	}
	
	/**
	 * delete ownership
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public boolean deleteOwner(StoreOwner owner){
		//TODO missing implementation
		return false;
	}
	/**
	 * delete manager responsibility
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public boolean deleteManager(StoreManager manager){
		//TODO missing implementation
		return false;
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
