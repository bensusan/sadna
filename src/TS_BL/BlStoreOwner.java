package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlStoreOwner {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 */
	static boolean addProductToStore(StoreOwner so, Product product, int amount) {
		return so != null && BlPermissions.addProductToStore(so.getStore(), product, amount);
	}
	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 */
	static boolean deleteProductFromStore(StoreOwner so, Product product) {
		return so != null && BlPermissions.deleteProductFromStore(so.getStore(), product);
	}
	/**
	 * update the product details , assumption the new product have same id like the old one 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount) {
		return so != null && BlPermissions.updateProductDetails(so.getStore(), oldProduct, newProduct, amount);
	}
	/**
	 * add policy to product
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 */
	static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product)
	{
		return so != null && BlPermissions.addPolicyToProduct(so.getStore(), policy, product);
	}
	
	/**
	 * add discount to product
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 */
	static boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product)
	{
		return so != null && BlPermissions.addDiscountToProduct(so.getStore(), discount, product);
	}
	/**
	 * add new owner to store
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	static boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo)
	{
		return (oldSo != null && newSo != null && oldSo.getStore() != null) && BlPermissions.addNewStoreOwner(oldSo.getStore(), newSo);
	}
	/**
	 * add new manager to store
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	static boolean addNewManager(StoreOwner so, StoreManager manager)
	{
		return (so != null && manager != null && so.getStore() != null) && BlPermissions.addNewManager(so.getStore(), manager);
	}
	/**
	 * @return true if the store close false otherwise
	 */
	static boolean closeStore(StoreOwner so)
	{
		return so != null && BlPermissions.closeStore(so.getStore());
	}
	/**
	 * @return true if the store reopen false otherwise
	 */
	static boolean openStore(StoreOwner so)
	{
		return so != null && BlPermissions.openStore(so.getStore());
	}
	/**
	 * @return history of pruchase in the store
	 */
	static List<Purchase> getPurchaseHistory(StoreOwner so)
	{
		if(so == null || so.getStore() == null)
			return null;
		
		return so.getStore().getPurchaseHistory();
	}
	
	static void expiredProducts(StoreOwner so){
		if(so != null) BlPermissions.expiredProducts(so.getStore());
	}
	
	static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp){
		return so != null && BlPermissions.changeStorePurchasePolicy(so.getStore(), pp);
	}
}
