package TS_BL;

import java.util.Date;

import TS_SharedClasses.*;

public class BlOvertDiscount {
	/**
	 * @param price
	 * @return the price after discount
	 */
	public static int updatePrice(OvertDiscount od, int price) {
		Date date = new Date();
		if (date.after(od.getDiscountEndDate())){
			return price;
		}
		return (price * od.getDiscountPrecentage()) / 100;
	}
}
