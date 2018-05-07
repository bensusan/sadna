package TS_BL;

import java.util.Date;

import TS_SharedClasses.*;

public class BlHiddenDiscount {


	/**
	 * @param price
	 * @return the price after discount if the code correct
	 * @throws Exception 
	 */
	public static int updatePrice(HiddenDiscount hd, int price, int code) throws Exception {
		if(hd == null)
			throw new Exception("something went wrong");
		if(price < 0)
			throw new Exception("price must be a positive number");
		if (!checkCodeForDiscount(hd, code))
			throw new Exception("wrong coupon code");
		Date date = new Date();
		if (date.after(hd.getDiscountEndDate())){
			throw new Exception("the coupon has expired");
		}
		if(hd.getDiscountPrecentage() == 0)
			return price;
		return price - ((price * hd.getDiscountPrecentage()) / 100);
	}
	
	static boolean checkCodeForDiscount(HiddenDiscount hd, int code){
		return hd.getCode() == code;
	}
}
