package TS_BL;

import TS_SharedClasses.*;

public class BlPurchaseType {

	static boolean purchase(PurchaseType pt, Guest g, int price, int amount){
		return pt != null && pt.purchase(g, price, amount);
	}
}
