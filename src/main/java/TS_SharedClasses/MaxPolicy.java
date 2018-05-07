package TS_SharedClasses;

public class MaxPolicy extends PurchasePolicy {

	private int max;
	public MaxPolicy(DiscountPolicy discount,int max) {
		super(discount);
		this.setMax(max);
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}

}
