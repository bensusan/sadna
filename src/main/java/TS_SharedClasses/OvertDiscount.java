package TS_SharedClasses;

import java.io.Serializable;
import java.sql.Date;

import TS_BL.BlMain;
import TS_BL.BlOvertDiscount;

public class OvertDiscount extends DiscountPolicy {

	public OvertDiscount(Date dicountEndDate, int discountPrecentage) throws Exception {
		super(dicountEndDate, discountPrecentage);
	}
	
	public OvertDiscount() throws Exception {
		super();
	}

	//the code here doesn't mean anything
	@Override
	public int updatePrice(int price, int code) throws Exception {
		this.setDiscountEndDate(Date.valueOf("2020-01-01"));
		this.setDiscountPrecentage(0);
		return BlOvertDiscount.updatePrice(this, price);
	}

	
	
	
}
