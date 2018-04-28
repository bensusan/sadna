package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlStoreManager {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 */
	static boolean addProductToStore(StoreManager sm, Product product, int amount) {
		return sm != null && sm.getPremisions()[BlMain.addProductToStore] && BlPermissions.addProductToStore(sm.getStore(), product, amount);
	}

	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 */
	static boolean deleteProductFromStore(StoreManager sm, Product product) {
		return sm != null && sm.getPremisions()[BlMain.deleteProductFromStore] && BlPermissions.deleteProductFromStore(sm.getStore(), product);
	}

	/**
	 * update the product details , assumption the new product have same id like
	 * the old one
	 * 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount) {
		return sm != null && sm.getPremisions()[BlMain.updateProductDetails] && BlPermissions.updateProductDetails(sm.getStore(), oldProduct, newProduct, amount);
	}

	/**
	 * add policy to product
	 * 
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 */
	static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) {
		return sm != null && sm.getPremisions()[BlMain.addPolicyToProduct] && BlPermissions.addPolicyToProduct(sm.getStore(), policy, product);
	}

	/**
	 * add discount to product
	 * 
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 */
	static boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount, Product product) {
		return sm != null && sm.getPremisions()[BlMain.addDiscountToProduct] && BlPermissions.addDiscountToProduct(sm.getStore(), discount, product);
	}

	/**
	 * add new owner to store
	 * 
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	static boolean addNewStoreOwner(StoreManager sm, StoreOwner owner) {
		return sm != null && sm.getPremisions()[BlMain.addNewStoreOwner] && BlPermissions.addNewStoreOwner(sm.getStore(), owner);
	}

	/**
	 * add new manager to store
	 * 
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	static boolean addNewManager(StoreManager oldMan, StoreManager newMan) {
		return oldMan != null && oldMan.getPremisions()[BlMain.addNewManager] && BlPermissions.addNewManager(oldMan.getStore(), newMan);
	}

	/**
	 * @return true if the store close false otherwise
	 */
	static boolean closeStore(StoreManager sm) {
		return sm != null && sm.getPremisions()[BlMain.closeStore] && BlPermissions.closeStore(sm.getStore());
	}

	/**
	 * @return true if the store reopen false otherwise
	 */
	static boolean openStore(StoreManager sm) {
		return sm != null && sm.getPremisions()[BlMain.openStore] && BlPermissions.openStore(sm.getStore());
	}

	/**
	 * @return history of pruchase in the store
	 */
	static List<Purchase> getPurchaseHistory(StoreManager sm) {
		return sm != null && sm.getPremisions()[BlMain.getPurchaseHistory] ? BlPermissions.getPurchaseHistory(sm.getStore()) : null;
	}
	
	static void expiredProducts(StoreManager sm){
		if(sm != null && sm.getPremisions()[BlMain.expiredProducts]) 
			BlPermissions.expiredProducts(sm.getStore());
	}
	
	static boolean changeStorePurchasePolicy(StoreManager sm, PurchasePolicy pp){
		return sm != null && sm.getPremisions()[BlMain.changeStorePurchasePolicy] && BlPermissions.changeStorePurchasePolicy(sm.getStore(), pp);
	}
}
