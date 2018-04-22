package TS_SharedClasses;

import java.sql.Date;

import TS_BL.BlHiddenDiscount;

public class HiddenDiscount extends DiscountPolicy{
	private int code;

	public HiddenDiscount(int code,Date discountEndDate, int discountPrecentage) {
		super(discountEndDate, discountPrecentage);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public int updatePrice(int price, int code) {
		return BlHiddenDiscount.updatePrice(this, price, code);
	}

	@Override
	public int updatePrice(int price) {
		return -1; //can't update price in hidden discount without any code
	}

}
