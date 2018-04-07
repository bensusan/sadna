package TS_BL;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase extends BlPurchaseType{

	//our responsibility to send here the price after the discount.
	public boolean purchase(PurchaseType ip, Guest g, int price)
	{
		//TODO
		return false;
	}
	
	//Gets the original price, calculate and return the price after the discount.
	public int getDiscountedPrice(ImmediatelyPurchase ip, int price)
	{
		//TODO
		return 0;
	}
}
