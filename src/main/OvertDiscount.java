package main;

import java.sql.Date;

public class OvertDiscount extends DiscountPolicy {
	
	public OvertDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param price
	 * @return the price after discount
	 */
	public int updatePrice(int price)
	{
		//TODO missing implementation
		return 0;
	}
	

}
