package TS_BL;

import TS_SharedClasses.*;

public class BlProduct {

	public static boolean purchase(Product p, Guest g, int price, int amount) {
<<<<<<< HEAD
		return BlMain.purchase(p.getPurchasePolicy(), g, price, amount) && BlMain.buyProduct(p.getStore(), p, amount) && BlMain.payToStore(p.getStore(), price);
=======
		PurchasePolicy policy = p.getStore().getStorePolicy();
		if(policy == null)
			policy = p.getPurchasePolicy();
		return BlMain.purchase(policy , g, price, amount) && BlMain.buyProduct(p.getStore(), p, amount) && BlMain.payToStore(p.getStore(), price);
>>>>>>> 3f69e1fd2e7b78391fde25f8d0372d2c0c929ae4
	}
}
