package TS_BL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public interface BlInterface {

	public boolean puchaseCart(Cart c, int creditCardNumber,String buyerAddress);
	public boolean addProduct(Cart c, Product p, int amount);
	public boolean removeProduct(Cart c, Product p);
	public boolean editProduct(Cart c, Product p,int amount);
	public boolean editCart(Cart c, Map<Product,Integer> newCart);
	public int updatePrice(DiscountPolicy dp, int price);
	public boolean addProductToCart(Guest g, Product p,int amount);
	public boolean removeProductFromCart(Guest g, Product p);
	public boolean editProductInCart(Guest g, Product p,int amount);
	public boolean editCart(Guest g, Map<Product,Integer> newCart);
	public boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress);
	public boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress);
	public int updatePrice(HiddenDiscount hd, int price,int code);
	public boolean purchase(PurchaseType ip, Guest g, int price, int amount);
	public int getDiscountedPrice(ImmediatelyPurchase ip, int price);
	public boolean isLotteryDone(LotteryPurchase lp);
	public void closeCurrentLottery(LotteryPurchase lp);
	public void openNewLottery(LotteryPurchase lp);
	public boolean purchase(Product p, Guest g, int price, int amount);
	public boolean purchase(PurchasePolicy pp, Guest g, int price, int amount);
	public boolean canPurchase(PurchasePolicy pp, Guest g);
	public boolean checkInStock(Store s, Product p,int amount);
	public boolean addPurchaseToHistory(Store s, Cart cart);
	public boolean buyProduct(Store s, Product p, int amount);
	public boolean stockUpdate(Store s, Product p, int amount);
	public boolean addProductToStore(StoreManager sm, Product product,int amount);
	public boolean deleteProductFromStore(StoreManager sm, Product product);
	public boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount);
	public boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy,Product product);
	public boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount,Product product);
	public boolean addNewStoreOwner(StoreManager sm, StoreOwner owner);
	public boolean addNewManager(StoreManager oldMan, StoreManager newMan);
	public boolean closeStore(StoreManager sm);
	public boolean openStore(StoreManager sm);
	public List<Purchase> getPurchaseHistory(StoreManager sm);
	public boolean addProductToStore(StoreOwner so, Product product,int amount);
	public boolean deleteProductFromStore(StoreOwner so, Product product);
	public boolean updateProductDetails(StoreOwner so, Product oldProduct, Product newProduct, int amount);
	public boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product);
	public boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product);
	public boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo);
	public boolean addNewManager(StoreOwner so, StoreManager manager);
	public boolean closeStore(StoreOwner so);
	public boolean openStore(StoreOwner so);
	public List<Purchase> getPurchaseHistory(StoreOwner so);
	public Store openStore(Subscriber sub, String storeName,String Description);
	public boolean addPurchaseToHistory(Subscriber sub, Cart cart);
	public boolean addOwner(Subscriber sub, StoreOwner owner);
	public boolean addManager(Subscriber sub, StoreManager manager);
	public boolean deleteOwner(Subscriber sub, StoreOwner owner);
	public boolean deleteManager(Subscriber sub, StoreManager manager);
	public boolean removeSubscriber(SystemAdministrator sa, Subscriber s);
	public List<Purchase> viewSubscriberHistory(SystemAdministrator sa, Subscriber s);
	public List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store);
	
	
	
	//More functions that are necessary.
	public boolean payToStore(Store s, int price);
	public Subscriber signUp(Guest g, String username, String password, String fullName, String address, int phone, int creditCardNumber);
	public Subscriber signIn(Guest g, String username, String password);
	public Subscriber checkIfSubscriberExists(String username);
	public boolean misspelled(String str);
	public boolean legalPassword(String pass);
	public Map<Store, Map<Product, Integer>> getAllStoresWithThierProductsAndAmounts();
	public List<Store> getAllStores();
	public List<Product> findProductByName(String name);
	public List<Product> findProductByCategory(String category);
	public List<Product> findProductByCriterion(String criterion, String str);
	public boolean productInCriterion(String criterion, String str, Product p);
	public void expiredProducts(StoreOwner so);
	public void expiredProducts(StoreManager sm);
	public void changeStorePurchasePolicy(StoreOwner so, PurchasePolicy pp);
	public void changeStorePurchasePolicy(StoreManager so, PurchasePolicy pp);
	
	
}
