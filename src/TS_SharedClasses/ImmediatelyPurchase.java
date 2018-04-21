package TS_SharedClasses;

import TS_BL.BlImmediatelyPurchase;

public class ImmediatelyPurchase implements PurchaseType {

	private DiscountPolicy discountPolicy;

	public ImmediatelyPurchase() {
		super();
		discountPolicy = new OvertDiscount();
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

	@Override
	public boolean purchase(Guest g, int price, int amount) {
		if(amount <= 0)
			return false;
		boolean bool = true;
		for (int i = 0; i < amount; i++){
			bool = BlImmediatelyPurchase.purchase(this, g, price);
			if(!bool)
				return bool;
		}
		return bool;
	}
	
	
}
