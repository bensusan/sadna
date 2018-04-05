package main;

public class PurchasePolicy {

	private PurchaseType purchaseType;
	
	public PurchasePolicy(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	public boolean purchase(Guest g, int price){
		//TODO
		return false;
	}
	
	private boolean canPurchase(Guest g){
		//TODO
		return false;
	}
	
	//From here methods that define the rules. for example minimum number of products per order.
	//TODO
}
