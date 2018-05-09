package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlStoreOwner {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addProductToStore(StoreOwner so, Product product, int amount) throws Exception {
		return so != null && BlPermissions.addProductToStore(so.getStore(), product, amount);
	}
	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 * @throws Exception 
	 */
	static boolean deleteProductFromStore(StoreOwner so, Product product) throws Exception {
		return so != null && BlPermissions.deleteProductFromStore(so.getStore(), product);
	}
	/**
	 * update the product details , assumption the new product have same id like the old one 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount) throws Exception {
		return so != null && BlPermissions.updateProductDetails(so.getStore(), oldProduct, newProduct, amount);
	}
	/**
	 * add policy to product
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product) throws Exception
	{
		return so != null && BlPermissions.addPolicyToProduct(so.getStore(), policy, product);
	}
	
	/**
	 * add discount to product
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addDiscountToProduct(StoreOwner so, PurchasePolicy discountTree, Product product) throws Exception
	{
		return so != null && BlPermissions.addDiscountToProduct(so.getStore(), discountTree, product);
	}
	/**
	 * add new owner to store
	 * @param owner
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addNewStoreOwner(StoreOwner oldSo, Subscriber newSo) throws Exception
	{
		return (oldSo != null && newSo != null && oldSo.getStore() != null) && BlPermissions.addNewStoreOwner(oldSo.getStore(), newSo);
	}
	/**
	 * add new manager to store
	 * @param manager
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addNewManager(StoreOwner so, Subscriber manager) throws Exception
	{
		if(so == null || manager == null || so.getStore() == null)
			throw new Exception("something went wrong");
		
		return BlPermissions.addNewManager(so.getStore(), manager);
	}
	/**
	 * @return true if the store close false otherwise
	 * @throws Exception 
	 */
	static boolean closeStore(StoreOwner so) throws Exception
	{
		if(so == null)
			throw new Exception("something went wrong");
		return BlPermissions.closeStore(so.getStore());
	}
	/**
	 * @return true if the store reopen false otherwise
	 * @throws Exception 
	 */
	static boolean openStore(StoreOwner so) throws Exception
	{
		if(so == null)
			throw new Exception("something went wrong");
		return BlPermissions.openStore(so.getStore());
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
	
	static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp) throws Exception{
		return so != null && BlPermissions.changeStorePurchasePolicy(so.getStore(), pp);
	}
}
