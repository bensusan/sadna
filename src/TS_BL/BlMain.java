package TS_BL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import TS_SharedClasses.*;

public class BlMain{	
	
	public static List<Subscriber> allSubscribers = new LinkedList<Subscriber>();
	public static Map<Guest, List<Integer>> allUsersWithTheirCreditCards = new HashMap<Guest, List<Integer>>(); //TODO need to insert to here all guests that payed with their credit card for pay back
	
	public static boolean puchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
		
		return BlCart.puchaseCart(c, creditCardNumber, buyerAddress);
	}

	
	public static boolean addProduct(Cart c, Product p, int amount) {
		return BlCart.addProduct(c, p, amount);
	}

	
	public static boolean removeProduct(Cart c, Product p) {
		return BlCart.removeProduct(c, p);
	}

	
	public static boolean editProduct(Cart c, Product p, int amount) {
		return BlCart.editProduct(c, p, amount);
	}

	
	public static boolean editCart(Cart c, Map<Product, Integer> newCart) {
		return BlCart.editCart(c, newCart);
	}

	
	public static int updatePrice(DiscountPolicy dp, int price) {
		return BlDiscountPolicy.updatePrice(dp, price);
	}

	
	public static boolean addProductToCart(Guest g, Product p, int amount) {
		return BlGuest.addProductToCart(g, p, amount);
	}

	
	public static boolean removeProductFromCart(Guest g, Product p) {
		return BlGuest.removeProductFromCart(g, p);
	}

	
	public static boolean editProductInCart(Guest g, Product p, int amount) {
		return BlGuest.editProductInCart(g, p, amount);
	}

	
	public static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		return BlGuest.editCart(g, newCart);
	}

	
	public static boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress) {
		return BlGuest.puchaseCart(g, creditCardNumber, buyerAddress);
	}

	
	public static boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress) {
		return BlGuest.pruchaseProduct(g, product, amount, creditCardNumber, buyerAddress);
	}

	
	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		return BlHiddenDiscount.updatePrice(hd, price, code);
	}

	
	public static boolean purchase(PurchaseType pt, Guest g, int price, int amount) {
		return BlPurchaseType.purchase(pt, g, price, amount);
	}

	
	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price) {
		return BlImmediatelyPurchase.getDiscountedPrice(ip, price);
	}

	
	public static boolean isLotteryDone(LotteryPurchase lp) {
		return BlLotteryPurchase.isLotteryDone(lp);
	}

	
	public static void closeCurrentLottery(LotteryPurchase lp) {
		BlLotteryPurchase.closeCurrentLottery(lp);
	}

	
	public static void openNewLottery(LotteryPurchase lp) {
		BlLotteryPurchase.openNewLottery(lp);
	}

	
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
		return BlStore.stockUpdate(s, p, amount);
	}

	public static boolean addPurchaseToHistory(Store s, Cart cart) {
		return BlStore.addPurchaseToHistory(s, cart);
	}


	public static boolean buyProduct(Store s, Product p, int amount) {
		return BlStore.buyProduct(s, p, amount);  
	}


	public static boolean stockUpdate(Store s, Product p, int amount) {
		return BlStore.stockUpdate(s, p, amount);
	}
	
	public static boolean addProductToStore(StoreManager sm, Product product, int amount) {
		return BlStoreManager.addProductToStore(sm, product, amount);
	}


	public static boolean deleteProductFromStore(StoreManager sm, Product product) {
		return BlStoreManager.deleteProductFromStore(sm, product);
	}

	public static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount) {
		return BlStoreManager.updateProductDetails(sm, oldProduct, newProduct, amount);
	}


	public static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) {
		return BlStoreManager.addPolicyToProduct(sm, policy, product);
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

	
	public static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product) {
		
		return false;
	}

	
	public static boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addNewManager(StoreOwner so, StoreManager manager) {
		List<StoreManager> sMng = so.getStore().getMyManagers();
		if(sMng.contains(manager))
			return false;
		
		sMng.add(manager);
		so.getStore().setMyManagers(sMng);
		return false;
	}

	
	public static boolean closeStore(StoreOwner so) {
		so.getStore().setIsOpen(false);
		return true;
	}

	
	public static boolean openStore(StoreOwner so) {
		so.getStore().setIsOpen(true);
		return true;
	}

	
	public static List<Cart> getPurchaseHistory(StoreOwner so) {
		//TODO , StoreOwner doesn't even extends Subscriber
		return null;
	}

	
	public static Store openStore(Subscriber sub, String storeName, String Description) {
		//TODO: list of stores for sub? store constructor doesn't fit
		return null;
	}

	
	public static boolean addPurchaseToHistory(Subscriber sub, Cart cart) {
		if(cart == null || sub == null)
			return false;
		
		sub.setCart(cart);
		return true;
	}

	
	public static boolean addOwner(Subscriber sub, StoreOwner owner) {
		List<StoreOwner> sOwn = sub.getOwner();
		if(sOwn.contains(owner))
			return false;
		
		sOwn.add(owner);
		sub.setOwner(sOwn);
		return true;
	}

	
	public static boolean addManager(Subscriber sub, StoreManager manager) {
		List<StoreManager> sMng = sub.getManager();
		if(sMng.contains(manager))
			return false;
		
		sMng.add(manager);
		sub.setManager(sMng);
		return true;
	}

	
	public static boolean deleteOwner(Subscriber sub, StoreOwner owner) {
		List<StoreOwner> sOwn = sub.getOwner();
		if(!sOwn.contains(owner))
			return false;
		
		sOwn.remove(owner);
		sub.setOwner(sOwn);
		return true;
	}

	
	public static boolean deleteManager(Subscriber sub, StoreManager manager) {
		List<StoreManager> sMng = sub.getManager();
		if(!sMng.contains(manager))
			return false;
		
		sMng.remove(manager);
		sub.setManager(sMng);
		return true;
	}

	
	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
		List<Subscriber> subList = BlMain.allSubscribers;
		if(!subList.contains(s))
			return false;
		
		subList.remove(s);
		BlMain.allSubscribers = subList;
		return true;
	}

	
	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
		List<Subscriber> subList = BlMain.allSubscribers;
		if(!subList.contains(s))
			return null;
		
		return subList.get(subList.indexOf(s)).getPurchaseHistory();
	}

	
	public static List<Cart> viewStoreHistory(SystemAdministrator sa, Store store) {
		//TODO: add list for stores?
		return null;
	}

	//More functions that are necessary.
	public static boolean payToStore(Store s, int price){
		return BlStore.payToStore(s, price);
	}
	
	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address, int phone, int creditCardNumber){
		return BlGuest.signUp(g, username, password, fullName, address, phone, creditCardNumber);
	}
	
	public static Subscriber signIn(Guest g, String username, String password){
		return BlGuest.signIn(g, username, password);
	}
	
	public static void expiredProducts(StoreOwner so){
		BlStoreOwner.expiredProducts(so);
	}
	
	public static void expiredProducts(StoreManager sm){
		BlStoreManager.expiredProducts(sm);
	}
	
	
	
	//////////////////////////////////////Help functions for everyone
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
	
	
}
