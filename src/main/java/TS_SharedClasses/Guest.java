package TS_SharedClasses;

import java.io.Serializable;

public class Guest {
	private Cart cart;
	
	public Guest(Cart cart) {
		super();
		this.cart = cart;
	}
		
	public Guest() {
		super();
		this.cart=new Cart();
	}
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guest other = (Guest) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		return true;
	}
	
	
}
