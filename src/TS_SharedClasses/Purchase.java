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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		if (purchaseID != other.purchaseID)
			return false;
		if (purchased == null) {
			if (other.purchased != null)
				return false;
		} else if (!purchased.equals(other.purchased))
			return false;
		if (whenPurchased == null) {
			if (other.whenPurchased != null)
				return false;
		} else if (!whenPurchased.equals(other.whenPurchased))
			return false;
		return true;
	}

	
}
