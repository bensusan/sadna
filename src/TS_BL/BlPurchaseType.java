package TS_BL;

import TS_SharedClasses.*;

public class BlPurchaseType {

	public static boolean purchase(PurchaseType pt, Guest g, int price, int amount){
		return pt.purchase(g, price, amount);
	}
}
