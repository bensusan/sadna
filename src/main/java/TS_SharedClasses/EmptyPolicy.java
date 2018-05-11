package TS_SharedClasses;

public class EmptyPolicy extends PurchasePolicy {

	public EmptyPolicy(DiscountPolicy discount) {
		super(discount);
	}
	public EmptyPolicy() {
		super(null);
	}

	@Override
	public boolean isCorrectProduct(int amount, String address) throws Exception {
		return true;
	}

	@Override
	public int updatePriceProduct(int price, int amount, String address, int discountCode) throws Exception {
		if (getCurrDiscount()==null)
			return price;
		return getCurrDiscount().updatePrice(price, discountCode);
	}

}
