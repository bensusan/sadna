package TS_SharedClasses;

import java.sql.Date;

import TS_BL.BlMain;
import TS_BL.BlOvertDiscount;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
	}
	
	public OvertDiscount() {
		super(new Date(8099, 11, 31), 0);
	}

	//the code here doesn't mean anything
	@Override
	public int updatePrice(int price, int code) {
		if(code < 0)
			return -1;
		return BlOvertDiscount.updatePrice(this, price);
	}

	@Override
	public int updatePrice(int price) {
		return -1;
	}
	
	
}
