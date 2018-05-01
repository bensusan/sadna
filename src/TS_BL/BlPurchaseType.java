package TS_BL;

import TS_SharedClasses.*;

public class BlPurchaseType {

	static boolean purchase(ProductInCart pic, Guest g){
		return pic.getMyProduct().getPurchasePolicy().getPurchaseType().purchase(g, pic);
	}
}
