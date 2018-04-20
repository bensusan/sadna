package TS_SharedClasses;

import java.util.Date;

public class Purchase {

	//the purchase object
	private Date whenPurchased;
	private int purchaseID;
	private Cart purchased;

	public Purchase(Date date, int purchaseID, Cart purchased){
		this.whenPurchased = date;
		this.purchaseID = purchaseID;
		this.purchased = purchased;
	}


	public int getPurchaseID() {
		return purchaseID;
	}

	public void setPurchaseID(int purchaseID) {
		this.purchaseID = purchaseID;
	}

	public Date getWhenPurchased() {
		return whenPurchased;
	}

	public void setWhenPurchased(Date whenPurchased) {
		this.whenPurchased = whenPurchased;
	}

	public Cart getPurchased() {
		return purchased;
	}

	public void setPurchased(Cart purchased) {
		this.purchased = purchased;
	}

}
