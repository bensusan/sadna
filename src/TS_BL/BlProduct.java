package TS_BL;

import TS_SharedClasses.*;

public class BlProduct {

	public static boolean purchase(Product p, Guest g, int price, int amount) {
		Cart newCart = new Cart();
		return BlMain.purchase(p.getPurchasePolicy(), g, price, amount) && BlMain.buyProduct(p.getStore(), p, amount) && BlMain.payToStore(p.getStore(), price) && BlMain.addProduct(newCart, p, amount) && BlMain.addPurchaseToHistory(p.getStore(), newCart);
	}
}
