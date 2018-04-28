package TS_SharedClasses;

import TS_BL.BlMain;

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
			bool = BlMain.purchase(this, g, price);
			if(!bool)
				return bool;
		}
		return bool;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmediatelyPurchase other = (ImmediatelyPurchase) obj;
		if (discountPolicy == null) {
			if (other.discountPolicy != null)
				return false;
		} else if (!discountPolicy.equals(other.discountPolicy))
			return false;
		return true;
	}
	
	
	
}
