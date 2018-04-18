package TS_SharedClasses;

import java.sql.Date;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
	}
	
	public OvertDiscount() {
		super(new Date(8099, 11, 31), 0);
	}

	@Override
	public int updatePrice(int price, int code) {
		// TODO Auto-generated method stub
		return 0;
	}
}
