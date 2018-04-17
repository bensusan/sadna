package TS_BL;

import TS_SharedClasses.*;

public class BlAMIT {

	//add Store field - also add set + get + edit constructor
	private PurchasePolicy storePolicy;

	//add permission in BlMain
	final int changeStorePurchasePolicy = 11;

	//for BlPermission
	public static boolean changeStorePurchasePolicy(Store s, PurchasePolicy pp){
		s.setStorePolicy(pp);
		return true;
	}

	//for BlStoreOwner
	public static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp){
		return BlPermissions.changeStorePurchasePolicy(so.getStore(), pp);
	}

	//for BlStoreManager
	public static boolean changeStorePurchasePolicy(StoreManager sm, PurchasePolicy pp){
		return sm.getPremisions()[BlMain.changeStorePurchasePolicy] && BlPermissions.changeStorePurchasePolicy(sm.getStore(), pp);
	}

	//for BlMain
	public static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp){
		return BlStoreOwner.changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp);
	}

	//for BlMain
	public static boolean changeStorePurchasePolicy(StoreManager sm, PurchasePolicy pp){
		return BlStoreManager.changeStorePurchasePolicy(StoreManager so, PurchasePolicy pp);
	}

	//override BlProduct
	public static boolean purchase(Product p, Guest g, int price, int amount) {
		Cart newCart = new Cart();
		PurchasePolicy policy = p.getStore().getStorePolicy();
		if(policy == null)
			policy = p.getPurchasePolicy();
		return BlMain.purchase(policy , g, price, amount) && BlMain.buyProduct(p.getStore(), p, amount) && BlMain.payToStore(p.getStore(), price) && BlMain.addProduct(newCart, p, amount) && BlMain.addPurchaseToHistory(p.getStore(), newCart);
	}
}
