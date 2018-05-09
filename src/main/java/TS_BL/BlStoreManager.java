package TS_BL;

import java.util.LinkedList;
import java.util.List;

import TS_SharedClasses.*;

public class BlStoreManager {

	/**
	 * @param product
	 * @param amount
	 * @return true if the add succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addProductToStore(StoreManager sm, Product product, int amount) throws Exception {
		return sm != null && sm.getPremisions()[BlMain.addProductToStore] && BlPermissions.addProductToStore(sm.getStore(), product, amount);
	}

	/**
	 * @param product
	 * @return true if the delete succseed false otherwise
	 * @throws Exception 
	 */
	static boolean deleteProductFromStore(StoreManager sm, Product product) throws Exception {
		return sm != null && sm.getPremisions()[BlMain.deleteProductFromStore] && BlPermissions.deleteProductFromStore(sm.getStore(), product);
	}

	/**
	 * update the product details , assumption the new product have same id like
	 * the old one
	 * 
	 * @param newProduct
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount) throws Exception {
		return sm != null && sm.getPremisions()[BlMain.updateProductDetails] && BlPermissions.updateProductDetails(sm.getStore(), oldProduct, newProduct, amount);
	}

	/**
	 * add policy to product
	 * 
	 * @param policy
	 * @param product
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) throws Exception {
		return sm != null && sm.getPremisions()[BlMain.addPolicyToProduct] && BlPermissions.addPolicyToProduct(sm.getStore(), policy, product);
	}

	/**
	 * add discount to product
	 * 
	 * @param discount
	 * @param product
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addDiscountToProduct(StoreManager sm, PurchasePolicy discountTree, Product product) throws Exception {
		return sm != null && sm.getPremisions()[BlMain.addDiscountToProduct] && BlPermissions.addDiscountToProduct(sm.getStore(), discountTree, product);
	}

	/**
	 * add new owner to store
	 * 
	 * @param owner
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addNewStoreOwner(StoreManager sm, Subscriber owner) throws Exception {
		return sm != null && sm.getPremisions()[BlMain.addNewStoreOwner] && BlPermissions.addNewStoreOwner(sm.getStore(), owner);
	}

	/**
	 * add new manager to store
	 * 
	 * @param manager
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addNewManager(StoreManager oldMan, Subscriber newMan) throws Exception {
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
		return sm != null && sm.getPremisions()[BlMain.getPurchaseHistory] ? BlPermissions.getPurchaseHistory(sm.getStore()) : new LinkedList<Purchase>();
	}
	
	static void expiredProducts(StoreManager sm){
		if(sm != null && sm.getPremisions()[BlMain.expiredProducts]) 
			BlPermissions.expiredProducts(sm.getStore());
	}
	
	static boolean changeStorePurchasePolicy(StoreManager sm, PurchasePolicy pp) throws Exception{
		return sm != null && sm.getPremisions()[BlMain.changeStorePurchasePolicy] && BlPermissions.changeStorePurchasePolicy(sm.getStore(), pp);
	}
}
