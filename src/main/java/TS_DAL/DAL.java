package TS_DAL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public interface DAL {
	public List<Subscriber> allSubscribers() throws Exception;
	public List<StoreOwner> getStoreOwners(String username) throws Exception;
	public Store getStoreByStoreId(int storeId) throws Exception;
	public List<StoreManager> getSubscriberManagers(String username) throws Exception;
	public List<Purchase> getMyPurchase(String username) throws Exception;
	public boolean isSubscriberExist(String username) throws Exception;
	public boolean isAdmin(String username) throws Exception;
	public void removeSubscriber(Subscriber sub) throws Exception;
	public List<Purchase> getStorePurchase(Store store);
	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception;
	public void removeStoreOwner(String username, int storeId) throws Exception;
	public void deleteStoreManager(String username, int storeId) throws Exception;
	public boolean checkInStock(int productId, int amount) throws Exception;
	public Store getProductStore(Product p) throws Exception;
	public List<Product> getAllProductsOfStore(Store store) throws Exception;
	public void stockUpdate(Product p, int amount,Store s) throws Exception;
	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception;
	public List<Category>getAllCategory();
	public Category getCategory(String categoryName);
	public void addProductToStore(Store s, Product product, int amount,String category) throws Exception;
	public void deleteProductFromStore(Store s, Product product) throws Exception;
	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory);
	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception;
	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception;
	public void updateStore(Store s) throws Exception;
	public void addSubscriber(Subscriber s) throws Exception;
	public Subscriber getSubscriber(String username, String password)throws Exception;
	public void addImeddiatleyProductToCart(String username, int productId, int amount, int code) throws Exception;
	public void removeProductFromCart(String username, int productId) throws Exception;
	public void editProductAmount(String username, int productId, int amount) throws Exception;
	public void editProductCode(String username, int productId, int code) throws Exception;
	public void editProductPrice(int productId, int price) throws Exception;
	public void closeStore(int storeId) throws Exception;
	public void openStore(int storeId) throws Exception;
	public void addStore(Store s) throws Exception;
	public  Map<Product,Integer> getProductAmount(int storeId) throws Exception;
	public  List<Purchase> getStorePurchase(int storeId);
	public PurchasePolicy getPurchasePolicy(int policyId, int type) throws Exception;
	public List<PurchasePolicy> getSubPolicies(int policyId);
	public void removePurchase(Subscriber s, Purchase p);
	public void deleteStore(int storeId);
	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception;
	public PurchaseType getPurchaseType(int productId);

}
