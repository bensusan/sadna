package TS_BL;

import TS_SharedClasses.*;

public class BlPurchasePolicy {

	static boolean purchase(ProductInCart pic, Guest g,String buyerAddress) {
	static boolean purchase(ProductInCart pic, Guest g,String buyerAddress) throws Exception {
		return BlPurchaseType.purchase(pic, g, buyerAddress);
	}
}
