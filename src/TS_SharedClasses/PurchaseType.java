package TS_SharedClasses;

public interface PurchaseType {

	public void undoPurchase(ProductInCart pic, Guest g);

	public boolean purchase(Guest g, ProductInCart pic, String buyerAddress);
	
}
