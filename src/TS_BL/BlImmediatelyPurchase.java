package TS_BL;

import TS_SharedClasses.*;

public class BlImmediatelyPurchase{

	//TODO
	//our responsibility to send here the price after the discount.
	public static boolean purchase(ImmediatelyPurchase ip, Guest g, int price)
	{
		int newPrice = getDiscountedPrice(ip, price);
		return BlMoneySystem.payMoney(g, newPrice);
	}
	
	//Gets the original price, calculate and return the price after the discount.
	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price)
	{
<<<<<<< HEAD
		return (ip.getDiscountPolicy().getDiscountPrecentage() / 100) * price;
=======
		return price * ip.getDiscountPolicy().getDiscountPrecentage() / 100;
>>>>>>> Ofir
	}
}
