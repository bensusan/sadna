package TS_SharedClasses;

public class MinPolicy extends PurchasePolicy {

	private int min;
	public MinPolicy(DiscountPolicy discount,int min) {
		super(discount);
		this.setMin(min);
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}

}
