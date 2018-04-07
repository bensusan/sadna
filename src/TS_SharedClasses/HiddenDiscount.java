package TS_SharedClasses;

import java.sql.Date;

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
}
