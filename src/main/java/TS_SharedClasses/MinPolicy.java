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
	@Override
	public int updatePriceProduct(Product p, int amount, Guest guest, int discountCode) throws Exception {
		if(isCorrectProduct(amount, guest))
		{
			return this.getCurrDiscount().updatePrice(p.getPrice(), discountCode);
		}
		return p.getPrice();
	}
	@Override
	public boolean isCorrectProduct( int amount, Guest guest) {
		return amount>=this.min;
	}

}
