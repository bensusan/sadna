package TS_BL;

import TS_SharedClasses.*;

public class BlProduct {

	public static boolean purchase(Product p, Guest g, int price, int amount) {
		if(p == null || g == null || price <= 0 || amount < 1)
			return false;
		PurchasePolicy policy = null;
		if(p.getStore() != null)
			policy = p.getStore().getStorePolicy(); 
		if(policy == null)
			policy = p.getPurchasePolicy();
		return BlMain.purchase(policy , g, price, amount) && BlMain.buyProduct(p.getStore(), p, amount) && BlMain.payToStore(p.getStore(), price);
	}
}
