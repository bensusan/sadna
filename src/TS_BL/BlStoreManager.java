package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlStoreManager {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 */
	public static boolean addProductToStore(StoreManager sm, Product product, int amount) {
		return sm.getPremisions()[BlPermissions.addDiscountToProduct] && BlPermissions.addProductToStore(sm.getStore(), product, amount);
	}

	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 */
	public static boolean deleteProductFromStore(StoreManager sm, Product product) {
		return sm.getPremisions()[BlPermissions.deleteProductFromStore] && BlPermissions.deleteProductFromStore(sm.getStore(), product);
	}

	/**
	 * update the product details , assumption the new product have same id like
	 * the old one
	 * 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount) {
		return sm.getPremisions()[BlPermissions.updateProductDetails] && BlPermissions.updateProductDetails(sm.getStore(), oldProduct, newProduct, amount);
	}

	/**
	 * add policy to product
	 * 
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) {
		return sm.getPremisions()[BlPermissions.addPolicyToProduct] && BlPermissions.addPolicyToProduct(sm.getStore(), policy, product);
	}

	/**
	 * add discount to product
	 * 
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 */
	public static boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount, Product product) {
		return sm.getPremisions()[BlPermissions.addDiscountToProduct] && BlPermissions.addDiscountToProduct(sm.getStore(), discount, product);
	}

	/**
	 * add new owner to store
	 * 
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public static boolean addNewStoreOwner(StoreManager sm, StoreOwner owner) {
		return sm.getPremisions()[BlPermissions.addNewStoreOwner] && BlPermissions.addNewStoreOwner(sm.getStore(), owner);
	}

	/**
	 * add new manager to store
	 * 
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public static boolean addNewManager(StoreManager oldMan, StoreManager newMan) {
		return oldMan.getPremisions()[BlPermissions.addNewManager] && BlPermissions.addNewManager(oldMan.getStore(), newMan);
	}

	/**
	 * @return true if the store close false otherwise
	 */
	public static boolean closeStore(StoreManager sm) {
		return sm.getPremisions()[BlPermissions.closeStore] && BlPermissions.closeStore(sm.getStore());
	}

	/**
	 * @return true if the store reopen false otherwise
	 */
	public static boolean openStore(StoreManager sm) {
		return sm.getPremisions()[BlPermissions.openStore] && BlPermissions.openStore(sm.getStore());
	}

	/**
	 * @return history of pruchase in the store
	 */
	public static List<Cart> getPurchaseHistory(StoreManager sm) {
		return sm.getPremisions()[BlPermissions.getPurchaseHistory] ? BlPermissions.getPurchaseHistory(sm.getStore()) : null;
	}
	
	public static void expiredProducts(StoreManager sm){
		if(sm.getPremisions()[BlPermissions.expiredProducts]) 
			BlPermissions.expiredProducts(sm.getStore());
	}
}
