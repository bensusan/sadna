package TS_SharedClasses;

import java.sql.Date;

import TS_BL.BlHiddenDiscount;
import TS_BL.BlMain;

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
	public int updatePrice(int price, int code) throws Exception {
		return BlHiddenDiscount.updatePrice(this, price, code);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HiddenDiscount other = (HiddenDiscount) obj;
		if (code != other.code)
			return false;
		return true;
	}
	
	

}
