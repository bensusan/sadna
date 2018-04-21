package TS_BL;

import java.util.Date;

import TS_SharedClasses.*;

public class BlHiddenDiscount {

	/**
	 * @param price
	 * @return the price
	 */
	public static int updatePrice(HiddenDiscount hd, int price) {
		// TODO missing implementation
		return 0;
	}

	/**
	 * @param price
	 * @return the price after discount if the code correct
	 */
	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		if(hd == null || price < 0)
			return -1;
		if (!checkCodeForDiscount(hd, code))
			return -1;
		Date date = new Date();
		if (date.after(hd.getDiscountEndDate())){
			return -1;
		}
		return (price * hd.getDiscountPrecentage()) / 100;
	}
	
	public static boolean checkCodeForDiscount(HiddenDiscount hd, int code){
		return hd.getCode() == code;
	}
}
