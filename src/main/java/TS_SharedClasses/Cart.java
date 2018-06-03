package TS_SharedClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TS_BL.BlMain;

public class Cart  {

	private List<ProductInCart> products;
	
	public Cart()
	{
		this.products=new LinkedList<ProductInCart>();
	}
	
	public Cart(List<ProductInCart> products) {
		super();
		this.products = products;
		if(products == null)
			this.products = new LinkedList<ProductInCart>();
	}
	
	public List<ProductInCart> getProducts() {
		return this.products;
	}
	
	public void setProducts(List<ProductInCart> products) {
		this.products = products;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if(!BlMain.equalsLists(products, other.products))
			return false;
		return true;
	}
	
	public String toString(){
		String ret = "";
		List<ProductInCart> pic = this.getProducts();
		for (ProductInCart productInCart : pic) {
			ret += productInCart.toString();
		}
		
		return ret;
	}
	
}
