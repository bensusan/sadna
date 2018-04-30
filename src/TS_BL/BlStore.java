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
	static boolean checkInStock(Store s, Product p, int amount) {
		return s != null && p != null && s.getProducts().get(p) != null && s.getProducts().get(p) >= amount;
	}

	/**
	 * add cart to history
	 * 
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	static boolean addPurchaseToHistory(Store s, Purchase p) {
		List<Purchase> res = s.getPurchaseHistory();
		if(!res.add(p))
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
	static boolean buyProduct(Store s, Product p, int amount) {
		return checkInStock(s, p, amount) && stockUpdate(s, p, s.getProducts().get(p) - amount) ;  
	}

	/**
	 * add product to stock
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean stockUpdate(Store s, Product p, int amount) {
		if(amount < 0 || !checkInStock(s, p, amount))
			return false;
		Map<Product, Integer> products = s.getProducts();
		products.put(p, products.get(p) - amount);
		if(products.get(p) == 0)
			products.remove(p);
		return true;
	}
	
	static boolean payToStore(Store s, int price){
		//TODO maybe later this function will return false
		s.setMoneyEarned(s.getMoneyEarned() + price);
		return true;
	}
}
