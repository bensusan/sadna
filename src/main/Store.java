package main;

import java.util.List;

public class Store {
	private int storeId;
	private String address;
	private int phone;
	private int gradeing;
	private List<Product> products;
	private List<Cart> purchaseHistory;
	public boolean isOpen;
	

	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Store(int storeId, String address, int phone, int gradeing, List<Product> products,
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Cart> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<Cart> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}
	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	/**
	 * @param p
	 * @param amount
	 * @return true if product in stock false otherwise
	 */
	public boolean checkInStock(Product p,int amount)
	{
		//TODO missing implementation
		return false;
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
	 * reduce product amount from stock
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean buyProduct(Product p,int amount)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * add product to stock
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean stockUpdate(Product p,int amount)
	{
		//TODO missing implementation
				return false;
	}
	
}
