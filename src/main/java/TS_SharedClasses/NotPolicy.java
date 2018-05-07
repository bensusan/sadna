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

}
