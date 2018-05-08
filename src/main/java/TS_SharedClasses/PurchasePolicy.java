package TS_SharedClasses;

import java.io.Serializable;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((purchaseType == null) ? 0 : purchaseType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchasePolicy other = (PurchasePolicy) obj;
		if (purchaseType == null) {
			if (other.purchaseType != null)
				return false;
		} else if (!purchaseType.equals(other.purchaseType))
			return false;
		return true;
	}
	
	
}
