package TS_SharedClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import TS_BL.BlMain;

public class Cart {

	private Map<Product,Integer> products;
	
	public Cart()
	{
		this.products=new HashMap<Product,Integer>();
	}
	
	public Cart(Map<Product, Integer> products) {
		super();
		this.products = products;
		if(products == null)
			this.products = new HashMap<Product, Integer>();
	}
	
	public Map<Product,Integer> getProducts() {
		return products;
	}
	
	public void setProducts(Map<Product,Integer> products) {
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
		if(!BlMain.equalsMaps(products, other.products))
			return false;
		return true;
	}
	
	
}
