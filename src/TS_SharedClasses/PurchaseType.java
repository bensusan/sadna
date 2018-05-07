package TS_SharedClasses;

public interface PurchaseType {

	void undoPurchase(ProductInCart pic, Guest g) throws Exception;

	boolean purchase(Guest g, ProductInCart pic, String buyerAddress) throws Exception;
	
}
