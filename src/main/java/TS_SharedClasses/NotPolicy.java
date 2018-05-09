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
	public int updatePriceProduct(Product p, int amount, String address, int discountCode) throws Exception {
		if(isCorrectProduct(amount, address)&&this.getCurrDiscount()!=null)
		{
			return this.getCurrDiscount().updatePrice(p.getPrice(), discountCode);
		}
		return p.getPrice();
	}

}
