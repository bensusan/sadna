package TS_BL;

import java.util.Date;
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

	public static List<Subscriber> allSubscribers = new LinkedList<Subscriber>();
	public static Map<Guest, LinkedList<String>> allUsersWithTheirCreditCards = new HashMap<Guest, LinkedList<String>>(); // TODO
	public static int purchaseId = -1;
	public static int storeId = -1;
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

	public static int updatePrice(DiscountPolicy dp, int price) {
		return BlDiscountPolicy.updatePrice(dp, price);
	}
	
	public static int updatePrice(DiscountPolicy dp, int price, int code) {
		return BlDiscountPolicy.updatePrice(dp, price , code);
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

	public static boolean purchaseCart(Guest g, String creditCardNumber, String buyerAddress) {
		return BlGuest.puchaseCart(g, creditCardNumber, buyerAddress);
	}

	public static boolean pruchaseProduct(Guest g, Product product, int amount, String creditCardNumber,
			String buyerAddress) {
		return BlGuest.pruchaseProduct(g, product, amount, creditCardNumber, buyerAddress);
	}
	
	//do we need this function? already have update price with discount policy
	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		return BlHiddenDiscount.updatePrice(hd, price, code);
	}

	public static boolean purchase(PurchaseType pt, Guest g, int price, int amount) {
		return BlPurchaseType.purchase(pt, g, price, amount);
	}
	public static boolean purchase(ImmediatelyPurchase ip, Guest g, int price){
		return BlImmediatelyPurchase.purchase(ip, g, price);
	}
	public static boolean purchase(LotteryPurchase lp, Guest g, int price) {
		return BlLotteryPurchase.purchase(lp, g, price);
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
		return BlStore.checkInStock(s, p, amount);
	}

	public static boolean addPurchaseToHistory(Store s, Purchase p) {
		return BlStore.addPurchaseToHistory(s, p);
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

	public static boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo) {
		return BlStoreOwner.addNewStoreOwner(oldSo, newSo);
	}

	public static boolean addNewManager(StoreOwner so, StoreManager manager) {
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

	public static Store openStore(Subscriber sub, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen) {
		return BlSubscriber.openStore(sub,gradeing,products,purchaseHistory,isOpen);
	}

	public static boolean addPurchaseToHistory(Subscriber sub, Purchase purchase) {
		return BlSubscriber.addPurchaseToHistory(sub, purchase);
	}

	public static boolean addOwner(Subscriber sub, StoreOwner owner) {
		return BlSubscriber.addOwner(sub,owner);
	}

	public static boolean addManager(Subscriber sub, StoreManager manager) {
		return BlSubscriber.addManager(sub, manager);
	}

	public static boolean deleteOwner(Subscriber sub, StoreOwner owner) {
		return BlSubscriber.deleteOwner(sub, owner);
	}

	public static boolean deleteManager(Subscriber sub, StoreManager manager) {
		return BlSubscriber.deleteManager(sub, manager);
	}

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
	public static boolean payToStore(Store s, int price) {
		return BlStore.payToStore(s, price);
	}

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

	public static boolean correctSpelledLettersSpacesNumbers(String str) {
		return str != null && str.matches("[0-9a-zA-Z\\s]+");
	}
	
	public static boolean correctSpelledLettersSpaces(String str) {
		return str != null && str.matches("[a-zA-Z\\s]+");
	}
	
	public static boolean correctSpelledNumbers(String str){
		return str != null && str.matches("[0-9]+");
	}

	public static boolean legalPassword(String pass) {
		//may change in the future
		return correctSpelledLettersSpacesNumbers(pass);
	}

	//credit card must have between 8 to 16 digits and digits only!
	public static boolean legalCreditCard(String creditCardNumber){
		if(creditCardNumber == null || creditCardNumber.length() < 8 || creditCardNumber.length() > 16)
			return false;
		String regex = "[0-9]+";
		if(creditCardNumber.matches(regex))
			return true;
		return false;
	}
	
	//should be update in the future
	public static boolean legalAddress(String buyerAddress){
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
		LinkedList<String> lst = allUsersWithTheirCreditCards.get(g);
		if(lst==null){
			lst = new LinkedList<String>();
		}
		lst.add(creditCardNumber);
		allUsersWithTheirCreditCards.put(g, lst);
	}
	
	public static void incrementPurchaseId(){
		purchaseId++;
	}
	
	public static int getPurchaseId(){
		return purchaseId;
	}
	
	public static int getStoreId(){
		return storeId++;
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
}
