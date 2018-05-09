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

	@Override
	public int updatePriceProduct(Product p, int amount, Guest guest, int discountCode) throws Exception {
		if(isCorrectProduct(amount,guest))
		{
			if(this.getCurrDiscount()!=null){
				return this.getCurrDiscount().updatePrice(p.getPrice(), discountCode);
			}
			else{
				int minPrice=p.getPrice();
				for(PurchasePolicy pol:subPolicy){
					int cur=pol.updatePriceProduct(p, amount, guest, discountCode);
					if(minPrice>cur)
						minPrice=cur;
				}
				return minPrice;
			}
		}
		return p.getPrice();
	}

	@Override
	public boolean isCorrectProduct(int amount, Guest guest) throws Exception {
		if(subPolicy.isEmpty()||subPolicy==null)
			throw new Exception("");
		boolean ans=true;
		for(PurchasePolicy pol:subPolicy){
			ans=ans&&pol.isCorrectProduct(amount, guest);
		}
		return ans;
	}

}
