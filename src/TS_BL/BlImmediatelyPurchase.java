package TS_BL;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase{

	public static boolean purchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic){
		Product p = pic.getMyProduct();
		int currentAmount = p.getStore().getProducts().get(p);
		return BlStore.stockUpdate(p, currentAmount - pic.getAmount());
	}
	
}
