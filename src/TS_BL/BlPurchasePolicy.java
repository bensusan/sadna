package TS_BL;

import TS_SharedClasses.*;

public class BlPurchasePolicy {

	static boolean purchase(PurchasePolicy pp, Guest g, int price, int amount) {
		return canPurchase(pp, g) && BlMain.purchase(pp.getPurchaseType(), g, price, amount);
	}
	
	private static boolean canPurchase(PurchasePolicy pp, Guest g) {
		//TODO
		return pp != null && g != null;
	}
	
	//From here methods that define the rules. for example minimum number of products per order.
	//TODO
	
}
