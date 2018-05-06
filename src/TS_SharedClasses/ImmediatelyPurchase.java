package TS_SharedClasses;

import TS_BL.BlImmediatelyPurchase;

public class ImmediatelyPurchase implements PurchaseType {

	private DiscountPolicy discountPolicy;

	public ImmediatelyPurchase() throws Exception {
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

	public boolean purchase(Guest g, ProductInCart pic,String buyerAddress) {
		return BlImmediatelyPurchase.purchase(this, g, pic);
	}
	
	public void undoPurchase(ProductInCart pic, Guest g) {
		BlImmediatelyPurchase.undoPurchase(this, g, pic);
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
