package TS_SharedClasses;

import TS_BL.BlImmediatelyPurchase;

public class ImmediatelyPurchase implements PurchaseType {

	private PurchasePolicy discountTree;

	public ImmediatelyPurchase() throws Exception {
		super();
		discountTree = null;
	}

	public ImmediatelyPurchase(PurchasePolicy discountTree) {
		this.discountTree = discountTree;
	}
	
	public void setDiscountTree(PurchasePolicy discountTree) {
		this.discountTree = discountTree;
	}

	public PurchasePolicy getDiscountTree() {
		return discountTree;
	}

	public boolean purchase(Guest g, ProductInCart pic,String buyerAddress) throws Exception {
		return BlImmediatelyPurchase.purchase(this, g, pic);
	}
	
	public void undoPurchase(ProductInCart pic, Guest g) throws Exception {

		BlImmediatelyPurchase.undoPurchase(this, g, pic);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmediatelyPurchase other = (ImmediatelyPurchase) obj;
		if (discountTree == null) {
			if (other.discountTree != null)
				return false;
		} else if (!discountTree.equals(other.discountTree))
			return false;
		return true;
	}

	
	
	
	
}
