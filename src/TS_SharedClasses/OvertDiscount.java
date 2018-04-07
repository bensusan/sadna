package TS_SharedClasses;

import java.sql.Date;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
	}
}
