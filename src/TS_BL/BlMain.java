package TS_BL;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import TS_SharedClasses.*;

public class BlMain {

	// constants according to BlPermission's functions order.
	public final static int addProductToStore = 0, deleteProductFromStore = 1, updateProductDetails = 2,
			addPolicyToProduct = 3, addDiscountToProduct = 4, addNewStoreOwner = 5, addNewManager = 6, closeStore = 7,
			openStore = 8, getPurchaseHistory = 9, expiredProducts = 10, changeStorePurchasePolicy = 11;

	public static List<Subscriber> allSubscribers = new LinkedList<Subscriber>();
	public static Map<Guest, List<String>> allUsersWithTheirCreditCards = new HashMap<Guest, List<String>>(); // TODO
	// need to insert to here all guests that payed with their credit card for pay back
	
	// public static boolean puchaseCart(Cart c, int creditCardNumber, String
	// buyerAddress) {
	//
	// return BlCart.puchaseCart(c, creditCardNumber, buyerAddress);
	// }

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

	public static int updatePrice(DiscountPolicy dp, int price, int code) {
		return dp.updatePrice(price, code);
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

	public static boolean puchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
		addCreditCartToMap(creditCardNumber, g);
		return BlGuest.puchaseCart(g, creditCardNumber, buyerAddress);
	}

	public static boolean pruchaseProduct(Guest g, Product product, int amount, String creditCardNumber,
			String buyerAddress) {
		addCreditCartToMap(creditCardNumber, g);
		return BlGuest.pruchaseProduct(g, product, amount, creditCardNumber, buyerAddress);
	}

	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		return hd.updatePrice(price, code);
	}

	public static boolean purchase(PurchaseType pt, Guest g, int price, int amount) {
		return pt.purchase(g, price, amount);
	}

	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price) {
		return BlImmediatelyPurchase.getDiscountedPrice(ip, price);
	}

	public static boolean isLotteryDone(LotteryPurchase lp, int price) {
		return BlLotteryPurchase.isLotteryDone(lp, price);
	}

	public static void closeCurrentLottery(LotteryPurchase lp) {
		BlLotteryPurchase.closeCurrentLottery(lp);
	}

	public static void openNewLottery(LotteryPurchase lp, Date endDate) {
		BlLotteryPurchase.openNewLottery(lp, endDate);
	}

	public static boolean purchase(Product p, Guest g, int price, int amount) {
		return BlProduct.purchase(p, g, price, amount);
	}

	public static boolean purchase(PurchasePolicy pp, Guest g, int price, int amount) {
		return BlPurchasePolicy.purchase(pp, g, price, amount);
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
		return BlStoreManager.addDiscountToProduct(sm, discount, product);
	}

	public static boolean addNewStoreOwner(StoreManager sm, StoreOwner owner) {
	return BlStoreManager.addNewStoreOwner(sm, owner);
	}

	public static boolean addNewManager(StoreManager oldMan, StoreManager newMan) {
		return BlStoreManager.addNewManager(oldMan, newMan);
	}

	public static boolean closeStore(StoreManager sm) {
		return BlStoreManager.closeStore(sm);
	}

	public static boolean openStore(StoreManager sm) {
		return BlStoreManager.openStore(sm);
	}

	public static List<Cart> getPurchaseHistory(StoreManager sm) {
		return BlStoreManager.getPurchaseHistory(sm);
	}

	public static boolean addProductToStore(StoreOwner so, Product product, int amount) {
		return BlStoreOwner.addProductToStore(so, product, amount);
	}

	public static boolean deleteProductFromStore(StoreOwner so, Product product) {
		return BlStoreOwner.deleteProductFromStore(so, product);
	}

	public static boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount) {
		return BlStoreOwner.updateProductDetails(so, oldProduct, newProduct, amount);
	}

	public static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product) {
		return BlStoreOwner.addPolicyToProduct(so, policy, product);
	}

	public static boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product) {
		return BlStoreOwner.addDiscountToProduct(so, discount, product);
	}

	public static boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo) {
		return BlStoreOwner.addNewStoreOwner(oldSo, newSo);
	}

	public static boolean addNewManager(StoreOwner so, StoreManager manager) {
		return BlStoreOwner.addNewManager(so, manager);
	}

	public static boolean closeStore(StoreOwner so) {
		return BlPermissions.closeStore(so.getStore());
	}

	public static boolean openStore(StoreOwner so) {
		return BlPermissions.openStore(so.getStore());
	}

	public static List<Cart> getPurchaseHistory(StoreOwner so) {
		return BlPermissions.getPurchaseHistory(so.getStore());
	}

	public static Store openStore(Subscriber sub, String storeName, String Description) {
		// TODO: list of stores for sub? store constructor doesn't fit + check
		// who will call this method

		return null;
	}

	public static boolean addPurchaseToHistory(Subscriber sub, Cart cart) {
		if (cart == null || sub == null)
			return false;

		sub.setCart(cart);
		return true;
	}

	public static boolean addOwner(Subscriber sub, StoreOwner owner) {
		List<StoreOwner> sOwn = sub.getOwner();
		if (sOwn.contains(owner))
			return false;

		sOwn.add(owner);
		sub.setOwner(sOwn);
		return true;
	}

	public static boolean addManager(Subscriber sub, StoreManager manager) {
		List<StoreManager> sMng = sub.getManager();
		if (sMng.contains(manager))
			return false;

		sMng.add(manager);
		sub.setManager(sMng);
		return true;
	}

	public static boolean deleteOwner(Subscriber sub, StoreOwner owner) {
		List<StoreOwner> sOwn = sub.getOwner();
		if (!sOwn.contains(owner))
			return false;

		sOwn.remove(owner);
		sub.setOwner(sOwn);
		return true;
	}

	public static boolean deleteManager(Subscriber sub, StoreManager manager) {
		List<StoreManager> sMng = sub.getManager();
		if (!sMng.contains(manager))
			return false;

		sMng.remove(manager);
		sub.setManager(sMng);
		return true;
	}

	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
		List<Subscriber> subList = BlMain.allSubscribers;
		if (!subList.contains(s))
			return false;

		subList.remove(s);
		BlMain.allSubscribers = subList;
		return true;
	}

	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
		List<Subscriber> subList = BlMain.allSubscribers;
		if (!subList.contains(s))
			return null;

		return subList.get(subList.indexOf(s)).getPurchaseHistory();
	}

	public static List<Cart> viewStoreHistory(SystemAdministrator sa, Store store) {
		return BlPermissions.getPurchaseHistory(store);
	}

	// More functions that are necessary.
	public static boolean payToStore(Store s, int price) {
		return BlStore.payToStore(s, price);
	}

	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address,
			int phone, String creditCardNumber) {
		return BlGuest.signUp(g, username, password, fullName, address, phone, creditCardNumber);
	}

	public static Subscriber signIn(Guest g, String username, String password) {
		return BlGuest.signIn(g, username, password);
	}

	public static void expiredProducts(StoreOwner so) {
		BlStoreOwner.expiredProducts(so);
	}

	public static void expiredProducts(StoreManager sm) {
		BlStoreManager.expiredProducts(sm);
	}
	
	public static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp){
		return BlStoreOwner.changeStorePurchasePolicy(so, pp);
	}
	
	public static boolean changeStorePurchasePolicy(StoreManager sm, PurchasePolicy pp){
		return BlStoreManager.changeStorePurchasePolicy(sm, pp);
	}

	// return money if lottery is done and nobody won
	public static boolean retMoney(Guest g, int amountToRet) {
		return true;
	}

	public static boolean payMoney(Guest g, int amountToPay) {
		return true;
	}

	////////////////////////////////////// Help functions for everyone
	public static Subscriber checkIfSubscriberExists(String username) {
		for (Subscriber subscriber : allSubscribers) {
			if (subscriber.getUsername().equals(username))
				return subscriber;
		}
		return null;
	}

	public static boolean misspelled(String str) {
		// TODO - check if string could be misspelled
		return false;
	}

	public static boolean legalPassword(String pass) {
		// TODO - check password rules
		return true;
	}

	public static Map<Store, Map<Product, Integer>> getAllStoresWithThierProductsAndAmounts() {
		Map<Store, Map<Product, Integer>> res = new HashMap<Store, Map<Product, Integer>>();
		List<Store> stores = getAllStores();
		for (Store store : stores) {
			res.put(store, store.getProducts());
		}
		return res;
	}

	public static List<Store> getAllStores() {
		List<Store> res = new LinkedList<Store>();
		Store newStore;
		for (Subscriber subscriber : allSubscribers) {
			for (StoreOwner owner : subscriber.getOwner()) {
				newStore = owner.getStore();
				for (Store store : res) {
					if (store.equals(owner.getStore()))
						newStore = null;
					break;
				}
				if (newStore != null)
					res.add(newStore);
				newStore = null;
			}
		}
		return res;
	}

	public static List<Product> findProductByName(String name) {
		return findProductByCriterion("Name", name);
	}

	public static List<Product> findProductByCategory(String category) {
		return findProductByCriterion("Category", category);
	}

	public static List<Product> findProductByCriterion(String criterion, String str) {
		List<Product> res = new LinkedList<Product>();
		Map<Store, Map<Product, Integer>> swithpanda = getAllStoresWithThierProductsAndAmounts();
		for (Map<Product, Integer> panda : swithpanda.values()) {
			for (Product p : panda.keySet()) {
				if (productInCriterion(criterion, str, p))
					res.add(p);
			}
		}
		return res;
	}

	public static boolean productInCriterion(String criterion, String str, Product p) {
		if (criterion.equals("Name") && p.getName().equals(str))
			return true;
		if (criterion.equals("Category") && p.getCategory().equals(str))
			return true;
		return false;
	}

	public static void addCreditCartToMap(String creditCardNumber, Guest g) {
		List<String> lst = allUsersWithTheirCreditCards.get(g);
		lst.add(creditCardNumber);
		allUsersWithTheirCreditCards.put(g, lst);
	}
}
