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
		Date date = new Date();
		if (date.after(hd.getDiscountEndDate())){
			return price;
		}
		return (price * hd.getDiscountPrecentage()) / 100;
	}
}
