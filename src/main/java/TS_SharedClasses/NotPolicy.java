package TS_SharedClasses;



public class NotPolicy extends PurchasePolicy {

	private PurchasePolicy subPolicy;
	public NotPolicy(DiscountPolicy discount,PurchasePolicy subPolicy) {
		super(discount);
		this.setSubPolicy(subPolicy);
	}
	public PurchasePolicy getSubPolicy() {
		return subPolicy;
	}
	public void setSubPolicy(PurchasePolicy subPolicy) {
		this.subPolicy = subPolicy;
	}
	@Override
	public boolean isCorrectProduct(int amount, String address) throws Exception {
		if(subPolicy==null)
			throw new Exception("");
		return !subPolicy.isCorrectProduct(amount, address);
	}
	@Override
	public int updatePriceProduct(int price, int amount, String address, int discountCode) throws Exception {
		if(isCorrectProduct(amount, address)&&this.getCurrDiscount()!=null)
		{
			if (getCurrDiscount()!=null)
				return this.getCurrDiscount().updatePrice(price, discountCode);
		}
		return price;
	}

}
