package TS_BL;

import TS_SharedClasses.*;

public class BlProduct {

	static boolean purchase(ProductInCart pic, Guest g) {
		return BlPurchasePolicy.purchase(pic, g);
	}
}
