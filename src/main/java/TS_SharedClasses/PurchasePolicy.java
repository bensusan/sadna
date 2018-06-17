package TS_SharedClasses;

import TS_BL.BlMain;

public abstract class PurchasePolicy {

//	private static int nextID = 0;
	private int policyId;
	private DiscountPolicy discount;

	public PurchasePolicy(DiscountPolicy discount) throws Exception
	{
		policyId = /*nextID ++;*/ BlMain.dalRef.getNextPolicyId();
		this.discount=discount;
	}

	//WARNING FOR DAL!!!!!!
	public PurchasePolicy(int policyId, DiscountPolicy discount) {
		super();
		this.policyId = policyId;
		this.discount = discount;
	}

	
	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public void setDiscount(DiscountPolicy discount) {
		this.discount = discount;
	}

	public DiscountPolicy getCurrDiscount() {
		return this.discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchasePolicy other = (PurchasePolicy) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		return true;
	}
	public abstract boolean isCorrectProduct( int amount, String address) throws Exception ;
		
	public abstract int updatePriceProduct(int price, int amount, String address, int discountCode) throws Exception;
	
	
}
