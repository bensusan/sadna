package TS_BL;

import java.util.Date;

import TS_SharedClasses.*;

public class BlOvertDiscount {
	/**
	 * @param price
	 * @return the price after discount
	 */
	public static int updatePrice(OvertDiscount od, int price) {
		if(od == null || price <= 0)
			return -1;
		Date date = new Date();
		if (date.after(od.getDiscountEndDate())){
			return -1;
		}
		if(od.getDiscountPrecentage() == 0)
			return price;
		return (price * od.getDiscountPrecentage()) / 100;
	}
}
