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
	static boolean checkInStock(Product p, int amount) {
		Store s = p.getStore();
		return s.getProducts().get(p) != null && s.getProducts().get(p) >= amount;
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
	 * add product to stock
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean stockUpdate(Product p, int amount) {
		Store s = p.getStore();
		if(amount < 0 || !checkInStock(p, amount))
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
	
	/**
	 * g has in cart all products that purchased already.
	 * need to find all products that can be send to the customer - i.e. ImmediatelyPurchase and may be more.
	 * then need to send all those products to the buyerAddress
	 * @param g - Guest, buyerAddress - destination address.
	 * @return true for success. false for fail.
	 */
	static boolean sendTheProducts(Guest g, String buyerAddress) {
		// TODO Auto-generated method stub
		return true;
	}
}
