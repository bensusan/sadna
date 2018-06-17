package TS_SharedClasses;

import java.io.Serializable;
import java.util.Date;

import TS_BL.BlMain;

public class Purchase {

//	private static int nextID = 0;
	//the purchase object
	private Date whenPurchased;
	private int purchaseID;
	private ProductInCart purchased;

	public Purchase(Date date, ProductInCart purchased) throws Exception{
		this.whenPurchased = date;
		this.purchaseID = /*nextID++;*/ BlMain.dalRef.getNextPurchaseId();
		this.purchased = purchased;
	}
	
	//WARNING FOR DAL!!!!!!!
	public Purchase(Date whenPurchased, int purchaseID, ProductInCart purchased) {
		super();
		this.whenPurchased = whenPurchased;
		this.purchaseID = purchaseID;
		this.purchased = purchased;
	}

	public int getPurchaseID() {
		return purchaseID;
	}

	public Date getWhenPurchased() {
		return whenPurchased;
	}

	public void setWhenPurchased(Date whenPurchased) {
		this.whenPurchased = whenPurchased;
	}

	public ProductInCart getPurchased() {
		return purchased;
	}

	public void setPurchased(ProductInCart purchased) {
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
		return this.purchaseID == other.purchaseID;
//		if (purchaseID != other.purchaseID)
//			return false;
//		if (purchased == null) {
//			if (other.purchased != null)
//				return false;
//		} else if (!purchased.equals(other.purchased))
//			return false;
//		if (whenPurchased == null) {
//			if (other.whenPurchased != null)
//				return false;
//		} else if (!whenPurchased.equals(other.whenPurchased))
//			return false;
//		return true;
	}

	
}
