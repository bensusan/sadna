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
	public int updatePriceProduct(Product p, int amount, Guest guest, int discountCode) throws Exception {
		if(isCorrectProduct(amount, guest))
		{
			return this.getCurrDiscount().updatePrice(p.getPrice(), discountCode);
		}
		return p.getPrice();
	}
	@Override
	public boolean isCorrectProduct(int amount, Guest guest) {
		return (amount<=this.max);
	}

}
