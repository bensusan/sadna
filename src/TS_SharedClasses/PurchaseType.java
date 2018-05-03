package TS_SharedClasses;

public interface PurchaseType {

	void undoPurchase(ProductInCart pic, Guest g);

	boolean purchase(Guest g, ProductInCart pic, String buyerAddress);
	
}
