package TS_SharedClasses;

import java.sql.Date;

import TS_BL.BlMain;
import TS_BL.BlOvertDiscount;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) throws Exception {
		super(dicountEndDate, discountPrecentage);
	}
	
	public OvertDiscount() throws Exception {
		super(new Date(8099, 11, 31), 0);
	}

	//the code here doesn't mean anything
	@Override
	public int updatePrice(int price, int code) throws Exception {
		return BlOvertDiscount.updatePrice(this, price);
	}

	
	
	
}
