package TS_BL;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase{

	public static boolean purchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic){
	public static boolean purchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic) throws Exception{
		Product p = pic.getMyProduct();
		int currentAmount = p.getStore().getProducts().get(p);
		return BlStore.stockUpdate(p, currentAmount - pic.getAmount());
	}

	public static void undoPurchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic) {
	public static void undoPurchase(ImmediatelyPurchase ip, Guest g, ProductInCart pic) throws Exception {
		Product p = pic.getMyProduct();
		int currentAmount = p.getStore().getProducts().get(p);
		BlStore.stockUpdate(p, currentAmount + pic.getAmount());
	}
	
}
