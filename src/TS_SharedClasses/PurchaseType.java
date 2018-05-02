package TS_SharedClasses;

public interface PurchaseType {

	boolean purchase(Guest g, ProductInCart pic);

	void undoPurchase(ProductInCart pic, Guest g);
	
}
