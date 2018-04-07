package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlPermissions {

	final static int 
	addProductToStore = 0,
	deleteProductFromStore = 1,
	updateProductDetails = 2,
	addPolicyToProduct = 3,
	addDiscountToProduct = 4,
	addNewStoreOwner = 5,
	addNewManager = 6,
	closeStore = 7,
	openStore = 8,
	getPurchaseHistory = 9;

	//Here we will implement Store's owner and Store's manager permissions
	public static boolean addProductToStore(Store s, Product product, int amount) {
		if(s.getProducts().get(product) != null)
			return s.getProducts().put(product, s.getProducts().get(product)+ amount) != null;
		return s.getProducts().put(product, amount) != null;
	}


	public static boolean deleteProductFromStore(Store s, Product product) {
		return s.getProducts().remove(product) != null;
	}


	public static boolean updateProductDetails(Store s, Product newProduct, int amount) {
		return s.getProducts().put(newProduct, amount) != null;
	}


	public static boolean addPolicyToProduct(Store s, PurchasePolicy policy, Product product) {
		if(s.getProducts().get(product) == null)
			return false;
		product.setPurchasePolicy(policy);
		return true;
	}


	public static boolean addDiscountToProduct(Store s, DiscountPolicy discount, Product product) {
		if(s.getProducts().get(product) == null)
			return false;
		PurchaseType pt = product.getPurchasePolicy().getPurchaseType(); 
		if(pt instanceof ImmediatelyPurchase){
			((ImmediatelyPurchase) pt).setDiscountPolicy(discount);
			return true;
		}
		return false;
	}


	public static boolean addNewStoreOwner(Store s, StoreOwner owner) {
		List<StoreOwner> owners = s.getMyOwners();
		if(!owners.add(owner))
			return false;
		s.setMyOwners(owners);
		owner.setStore(s);
		return true;
	}


	public static boolean addNewManager(Store s, StoreManager newMan) {
		List<StoreManager> managers = s.getMyManagers();
		if(!managers.add(newMan))
			return false;
		s.setMyManagers(managers);
		newMan.setStore(s);
		return true;
	}


	public static boolean closeStore(Store s) {
		// TODO Auto-generated method stub
		return false;
	}


	public static boolean openStore(Store s) {
		// TODO Auto-generated method stub
		return false;
	}


	public static List<Cart> getPurchaseHistory(Store s) {
		// TODO Auto-generated method stub
		return null;
	}


}
