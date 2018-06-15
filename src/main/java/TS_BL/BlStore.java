package TS_BL;

import java.util.Date;
import java.util.Map;

import TS_SharedClasses.*;
import static TS_BL.BlMain.dalRef;

public class BlStore {

	/**
	 * @param p
	 * @param amount
	 * @return true if product in stock false otherwise
	 */
	static boolean checkInStock(Product p, int amount) throws Exception{
		return dalRef.isProductExistsInStore(p.getStore(), p) && dalRef.getProductAmount(p.getStore().getStoreId()).get(p) >= amount;
	}

	/**
	 * add cart to history
	 * 
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	static Purchase addProductToHistory(ProductInCart pic) throws Exception{
		Purchase newPur = new Purchase(new Date(), pic);
		Store s = dalRef.getStoreByStoreId(pic.getMyProduct().getStore().getStoreId());
		s.getPurchaseHistory().add(newPur);
		dalRef.updateStore(s);
		return newPur;
	}

	/**
	 * add product to stock
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean stockUpdate(Product p, int amount) throws Exception {
		Store s = dalRef.getStoreByStoreId(p.getStore().getStoreId());
		if(amount < 0)
			throw new Exception("amount must be a positive number");
		if(!checkInStock(p, amount))
			throw new Exception("there isn't enough products in stock");
		Map<Product, Integer> products = dalRef.getProductAmount(s.getStoreId());
		products.put(p, amount);
		if(products.get(p) == 0)
			products.remove(p);
		dalRef.updateStore(s);
		return true;
	}
	
	static boolean payToStore(Store s, int price, String creditCard) throws Exception{
		//TODO maybe later this function will return false
		Store updatedStore = dalRef.getStoreByStoreId(s.getStoreId());
		s.setMoneyEarned(updatedStore.getMoneyEarned() + price);
		dalRef.updateMoneyEarned(updatedStore, price);
		s = updatedStore;
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

	public static void undoPayToStore(Store s, int price, String creditCardNumber) throws Exception{
		Store updatedStore = dalRef.getStoreByStoreId(s.getStoreId());
		s.setMoneyEarned(updatedStore.getMoneyEarned() - price);
		dalRef.updateMoneyEarned(updatedStore, price);
		s = updatedStore;
	}
}
