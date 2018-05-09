package TS_SharedClasses;

public abstract class PurchasePolicy {
	
	private DiscountPolicy discount;

	public PurchasePolicy(DiscountPolicy discount)
	{
		this.discount=discount;
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
		
	public abstract int updatePriceProduct(Product p, int amount, String address, int discountCode) throws Exception;
	
	
}
