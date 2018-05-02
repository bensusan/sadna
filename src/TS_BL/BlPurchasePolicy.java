package TS_BL;

import TS_SharedClasses.*;

public class BlPurchasePolicy {

	static boolean purchase(ProductInCart pic, Guest g,String buyerAddress) {
		return BlPurchaseType.purchase(pic, g, buyerAddress);
	}
}
