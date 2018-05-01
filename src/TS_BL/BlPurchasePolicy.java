package TS_BL;

import TS_SharedClasses.*;

public class BlPurchasePolicy {

	static boolean purchase(ProductInCart pic, Guest g) {
		return BlPurchaseType.purchase(pic, g);
	}
}
