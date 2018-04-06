package main;

import java.util.List;

public class StoreOwner {
	
	private Store store;
	
	public StoreOwner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StoreOwner(Store store) {
		super();
		this.store = store;
	}
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 */
	public boolean addProductToStore(Product product,int amount){
		//TODO missing implementation
		return false;
	}
	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 */
	public boolean deleteProductFromStore(Product product){
		//TODO missing implementation
		return false;
	}
	/**
	 * update the product details , assumption the new product have same id like the old one 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean updateProductDetails(Product newProduct,int amount){
		//TODO missing implementation
		return false;
	}
	/**
	 * add policy to product
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public boolean addPolicyToProduct(PurchasePolicy policy,Product product){
		//TODO missing implementation
		return false;
	}
	
	/**
	 * add discount to product
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public boolean addDiscountToProduct(DiscountPolicy discount,Product product){
		//TODO missing implementation
		return false;
	}
	/**
	 * add new owner to store
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public boolean addNewStoreOwner(StoreOwner owner){
		//TODO missing implementation
		return false;
	}
	/**
	 * add new manager to store
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public boolean addNewManager(StoreManager manager){
		//TODO missing implementation
		return false;
	}
	/**
	 * @return true if the store close false otherwise
	 */
	public boolean CloseStore(){
		//TODO missing implementation
		return false;
	}
	/**
	 * @return true if the store reopen false otherwise
	 */
	public boolean OpenStore(){
		//TODO missing implementation
		return false;
	}
	/**
	 * @return history of pruchase in the store
	 */
	public List<Cart> getPurchaseHistory(){
		//TODO missing implementation
		return null;
	}
	

}
