package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlStoreManager {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 */
	public boolean addProductToStore(StoreManager sm, Product product,int amount)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 */
	public boolean deleteProductFromStore(StoreManager sm, Product product)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * update the product details , assumption the new product have same id like the old one 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public boolean updateProductDetails(StoreManager sm, Product newProduct, int amount)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * add policy to product
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy,Product product)
	{
		//TODO missing implementation
		return false;
	}
	
	/**
	 * add discount to product
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount,Product product)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * add new owner to store
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public boolean addNewStoreOwner(StoreManager sm, StoreOwner owner)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * add new manager to store
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public boolean addNewManager(StoreManager oldMan, StoreManager newMan)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @return true if the store close false otherwise
	 */
	public boolean closeStore(StoreManager sm)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @return true if the store reopen false otherwise
	 */
	public boolean openStore(StoreManager sm)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @return history of pruchase in the store
	 */
	public List<Cart> getPurchaseHistory(StoreManager sm)
	{
		//TODO missing implementation
		return null;
	}
}
