package TS_SharedClasses;

public class MinPolicy extends PurchasePolicy {

	private int min;
	public MinPolicy(DiscountPolicy discount,int min) throws Exception {
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
	public int updatePriceProduct(int price, int amount, String address, int discountCode) throws Exception {
		if(isCorrectProduct(amount, address))
		{
			if (getCurrDiscount()!=null)
				return this.getCurrDiscount().updatePrice(price, discountCode);
		}
		return price;
	}
	@Override
	public boolean isCorrectProduct( int amount, String address) {
		return amount>=this.min;
	}
	public String toString(){
		String toRet = "";
		
		toRet = "Min Policy: " + this.min;
			
		return toRet;
	
	}
}
