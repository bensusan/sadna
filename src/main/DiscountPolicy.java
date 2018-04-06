package main;

import java.sql.Date;

public abstract class DiscountPolicy {
	private Date dicountEndDate;
	private int discountPrecentage;

	public DiscountPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiscountPolicy(Date dicountEndDate, int discountPrecentage) {
		super();
		this.dicountEndDate = dicountEndDate;
		this.discountPrecentage = discountPrecentage;
	}
	
	
	public Date getDicountEndDate() {
		return dicountEndDate;
	}

	public void setDicountEndDate(Date dicountEndDate) {
		this.dicountEndDate = dicountEndDate;
	}

	public int getDiscountPrecentage() {
		return discountPrecentage;
	}

	public void setDiscountPrecentage(int discountPrecentage) {
		this.discountPrecentage = discountPrecentage;
	}
	/**
	 * @param price
	 * @return the price after discount
	 */
	public int updatePrice(int price)
	{
		//TODO missing implementation
		return 0;
	}



}
