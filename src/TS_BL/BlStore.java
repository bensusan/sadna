package TS_BL;

import java.util.Date;
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
	static boolean addProductToHistory(ProductInCart pic) {
		return pic.getMyProduct().getStore().getPurchaseHistory().add(new Purchase(new Date(), pic));
	}

	/**
	 * reduce product amount from stock
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean buyProduct(ProductInCart pic, String creditCardNumber) {
		Product p = pic.getMyProduct();
		Store s = p.getStore();
		int amount = pic.getAmount();
		int price = pic.getPrice();
		//TODO ASSUME stockUpdate + payment + add to history are atomic. 
		return checkInStock(s, p, amount) && stockUpdate(s, p, s.getProducts().get(p) - amount) && payToStore(s, price, creditCardNumber) && addProductToHistory(pic);  
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
	
	static boolean payToStore(Store s, int price, String creditCard){
		//TODO maybe later this function will return false
		s.setMoneyEarned(s.getMoneyEarned() + price);
		return true;
	}
	
	static boolean retMoney(String creditCard, int price){
		return true;
	}
}
