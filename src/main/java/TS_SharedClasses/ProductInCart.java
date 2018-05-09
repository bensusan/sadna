package TS_SharedClasses;

import java.io.Serializable;

public class ProductInCart {

	private Product myProduct;
	private int price;
	private int amount;
	public ProductInCart(Product myProduct, int price, int amount) {
		super();
		this.myProduct = myProduct;
		this.price = price;
		this.amount = amount;
	}
	
	public Product getMyProduct() {
		return myProduct;
	}
	public void setMyProduct(Product myProduct) {
		this.myProduct = myProduct;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInCart other = (ProductInCart) obj;
		if (amount != other.amount)
			return false;
		if (myProduct == null) {
			if (other.myProduct != null)
				return false;
		} else if (!myProduct.equals(other.myProduct))
			return false;
		if (price != other.price)
			return false;
		return true;
	}
}
