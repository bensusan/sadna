package TS_SharedClasses;

import java.io.Serializable;
import java.sql.Date;

public abstract class DiscountPolicy {
	private Date discountEndDate;
	private int discountPrecentage;
	
	public DiscountPolicy(Date discountEndDate, int discountPrecentage) throws Exception {
		super();
		if(discountPrecentage < 0 || discountPrecentage > 100)
			throw new Exception("Invalid Discount precentage.");
		this.discountEndDate = discountEndDate;
		
		this.discountPrecentage = discountPrecentage;
	}
	
	public Date getDiscountEndDate() {
		return discountEndDate;
	}

	public void setDiscountEndDate(Date discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	public int getDiscountPrecentage() {
		return discountPrecentage;
	}

	public void setDiscountPrecentage(int discountPrecentage) {
		this.discountPrecentage = discountPrecentage;
	}
	
	public abstract int updatePrice(int price, int code)  throws Exception;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiscountPolicy other = (DiscountPolicy) obj;
		if (discountEndDate == null) {
			if (other.discountEndDate != null)
				return false;
		} else if (!discountEndDate.equals(other.discountEndDate))
			return false;
		if (discountPrecentage != other.discountPrecentage)
			return false;
		return true;
	}
	
	
}
