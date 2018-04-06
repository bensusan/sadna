package main;

import java.sql.Date;

public class HiddenDiscount extends DiscountPolicy{
	private int code;
	
	public HiddenDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HiddenDiscount(Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
		// TODO Auto-generated constructor stub
	}

	public HiddenDiscount(int code,Date dicountEndDate, int discountPrecentage) {
		super(dicountEndDate, discountPrecentage);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @param price
	 * @return the price 
	 */
	public int updatePrice(int price)
	{
		//TODO missing implementation
		return 0;
	}
	

	/**
	 * @param price
	 * @return the price after discount if the code correct
	 */
	public int updatePrice(int price,int code)
	{
		//TODO missing implementation
		return 0;
	}
	

}
