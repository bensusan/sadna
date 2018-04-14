package TS_SharedClasses;

import java.sql.Date;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
	}

	@Override
	public int updatePrice(int price, int code) {
		// TODO Auto-generated method stub
		return 0;
	}
}
