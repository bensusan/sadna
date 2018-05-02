package TS_SharedClasses;

public interface PurchaseType {

<<<<<<< HEAD
	boolean purchase(Guest g, ProductInCart pic);

	void undoPurchase(ProductInCart pic, Guest g);
=======
	boolean purchase(Guest g, ProductInCart pic, String buyerAddress);
>>>>>>> Alex
	
}
