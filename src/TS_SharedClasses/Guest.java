package TS_SharedClasses;

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
}
