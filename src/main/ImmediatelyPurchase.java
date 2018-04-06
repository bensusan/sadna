package main;

public class ImmediatelyPurchase implements PurchaseType {

	private DiscountPolicy discountPolicy = null;
	public ImmediatelyPurchase(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
		// TODO Auto-generated constructor stub
	}

	//our responsibility to send here the price after the discount.
	public boolean purchase(Guest g, int price){
		//TODO
		return false;
	}

	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
	}
	
	//Gets the original price, calculate and return the price after the discount.
	public int getDiscountedPrice(int price){
		//TODO
		return 0;
	}
}
