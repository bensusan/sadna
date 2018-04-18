package TS_BL;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase{

	//TODO
	//our responsibility to send here the price after the discount.
	public static boolean purchase(ImmediatelyPurchase ip, Guest g, int price)
	{
		int newPrice = getDiscountedPrice(ip, price);
		return BlMain.payMoney(g, newPrice);
	}
	
	//Gets the original price, calculate and return the price after the discount.
	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price)
	{
		return price * ip.getDiscountPolicy().getDiscountPrecentage() / 100;
	}
}
