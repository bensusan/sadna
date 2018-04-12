package TS_SharedClasses;

public class ImmediatelyPurchase implements PurchaseType {

	private DiscountPolicy discountPolicy;

	public ImmediatelyPurchase() {
		super();
		discountPolicy = null;
	}

	public ImmediatelyPurchase(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
		// TODO Auto-generated constructor stub
	}
	
	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
	}

	public DiscountPolicy getDiscountPolicy() {
		return discountPolicy;
	}
	
	
}
