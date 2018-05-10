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
	public int updatePriceProduct(int price, int amount, String address, int discountCode) throws Exception {
		if(isCorrectProduct(amount,address))
		{
			if(this.getCurrDiscount()!=null){
				return this.getCurrDiscount().updatePrice(price, discountCode);
			}
			else{
				int minPrice=price;
				for(PurchasePolicy pol:subPolicy){
					int cur=pol.updatePriceProduct(price, amount, address, discountCode);
					if(minPrice>cur)
						minPrice=cur;
				}
				return minPrice;
			}
		}
		return price;
	}

	@Override
	public boolean isCorrectProduct(int amount, String address) throws Exception {
		if(subPolicy.isEmpty()||subPolicy==null)
			throw new Exception("");
		boolean ans=true;
		for(PurchasePolicy pol:subPolicy){
			ans=ans&&pol.isCorrectProduct(amount, address);
		}
		return ans;
	}

}
