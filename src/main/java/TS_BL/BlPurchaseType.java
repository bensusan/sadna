package TS_BL;

import TS_SharedClasses.*;

public class BlPurchaseType {

	static boolean purchase(ProductInCart pic, Guest g, String buyerAddress) throws Exception{
		PurchaseType pt = pic.getMyProduct().getType();
		Product p = pic.getMyProduct();
		int amount = pic.getAmount();
		return BlStore.checkInStock(p, amount) && pt.purchase(g, pic,buyerAddress);
	}

	public static void undoPurchase(ProductInCart pic, Guest g) throws Exception {
		pic.getMyProduct().getType().undoPurchase(pic, g);
		
	}
}
