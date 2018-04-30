package TS_BL;

import TS_SharedClasses.*;

public class BlProduct {

	static boolean purchase(Product p, Guest g, int price, int amount) {
		if(p == null || g == null || price <= 0 || amount < 1)
			return false;
		PurchasePolicy policy = null;
		if(p.getStore() != null)
			policy = p.getStore().getStorePolicy(); 
		if(policy == null)
			policy = p.getPurchasePolicy();
		return g != null && BlPurchasePolicy.purchase(policy , g, price, amount) && BlStore.buyProduct(p.getStore(), p, amount) && BlStore.payToStore(p.getStore(), price);
	}
}
