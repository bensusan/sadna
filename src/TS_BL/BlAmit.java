package TS_BL;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class BlAmit {

	static List<Subscriber> allSubscribers = new LinkedList<Subscriber>();
	static Map<Guest, List<Integer>> allUsersWithTheirCreditCards = new HashMap<Guest, List<Integer>>(); //TODO need to insert to here all guests that payed with their credit card for pay back
	
	//start new functions
	public static boolean payToStore(Store s, int price){
		//TODO maybe later this function will return false
		s.setMoneyEarned(s.getMoneyEarned() + price);
		return true;
	}
	
	//return null if not good else return the new subscriber
	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address, int phone, int creditCardNumber){
		if(misspelled(username) || misspelled(fullName) || misspelled(address))
			return null; //exception spell in user name | full name | address
		if(!legalPassword(password))
			return null; //password rules failed.
		if(checkIfSubscriberExists(username) != null)
			return null; //user name exists
		return new Subscriber(g.getCart(), username, password, fullName, address, phone, creditCardNumber, new LinkedList<Cart>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>());
		
	}
	
	public static Subscriber signIn(Guest g, String username, String password){
		if(misspelled(username))
			return null; //exception spell in user name
		if(!legalPassword(password))
			return null; //password rules failed.
		return checkIfSubscriberExists(username);
	}
	
	public static Subscriber checkIfSubscriberExists(String username){
		for (Subscriber subscriber : allSubscribers) {
			if(subscriber.getUsername().equals(username))
				return subscriber;
		}
		return null;
	}
	
	public static boolean misspelled(String str){
		//TODO - check if string could be misspelled
		return false;
	}
	
	public static boolean legalPassword(String pass){
		//TODO - check password rules
		return true;
	}
	
	//1.3
	public static Map<Store, Map<Product, Integer>> getAllStoresWithThierProductsAndAmounts(){
		Map<Store, Map<Product, Integer>> res = new HashMap<Store, Map<Product, Integer>>();
		List<Store> stores = getAllStores();
		for (Store store : stores) {
			res.put(store, store.getProducts());
		}
		return res;
	}
	
	public static List<Store> getAllStores(){
		List<Store> res = new LinkedList<Store>();
		Store newStore;
		for (Subscriber subscriber : allSubscribers) {
			for (StoreOwner owner : subscriber.getOwner()) {
				newStore = owner.getStore();
				for (Store store : res) {
					if(store.equals(owner.getStore()))
						newStore = null;
						break;
				}
				if(newStore != null)
					res.add(newStore);
				newStore = null;
			}
		}
		return res;
	}
	
	//1.4
	public static List<Product> findProductByName(String name){
		return findProductByCriterion("Name", name);
	}
	
	public static List<Product> findProductByCategory(String category){
		return findProductByCriterion("Category", category);
	}
	
	public static List<Product> findProductByCriterion(String criterion, String str){
		List<Product> res = new LinkedList<Product>();
		Map<Store, Map<Product, Integer>> swithpanda = getAllStoresWithThierProductsAndAmounts();
		for (Map<Product, Integer> panda : swithpanda.values()) {
			for (Product p : panda.keySet()) {
				if(productInCriterion(criterion, str, p))
					res.add(p);
			}
		}
		return res;
	}
	
	public static boolean productInCriterion(String criterion, String str, Product p){
		if(criterion.equals("Name") && p.getName().equals(str))
			return true;
		if(criterion.equals("Category") && p.getCategory().equals(str))
			return true;
		return false;
	}
	
	//1.7.1 . need to add the errors and the third function in BLPremissions
	public static void expiredProducts(StoreOwner so){
		BlPermissions.expiredProducts(so.getStore(), allUsersWithTheirCreditCards);
	}
	
	public static void expiredProducts(StoreManager sm){
		if(sm.getPremisions()[BlPermissions.expiredProducts]) 
			BlPermissions.expiredProducts(sm.getStore(), allUsersWithTheirCreditCards);
	}
	
	public static void expiredProducts(Store s){
		for (Product product : s.getProducts().keySet()) {
			PurchaseType pt = product.getPurchasePolicy().getPurchaseType(); 
			if(pt instanceof LotteryPurchase){
				Calendar today = Calendar.getInstance();
				today.set(Calendar.HOUR_OF_DAY, 0);
				LotteryPurchase lpt = ((LotteryPurchase)pt); 
				if(lpt.getLotteryEndDate().before(today.getTime())){
					BlMain.closeCurrentLottery(lpt);
				}
			}
		}
	}
	//end of new functions
	
	
	public static boolean purchase(Product p, Guest g, int price, int amount) {
		Cart newCart = new Cart();
		return purchase(p.getPurchasePolicy(), g, price, amount) && buyProduct(p.getStore(), p, amount) && payToStore(p.getStore(), price) && BlMain.addProduct(newCart, p, amount) && addPurchaseToHistory(p.getStore(), newCart);
	}

	public static boolean purchase(PurchasePolicy pp, Guest g, int price, int amount) {
		return canPurchase(pp, g) && BlMain.purchase(pp.getPurchaseType(), g, price, amount);
	}

	public static boolean canPurchase(PurchasePolicy pp, Guest g) {
		//TODO think if there are any conditionals here.
		return true;
	}

	public static boolean checkInStock(Store s, Product p, int amount) {
		return s.getProducts().get(p) != null && s.getProducts().get(p) >= amount;
	}

	public static boolean addPurchaseToHistory(Store s, Cart cart) {
		List<Cart> res = s.getPurchaseHistory();
		if(!res.add(cart))
			return false;
		s.setPurchaseHistory(res);
		return true;
	}


	public static boolean buyProduct(Store s, Product p, int amount) {
		return BlMain.checkInStock(s, p, amount) && BlMain.stockUpdate(s, p, s.getProducts().get(p) - amount) ;  
	}


	public static boolean stockUpdate(Store s, Product p, int amount) {
		if(amount < 0 || !BlMain.checkInStock(s, p, amount))
			return false;
		Map<Product, Integer> products = s.getProducts();
		products.put(p, products.get(p) - amount);
		if(products.get(p) == 0)
			products.remove(p);
		return true;
	}
	
	public static boolean addProductToStore(StoreManager sm, Product product, int amount) {
		return sm.getPremisions()[BlPermissions.addDiscountToProduct] && BlPermissions.addProductToStore(sm.getStore(), product, amount);
	}


	public static boolean deleteProductFromStore(StoreManager sm, Product product) {
		return sm.getPremisions()[BlPermissions.deleteProductFromStore] && BlPermissions.deleteProductFromStore(sm.getStore(), product);
	}

	public static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount) {
		return sm.getPremisions()[BlPermissions.updateProductDetails] && BlPermissions.updateProductDetails(sm.getStore(), oldProduct, newProduct, amount);
	}


	public static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) {
		return sm.getPremisions()[BlPermissions.addPolicyToProduct] && BlPermissions.addPolicyToProduct(sm.getStore(), policy, product);
	}


	public static boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount, Product product) {
		return sm.getPremisions()[BlPermissions.addDiscountToProduct] && BlPermissions.addDiscountToProduct(sm.getStore(), discount, product);
	}


	public static boolean addNewStoreOwner(StoreManager sm, StoreOwner owner) {
		return sm.getPremisions()[BlPermissions.addNewStoreOwner] && BlPermissions.addNewStoreOwner(sm.getStore(), owner);
	}


	public static boolean addNewManager(StoreManager oldMan, StoreManager newMan) {
		return oldMan.getPremisions()[BlPermissions.addNewManager] && BlPermissions.addNewManager(oldMan.getStore(), newMan);
	}


	public static boolean closeStore(StoreManager sm) {
		return sm.getPremisions()[BlPermissions.closeStore] && BlPermissions.closeStore(sm.getStore());
	}


	public static boolean openStore(StoreManager sm) {
		return sm.getPremisions()[BlPermissions.openStore] && BlPermissions.openStore(sm.getStore());
	}


	public static List<Cart> getPurchaseHistory(StoreManager sm) {
		return sm.getPremisions()[BlPermissions.getPurchaseHistory] ? BlPermissions.getPurchaseHistory(sm.getStore()) : null;
	}


	public static boolean addProductToStore(StoreOwner so, Product product, int amount) {
		return BlPermissions.addProductToStore(so.getStore(), product, amount);
	}


	public static boolean deleteProductFromStore(StoreOwner so, Product product) {
		return BlPermissions.deleteProductFromStore(so.getStore(), product);
	}

	public static boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount) {
		return BlPermissions.updateProductDetails(so.getStore(), oldProduct, newProduct, amount);
	}
}
