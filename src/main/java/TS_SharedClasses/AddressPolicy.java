package TS_SharedClasses;

public class AddressPolicy extends PurchasePolicy {

	private String address;
	public AddressPolicy(DiscountPolicy discount,String address) {
		super(discount);
		this.address=address;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public boolean isCorrectProduct(int amount, String address) throws Exception {
		return address.contains(this.address);
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
	
	public String toString(){
		String toRet = "";
		
		toRet = "Address Policy: " + this.address;
			
		return toRet;
	
	}

}
