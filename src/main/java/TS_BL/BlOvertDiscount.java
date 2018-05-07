package TS_BL;

import java.util.Date;

import TS_SharedClasses.*;

public class BlOvertDiscount {
	/**
	 * @param price
	 * @return the price after discount
	 * @throws Exception 
	 */
	public static int updatePrice(OvertDiscount od, int price) throws Exception {
		if(od == null)
			throw new Exception("something went wrong");
		if(price <= 0)
			throw new Exception("price must be a positive number");
			
		Date date = new Date();
		if (date.after(od.getDiscountEndDate())){
			throw new Exception("discount end date has passed");
		}
		if(od.getDiscountPrecentage() == 0)
			return price;

		return (price * od.getDiscountPrecentage()) / 100;

	}
}
