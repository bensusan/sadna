package TS_BL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import TS_DAL.*;
import TS_SharedClasses.*;

public class BlMain {
	
	
	static Logger logger = Logger.getLogger("SecurityLog");
    static FileHandler fh;
    static SimpleFormatter formatter = new SimpleFormatter();
	{
		Locale.setDefault(new Locale("en", "US"));
		try {
			fh = new FileHandler("SecurityLogFile.log");
		}catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
		logger.addHandler(fh);fh.setFormatter(formatter);
	}
	
	public final static int NUM_OF_PERMISSIONS = 14; 
	// constants according to BlPermission's functions order.
	public final static int addProductToStore = 0, deleteProductFromStore = 1, updateProductDetails = 2,
			addPolicyToProduct = 3, addDiscountToProduct = 4, addNewStoreOwner = 5, addNewManager = 6, closeStore = 7,
			openStore = 8, getPurchaseHistory = 9, expiredProducts = 10, changeStorePurchasePolicy = 11,
			addDiscountToCategoryStore=12, changeProductType=13;

	public static List<Subscriber> allSubscribers = new ArrayList<Subscriber>(){
		{
			add(((Subscriber)new SystemAdministrator("itzik", BlGuest.md5Hash("11111111"), "shmulik", "bait shel or", "0522222222", "111111111111", new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>())));
		}		
	};
	public static List<Category> allCategory=new ArrayList<Category>(){
		{
			add(new Category("toys"));
			add(new Category("food"));
			add(new Category("a"));
			//....
		}
	};
	

	//public final static DAL dalRef = new DALProxy();
	public static Map<Guest, LinkedList<String>> allUsersWithTheirCreditCards = new HashMap<Guest, LinkedList<String>>();
	
	
	// need to insert to here all guests that payed with their credit card for pay back
	public static List<String> getAllCategorys() {
		List<String>ans=new LinkedList<String>();
		for(Category c:allCategory){
			ans.add(c.getName());
		}
		return ans;
	}
	
	public static boolean addImmediatelyProduct(Guest g, Product p, int amount) throws Exception {
		return BlGuest.addImmediatelyProduct(g, p, amount, -1);
	}

	public static boolean addImmediatelyProduct(Guest g, Product p, int amount, int discountCode) throws Exception {
		return BlGuest.addImmediatelyProduct(g, p, amount, discountCode);
	}

	public static boolean addLotteryProduct(Guest g, Product p, int money) throws Exception {
		return BlGuest.addLotteryProduct(g, p, money);
	}

	public static boolean removeProductFromCart(Guest g, Product p) throws Exception {
		return BlGuest.removeProductFromCart(g, p);
	}

	public static boolean editProductAmount(Guest g, Product p, int amount) throws Exception {
		return BlGuest.editProductAmount(g, p, amount);
	}

	public static boolean editProductDiscount(Guest g, Product p, int discountCode) throws Exception {
		return BlGuest.editProductDiscount(g, p, discountCode);
	}

	public static boolean editProductPrice(Guest g, Product p, int money) throws Exception {
		return BlGuest.editProductPrice(g, p, money);
	}

	public static boolean editCart(Guest g, Cart newCart) throws Exception {
		return BlGuest.editCart(g, newCart);
	}

	public static boolean purchaseCart(Guest g, String creditCardNumber, String buyerAddress) throws Exception {
		return BlGuest.puchaseCart(g, creditCardNumber, buyerAddress);
	}

	public static boolean addProductToStore(StoreManager sm, Product product, int amount,String category) throws Exception {
		return correctSpelledLettersSpacesNumbers(category) && BlStoreManager.addProductToStore(sm, product, amount,category);
	}

	public static boolean deleteProductFromStore(StoreManager sm, Product product) throws Exception {
		return BlStoreManager.deleteProductFromStore(sm, product);
	}

	public static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount,String newProductCategory) throws Exception {
		return correctSpelledLettersSpacesNumbers(newProductCategory) && BlStoreManager.updateProductDetails(sm, oldProduct, newProduct, amount,newProductCategory);
	}

	public static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) throws Exception {
		return BlStoreManager.addPolicyToProduct(sm, policy, product);
	}

	public static boolean addDiscountToProduct(StoreManager sm, PurchasePolicy discountTree, Product product) throws Exception {
		return BlStoreManager.addDiscountToProduct(sm, discountTree, product);
	}
	public static boolean changeProductType(StoreOwner so,PurchaseType type,Product product) throws Exception
	{
		return BlStoreOwner.changeProductType(so,type,product);
	}
	public static boolean changeProductType(StoreManager sm,PurchaseType type,Product product) throws Exception
	{
		return BlStoreManager.changeProductType(sm,type,product);
	}
	
	public static boolean addNewStoreOwner(StoreManager sm, Subscriber owner) throws Exception {
		return BlStoreManager.addNewStoreOwner(sm, owner);
	}

	public static boolean addNewManager(StoreManager oldMan, Subscriber newMan) throws Exception {
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

	public static boolean addProductToStore(StoreOwner so, Product product, int amount,String category) throws Exception {
		return correctSpelledLettersSpacesNumbers(category) && BlStoreOwner.addProductToStore(so, product, amount,category);
	}

	public static boolean deleteProductFromStore(StoreOwner so, Product product) throws Exception {
		return BlStoreOwner.deleteProductFromStore(so, product);
	}

	public static boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount,String newProductCategory) throws Exception {
		return correctSpelledLettersSpacesNumbers(newProductCategory) && BlStoreOwner.updateProductDetails(so, oldProduct, newProduct, amount,newProductCategory);
	}

	public static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product) throws Exception {
		return BlStoreOwner.addPolicyToProduct(so, policy, product);
	}

	public static boolean addDiscountToProduct(StoreOwner so, PurchasePolicy discountTree, Product product) throws Exception {
		return BlStoreOwner.addDiscountToProduct(so, discountTree, product);
	}
	
	public static boolean addDiscountToCategoryStore(StoreOwner so, PurchasePolicy discountTree, String category) throws Exception {
		return correctSpelledLettersSpacesNumbers(category) && BlStoreOwner.addDiscountToCategoryStore(so, discountTree, category);
	}
	public static boolean addDiscountToCategoryStore(StoreManager manager, PurchasePolicy discountTree, String category) throws Exception {
		return correctSpelledLettersSpacesNumbers(category) && BlStoreManager.addDiscountToCategoryStore(manager, discountTree, category);
	}

	public static boolean addNewStoreOwner(StoreOwner oldSo, Subscriber newSo) throws Exception {
		return BlStoreOwner.addNewStoreOwner(oldSo, newSo);
	}

	public static boolean addNewManager(StoreOwner so, Subscriber manager) throws Exception {
		return BlStoreOwner.addNewManager(so, manager);
	}

	public static boolean closeStore(StoreOwner so) throws Exception {
		return BlStoreOwner.closeStore(so);
	}

	public static boolean openStore(StoreOwner so) throws Exception {
		return BlStoreOwner.openStore(so);
	}

	public static List<Purchase> getPurchaseHistory(StoreOwner so) {
		return BlStoreOwner.getPurchaseHistory(so);
	}

	public static Store openStore(Subscriber sub, String storeName,  int gradeing, boolean isOpen) throws Exception {
		
		return correctSpelledLettersSpacesNumbers(storeName) ? BlSubscriber.openStore(sub,storeName,gradeing,new HashMap<Product, Integer>(),new LinkedList<Purchase>(),isOpen) : null;
	}

	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) throws Exception {
		return BlSystemAdministrator.removeSubscriber(sa, s);
	}

	public static List<Purchase> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) throws Exception {
		return BlSystemAdministrator.viewSubscriberHistory(sa, s);
	}

	public static List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store) throws Exception {
		return BlSystemAdministrator.viewStoreHistory(sa, store);
	}

	public static Subscriber signUp(Guest g, String username, String password, String fullName, String address,
			String phone, String creditCardNumber) throws Exception {
		return BlGuest.signUp(g, username, password, fullName, address, phone, creditCardNumber);
	}

	public static Subscriber signIn(Guest g, String username, String password) throws Exception {
		return BlGuest.signIn(g, username, password);
	}

	public static void expiredProducts(StoreOwner so) throws Exception{
		BlStoreOwner.expiredProducts(so);
	}

	public static void expiredProducts(StoreManager sm) throws Exception{
		BlStoreManager.expiredProducts(sm);
	}

	public static boolean changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp) throws Exception{
		return BlStoreOwner.changeStorePurchasePolicy(so, pp);
	}

	public static boolean changeStorePurchasePolicy(StoreManager sm, PurchasePolicy pp) throws Exception{
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
		boolean ok = str != null && str.matches("[0-9a-zA-Z\\s]+");
		if(!ok)
			logger.info("\ncorrectSpelledLettersSpacesNumbers FAIL\nString data - " + str + "\n");
		return ok;
	}

	static boolean correctSpelledLettersSpaces(String str) {
		boolean ok = str != null && str.matches("[a-zA-Z\\s]+");
		if(!ok)
			logger.info("\ncorrectSpelledLettersSpaces FAIL\nString data - " + str + "\n");
		return ok;
	}

	static boolean correctSpelledNumbers(String str){
		boolean ok = str != null && str.matches("[0-9]+");
		if(!ok)
			logger.info("\ncorrectSpelledNumbers FAIL\nString data - " + str + "\n");
		return ok;
	}

	static boolean legalPassword(String pass) {
		return correctSpelledLettersSpacesNumbers(pass);
	}

	//credit card must have between 8 to 16 digits and digits only!
	static boolean legalCreditCard(String creditCardNumber){
		if(creditCardNumber == null || creditCardNumber.length() < 8 || creditCardNumber.length() > 16)
			return false;
		String regex = "[0-9]+";
		boolean ok = creditCardNumber.matches(regex); 
		if(!ok)
			logger.info("\nlegalCreditCard FAIL\nCreditCard data - " + creditCardNumber+ "\n");
		return ok;
	}

	//should be update in the future
	static boolean legalAddress(String buyerAddress){
		return correctSpelledLettersSpacesNumbers(buyerAddress);
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
	
	public static List<Store> getAllSubscriberStores(Subscriber sub){
		LinkedList<Store> stores = new LinkedList<Store>();
		for (StoreOwner owner : sub.getOwner()) {
			Store s = owner.getStore();
			if(!stores.contains(s))
				stores.add(s);
		}
		for (StoreManager man : sub.getManager()) {
			Store s = man.getStore();
			if(!stores.contains(s))
				stores.add(s);
		}
		return stores;
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
		if (criterion.equals("Category") && p.getCategory().getName().equals(str))
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
		if(p.getPurchasePolicy() == null || !(p.getType() instanceof ImmediatelyPurchase))
			return false;
		return true;
	}
	
	static boolean isLotteryPurchase(Product p){
		if(p.getPurchasePolicy() == null || !(p.getType() instanceof LotteryPurchase))
			return false;
		return true;
	}
	
	static String getCreditCard(Guest g){
		return allUsersWithTheirCreditCards.get(g).get(0);
	}
	
	public static Subscriber getSubscriberFromUsername(String usrname){
		Subscriber to_return = null;
		for(Subscriber s : allSubscribers){
			if(s.getUsername().equals(usrname)){
				to_return = s;
				break;
			}
		}
		return to_return;
	}
	
	
	public static int getIndexByUN(Subscriber sub){
		int ans = 0;
		for (Subscriber s : allSubscribers) {
			if(s.getUsername().equals(sub.getUsername()))
				return ans;
			ans++;
		}
		
		return -1;
	}

	public static StoreOwner getStoreOwnerForStorePerUsername(int storeId, String username) {
		for (Subscriber s : allSubscribers) {
			if(s.getUsername().equals(username)){
				List<StoreOwner> owner = s.getOwner();
				for (StoreOwner storeOwner : owner) {
					if(storeOwner.getStore().getStoreId() == storeId){
						return storeOwner;
					}
				}
			}
		}
		return null;
		
	}
	
	public static Map<Product, Integer> getProductAndAmountPerStoreId(int storeId){
		Map<Store, Map<Product, Integer>> m = getAllStoresWithThierProductsAndAmounts();
		for (Map.Entry<Store, Map<Product, Integer>> entry : m.entrySet())
		{
		    if(entry.getKey().getStoreId() == storeId){
		    	return entry.getValue();
		    }
		}
		return null;
	}

	public static Product getProductFromProdId(int prodId) {
		Map<Store, Map<Product, Integer>> m = getAllStoresWithThierProductsAndAmounts();
		for (Map.Entry<Store, Map<Product, Integer>> entry : m.entrySet())
		{
			for(Map.Entry<Product, Integer> subEntry : entry.getValue().entrySet()){
				if(subEntry.getKey().getId() == prodId){
					return subEntry.getKey();
				}
			}
		}

		return null;
	}
	
	public static int getAmountFromProdId(int prodId) {
		Map<Store, Map<Product, Integer>> m = getAllStoresWithThierProductsAndAmounts();
		for (Map.Entry<Store, Map<Product, Integer>> entry : m.entrySet())
		{
			for(Map.Entry<Product, Integer> subEntry : entry.getValue().entrySet()){
				if(subEntry.getKey().getId() == prodId){
					return subEntry.getValue().intValue();
				}
			}
		}

		return 0;
	}
	
	public static List<Subscriber> getAllSubscribersWithPotential(){
		List<Subscriber> toReturn = new ArrayList<Subscriber>();
		for (Subscriber subscriber : allSubscribers) {
			toReturn.add(subscriber);
		}
		return toReturn;
	}
	

	public static StoreOwner getStoreOwnerFromUsername(String username, int storeId) {
		Subscriber s = getSubscriberFromUsername(username);
		List<StoreOwner> soList = s.getOwner();
		for (StoreOwner storeOwner : soList) {
			if(storeOwner.getStore().getStoreId() == storeId)
				return storeOwner;
		}
		return null;
	}
	
	public static StoreManager getStoreManagerFromUsername(String username, int storeId) {
		Subscriber s = getSubscriberFromUsername(username);
		List<StoreManager> smList = s.getManager();
		for (StoreManager storeManager : smList) {
			if(storeManager.getStore().getStoreId() == storeId)
				return storeManager;
		}
		return null;
	}

	public static boolean[] getPermitsFromString(String fromJson) {
		String[] permitAsStrings = fromJson.split("d");
		boolean[] toRet = new boolean[NUM_OF_PERMISSIONS];
		for(int i = 0; i < NUM_OF_PERMISSIONS; i++){
			if(permitAsStrings[i].equals("1"))
				toRet[i] = true;
			else if(permitAsStrings[i].equals("0"))
				toRet[i] = false;
		}
		return toRet;
	}
	
	public static List<Product> getAllProducts(){
		List<Product> ans = new ArrayList<Product>();
		
		Map<Store,Map<Product, Integer>> fmap = BlMain.getAllStoresWithThierProductsAndAmounts();
		for (Store s : fmap.keySet())
			ans.addAll(fmap.get(s).keySet());
		
		return ans;
	}
	
	public static SystemAdministrator getSystemAdminFromUsername(String username) {
		SystemAdministrator s = (SystemAdministrator) getSubscriberFromUsername(username);

		return s;
	}
	
	public static Store getStoreFromStoreId(int storeId){
		List<Store> stores = BlMain.getAllStores();
		for (Store store : stores) {
			if(store.getStoreId() == storeId)
				return store;
		}
		return null;
	}
	
}
