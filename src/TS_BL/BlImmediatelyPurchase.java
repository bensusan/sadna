package TS_BL;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase{

	//TODO
	//our responsibility to send here the price after the discount.
	static boolean purchase(ImmediatelyPurchase ip, Guest g, int price)
	{
		return ip != null && g != null && price > 0 && BlMain.payMoney(g, getDiscountedPrice(ip, price));
	}
	
	//Gets the original price, calculate and return the price after the discount.
	static int getDiscountedPrice(ImmediatelyPurchase ip, int price)
	{
		return (ip == null || price <= 0) ? -1 : price * ip.getDiscountPolicy().getDiscountPrecentage() / 100;
	}
}
