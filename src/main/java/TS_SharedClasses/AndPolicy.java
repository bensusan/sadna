package TS_SharedClasses;

import java.util.List;

public class AndPolicy extends PurchasePolicy {
	
	private List<PurchasePolicy> subPolicy;
	
	public AndPolicy(DiscountPolicy discount,List<PurchasePolicy> subPolicy) {
		super(discount);
		this.setSubPolicy(subPolicy);
	}

	public List<PurchasePolicy> getSubPolicy() {
		return subPolicy;
	}

	public void setSubPolicy(List<PurchasePolicy> subPolicy) {
		this.subPolicy = subPolicy;
	}

}
