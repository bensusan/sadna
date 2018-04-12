package TS_SharedClasses;

public class PurchasePolicy {

	private PurchaseType purchaseType;
	
	public PurchasePolicy(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}
}
