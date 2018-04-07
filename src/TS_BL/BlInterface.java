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
	public boolean updateProductDetails(StoreManager sm, Product newProduct, int amount);
	public boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy,Product product);
	public boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount,Product product);
	public boolean addNewStoreOwner(StoreManager sm, StoreOwner owner);
	public boolean addNewManager(StoreManager oldMan, StoreManager newMan);
	public boolean closeStore(StoreManager sm);
	public boolean openStore(StoreManager sm);
	public List<Cart> getPurchaseHistory(StoreManager sm);
	public boolean addProductToStore(StoreOwner so, Product product,int amount);
	public boolean deleteProductFromStore(StoreOwner so, Product product);
	public boolean updateProductDetails(StoreOwner so, Product newProduct, int amount);
	public boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product);
	public boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product);
	public boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo);
	public boolean addNewManager(StoreOwner so, StoreManager manager);
	public boolean closeStore(StoreOwner so);
	public boolean openStore(StoreOwner so);
	public List<Cart> getPurchaseHistory(StoreOwner so);
	public Store openStore(Subscriber sub, String storeName,String Description);
	public boolean addPurchaseToHistory(Subscriber sub, Cart cart);
	public boolean addOwner(Subscriber sub, StoreOwner owner);
	public boolean addManager(Subscriber sub, StoreManager manager);
	public boolean deleteOwner(Subscriber sub, StoreOwner owner);
	public boolean deleteManager(Subscriber sub, StoreManager manager);
	public boolean removeSubscriber(SystemAdministrator sa, Subscriber s);
	public List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s);
	public List<Cart> viewStoreHistory(SystemAdministrator sa, Store store);
	
	
}
