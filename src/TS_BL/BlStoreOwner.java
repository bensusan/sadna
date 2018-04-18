package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlStoreOwner {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 */
	public static boolean addProductToStore(StoreOwner so, Product product, int amount) {
		return BlPermissions.addProductToStore(so.getStore(), product, amount);
	}
	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 */
	public static boolean deleteProductFromStore(StoreOwner so, Product product) {
		return BlPermissions.deleteProductFromStore(so.getStore(), product);
	}
	/**
	 * update the product details , assumption the new product have same id like the old one 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount) {
		return BlPermissions.updateProductDetails(so.getStore(), oldProduct, newProduct, amount);
	}
	/**
	 * add policy to product
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product)
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
	public static boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * add new owner to store
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public static boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * add new manager to store
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public static boolean addNewManager(StoreOwner so, StoreManager manager)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @return true if the store close false otherwise
	 */
	public static boolean closeStore(StoreOwner so)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @return true if the store reopen false otherwise
	 */
	public static boolean openStore(StoreOwner so)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * @return history of pruchase in the store
	 */
	public static List<Cart> getPurchaseHistory(StoreOwner so)
	{
		//TODO missing implementation
		return null;
	}
	
	public static void expiredProducts(StoreOwner so){
		BlPermissions.expiredProducts(so.getStore());
	}
	
	public static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp){
		return BlPermissions.changeStorePurchasePolicy(so.getStore(), pp);
	}
}
