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
	@Override
	public int updatePriceProduct(int price, int amount, String address, int discountCode) throws Exception {
		if(isCorrectProduct(amount, address))
		{
			if (getCurrDiscount()!=null)
				return this.getCurrDiscount().updatePrice(price, discountCode);
		}
		return price;
	}
	@Override
	public boolean isCorrectProduct(int amount, String address) {
		return (amount<=this.max);
	}

}
