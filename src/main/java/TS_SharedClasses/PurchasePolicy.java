package TS_SharedClasses;

public abstract class PurchasePolicy {
	
	private DiscountPolicy discount;

	public PurchasePolicy(DiscountPolicy discount)
	{
		this.discount=discount;
	}
	public void setPurchaseType(DiscountPolicy discount) {
		this.discount = discount;
	}

	public DiscountPolicy getPurchaseType() {
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
	
	
}
