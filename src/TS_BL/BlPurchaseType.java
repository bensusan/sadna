package TS_BL;

import TS_SharedClasses.*;

public class BlPurchaseType {

	static boolean purchase(ProductInCart pic, Guest g){
		PurchaseType pt = pic.getMyProduct().getPurchasePolicy().getPurchaseType();
		Product p = pic.getMyProduct();
		int amount = pic.getAmount();
		return BlStore.checkInStock(p, amount) && pt.purchase(g, pic);
	}

	public static void undoPurchase(ProductInCart pic, Guest g) {
		pic.getMyProduct().getPurchasePolicy().getPurchaseType().undoPurchase(pic, g);
		
	}
}
