package TS_SharedClasses;

import java.sql.Date;

import TS_BL.BlOvertDiscount;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
	}

	//the code here doesn't mean anything
	@Override
	public int updatePrice(int price, int code) {
		return BlOvertDiscount.updatePrice(this, price);
	}
}
