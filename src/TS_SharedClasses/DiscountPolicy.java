package TS_SharedClasses;

import java.sql.Date;

public abstract class DiscountPolicy {
	private Date discountEndDate;
	private int discountPrecentage;
	
	public DiscountPolicy(Date discountEndDate, int discountPrecentage) {
		super();
		this.discountEndDate = discountEndDate;
		this.discountPrecentage = discountPrecentage;
	}
	
	public Date getDiscountEndDate() {
		return discountEndDate;
	}

	public void setDiscountEndDate(Date discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	public int getDiscountPrecentage() {
		return discountPrecentage;
	}

	public void setDiscountPrecentage(int discountPrecentage) {
		this.discountPrecentage = discountPrecentage;
	}
	
	public abstract int updatePrice(int price, int code);
	
}
