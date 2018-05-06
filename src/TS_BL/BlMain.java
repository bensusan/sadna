package TS_BL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import TS_SharedClasses.*;

public class BlMain {

	public final static int NUM_OF_PERMISSIONS = 12; 
	// constants according to BlPermission's functions order.
	public final static int addProductToStore = 0, deleteProductFromStore = 1, updateProductDetails = 2,
			addPolicyToProduct = 3, addDiscountToProduct = 4, addNewStoreOwner = 5, addNewManager = 6, closeStore = 7,
			openStore = 8, getPurchaseHistory = 9, expiredProducts = 10, changeStorePurchasePolicy = 11;

	public static Subscriber admin = new SystemAdministrator("itzik", "11111111", "shmulik", "bait shel or", "0522222222", "111111111111",
			new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>()); //or add this to allSubscribers
	public static List<Subscriber> allSubscribers = new LinkedList<Subscriber>();
	
	public static Map<Guest, LinkedList<String>> allUsersWithTheirCreditCards = new HashMap<Guest, LinkedList<String>>(); // TODO
//	public static int purchaseId = -1;
	public static int storeId = -1;
	

	// need to insert to here all guests that payed with their credit card for pay back

	public static boolean addImmediatelyProduct(Guest g, Product p, int amount) {
		return BlGuest.addImmediatelyProduct(g, p, amount, -1);
	}

	public static boolean addImmediatelyProduct(Guest g, Product p, int amount, int discountCode) {
		return BlGuest.addImmediatelyProduct(g, p, amount, discountCode);
	}

	public static boolean addLotteryProduct(Guest g, Product p, int money) {
		return BlGuest.addLotteryProduct(g, p, money);
	}

	public static boolean removeProductFromCart(Guest g, Product p) {
		return BlGuest.removeProductFromCart(g, p);
	}

	public static boolean editProductAmount(Guest g, Product p, int amount) {
		return BlGuest.editProductAmount(g, p, amount);
	}

	public static boolean editProductDiscount(Guest g, Product p, int discountCode) {
		return BlGuest.editProductDiscount(g, p, discountCode);
	}

	public static boolean editProductPrice(Guest g, Product p, int money) {
		return BlGuest.editProductPrice(g, p, money);
	}

	public static boolean editCart(Guest g, Cart newCart) {
		return BlGuest.editCart(g, newCart);
	}

	public static boolean purchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
		return BlGuest.puchaseCart(g, creditCardNumber, buyerAddress);
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

	public static boolean addNewStoreOwner(StoreManager sm, Subscriber owner) {
		return BlStoreManager.addNewStoreOwner(sm, owner);
	}

	public static boolean addNewManager(StoreManager oldMan, Subscriber newMan) {
		return BlStoreManager.addNewManager(oldMan, newMan);
	}

	public static boolean closeStore(StoreManager sm) {
		return BlStoreManager.closeStore(sm);
	}

	public static boolean openStore(StoreManager sm) {
		return BlStoreManager.openStore(sm);
	}

	public static List<Purchase> getPurchaseHistory(StoreManager sm) {
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

	public static boolean addNewStoreOwner(StoreOwner oldSo, Subscriber newSo) {
		return BlStoreOwner.addNewStoreOwner(oldSo, newSo);
	}

	public static boolean addNewManager(StoreOwner so, Subscriber manager) {
		return BlStoreOwner.addNewManager(so, manager);
	}

	public static boolean closeStore(StoreOwner so) {
		return BlStoreOwner.closeStore(so);
	}

	public static boolean openStore(StoreOwner so) {
		return BlStoreOwner.openStore(so);
	}

	public static List<Purchase> getPurchaseHistory(StoreOwner so) {
		return BlStoreOwner.getPurchaseHistory(so);
	}

	public static Store openStore(Subscriber sub, String storeName,  int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen) {
		return BlSubscriber.openStore(sub,storeName,gradeing,products,purchaseHistory,isOpen);
	}

	static boolean addPurchaseToHistory(Subscriber sub, Purchase purchase) {
		return BlSubscriber.addPurchaseToHistory(sub, purchase);
	}

	public static boolean addOwner(Subscriber sub, StoreOwner owner) {
		return BlSubscriber.addOwner(sub,owner);
	}

	public static boolean addManager(Subscriber sub, StoreManager manager) {
		return BlSubscriber.addManager(sub, manager);
	}
	//
	//	static boolean deleteOwner(Subscriber sub, StoreOwner owner) {
	//		return BlSubscriber.deleteOwner(sub, owner);
	//	}
	//
	//	static boolean deleteManager(Subscriber sub, StoreManager manager) {
	//		return BlSubscriber.deleteManager(sub, manager);
	//	}

	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
		return BlSystemAdministrator.removeSubscriber(sa, s);
	}

	public static List<Purchase> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
		return BlSystemAdministrator.viewSubscriberHistory(sa, s);
	}

	public static List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store) {
		return BlSystemAdministrator.viewStoreHistory(sa, store);
	}

	// More functions that are necessary.
	//	static boolean payToStore(Store s, int price) {
	//		return BlStore.payToStore(s, price);
	//	}

	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address,
			String phone, String creditCardNumber) {
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


	////////////////////////////////////// Help functions for everyone
	static Subscriber checkIfSubscriberExists(String username) {
		for (Subscriber subscriber : allSubscribers) {
			if (subscriber.getUsername().equals(username))
				return subscriber;
		}
		return null;
	}

	static boolean correctSpelledLettersSpacesNumbers(String str) {
		return str != null && str.matches("[0-9a-zA-Z\\s]+");
	}

	static boolean correctSpelledLettersSpaces(String str) {
		return str != null && str.matches("[a-zA-Z\\s]+");
	}

	static boolean correctSpelledNumbers(String str){
		return str != null && str.matches("[0-9]+");
	}

	static boolean legalPassword(String pass) {
		//may change in the future
		return correctSpelledLettersSpacesNumbers(pass);
	}

	//credit card must have between 8 to 16 digits and digits only!
	static boolean legalCreditCard(String creditCardNumber){
		if(creditCardNumber == null || creditCardNumber.length() < 8 || creditCardNumber.length() > 16)
			return false;
		String regex = "[0-9]+";
		if(creditCardNumber.matches(regex))
			return true;
		return false;
	}

	//should be update in the future
	static boolean legalAddress(String buyerAddress){
		if(buyerAddress == null)
			return false;
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

	private static List<Product> findProductByCriterion(String criterion, String str) {
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

	private static boolean productInCriterion(String criterion, String str, Product p) {
		if (criterion.equals("Name") && p.getName().equals(str))
			return true;
		if (criterion.equals("Category") && p.getCategory().equals(str))
			return true;
		return false;
	}

	static void addCreditCardToMap(String creditCardNumber, Guest g) {
		LinkedList<String> lst = allUsersWithTheirCreditCards.get(g);
		if(lst==null){
			lst = new LinkedList<String>();
		}
		lst.add(creditCardNumber);
		allUsersWithTheirCreditCards.put(g, lst);
	}
	
	public static boolean equalsLists(List<?> a, List<?> b){
		return (a==null && b==null) || (a!=null && b!=null && equalsLeftListToRightList(a, b) && equalsLeftListToRightList(b, a));
	}

	private static boolean equalsLeftListToRightList(List<?> a, List<?> b){
		boolean found = false;
		for (Object key : a) {
			if(b.contains(key))
			{
				for (int i = 0; i < b.size(); i++) {
					if(b.get(i).equals(key))
						found = true;
				}
			}
		}
		return found;
	}

	public static boolean equalsMaps(Map<?, ?> a, Map<?, ?> b){
		return (a==null && b==null) || (a!=null && b!=null && equalsLeftMapToRightMap(a, b) && equalsLeftMapToRightMap(b, a));
	}

	private static boolean equalsLeftMapToRightMap(Map<?, ?> a, Map<?, ?> b){
		for (Object key : a.keySet()) {
			if(a.get(key) != b.get(key))
				return false;
		}
		for (Object key : b.keySet()) {
			if(a.get(key) != b.get(key))
				return false;
		}
		return true;
	}
	
	static boolean isImmediatelyPurchase(Product p){
		if(p.getPurchasePolicy() == null || !(p.getPurchasePolicy().getPurchaseType() instanceof ImmediatelyPurchase))
			return false;
		return true;
	}
	
	static boolean isLotteryPurchase(Product p){
		if(p.getPurchasePolicy() == null || !(p.getPurchasePolicy().getPurchaseType() instanceof LotteryPurchase))
			return false;
		return true;
	}
	
	static String getCreditCard(Guest g){
		return allUsersWithTheirCreditCards.get(g).get(0);
	}
}
