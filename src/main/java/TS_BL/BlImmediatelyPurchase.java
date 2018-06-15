package TS_BL;

import static TS_BL.BlMain.dalRef;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase{

	public static boolean purchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic) throws Exception{
		Product p = pic.getMyProduct();
		int currentAmount = dalRef.getAmountOfProduct(pic.getMyProduct());
		return BlStore.stockUpdate(p, currentAmount - pic.getAmount());
	}

	public static void undoPurchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic) throws Exception {
		Product p = pic.getMyProduct();
		int currentAmount = dalRef.getAmountOfProduct(pic.getMyProduct());
		BlStore.stockUpdate(p, currentAmount + pic.getAmount());
	}
	
}
