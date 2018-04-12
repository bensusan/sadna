package TS_BL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class BlStore {

	/**
	 * @param p
	 * @param amount
	 * @return true if product in stock false otherwise
	 */
	public static boolean checkInStock(Store s, Product p, int amount) {
		return s.getProducts().get(p) != null && s.getProducts().get(p) >= amount;
	}

	/**
	 * add cart to history
	 * 
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	public static boolean addPurchaseToHistory(Store s, Cart cart) {
		List<Cart> res = s.getPurchaseHistory();
		if(!res.add(cart))
			return false;
		s.setPurchaseHistory(res);
		return true;
	}

	/**
	 * reduce product amount from stock
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean buyProduct(Store s, Product p, int amount) {
		return BlMain.checkInStock(s, p, amount) && BlMain.stockUpdate(s, p, s.getProducts().get(p) - amount) ;  
	}

	/**
	 * add product to stock
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	public static boolean stockUpdate(Store s, Product p, int amount) {
		if(amount < 0 || !BlMain.checkInStock(s, p, amount))
			return false;
		Map<Product, Integer> products = s.getProducts();
		products.put(p, products.get(p) - amount);
		if(products.get(p) == 0)
			products.remove(p);
		return true;
	}
	
	public static boolean payToStore(Store s, int price){
		//TODO maybe later this function will return false
		s.setMoneyEarned(s.getMoneyEarned() + price);
		return true;
	}
}
