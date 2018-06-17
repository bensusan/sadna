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
	public void removeSubscriber(String username) throws Exception;
	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception;
	public void removeStoreOwner(String username, int storeId) throws Exception;
	public void deleteStoreManager(String username, int storeId) throws Exception;
	public boolean checkInStock(int productId, int amount) throws Exception;
	public Store getProductStore(int productId) throws Exception;
	public List<Product> getAllProductsOfStore(int storeId) throws Exception;
	public void stockUpdate(Product p, int amount,Store s) throws Exception;
	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception;
	public List<Category>getAllCategory();
	public Category getCategory(String categoryName);
	public void addProductToStore(Store s, Product product, int amount,  String category) throws Exception;
	public void deleteProductFromStore(int storeId, int productId) throws Exception;
	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory) throws Exception;
	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception;
	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception;
	public void updateStore(Store s) throws Exception;
	public void addSubscriber(Subscriber s) throws Exception;
	public Subscriber getSubscriber(String username, String password)throws Exception;
	public void addImeddiatleyProductToCart(String username, Product p, int amount, int code) throws Exception;
	public void removeProductFromCart(String username, int productId) throws Exception;
	public void editProductAmount(String username, Product p, int amount) throws Exception;
	public void editProductCode(String username, Product p, int code) throws Exception;
	public void editProductPrice(int productId, int price) throws Exception;
	public void closeStore(int storeId) throws Exception;
	public void openStore(int storeId) throws Exception;
	public void addStore(Store s) throws Exception;
	public  Map<Product,Integer> getProductAmount(int storeId) throws Exception;
	public  List<Purchase> getStorePurchase(int storeId) throws Exception;
	public PurchasePolicy getPurchasePolicy(int policyId) throws Exception;
	public List<PurchasePolicy> getSubPolicies(int policyId);
	public void removePurchase(int purchaseId) throws Exception;
	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception;
	public PurchaseType getPurchaseType(int productId) throws Exception;
	public Subscriber getSubscriberIfExists(String username) throws Exception;
	public List<Store> getStores() throws Exception;
	public void addLotteryProductToCart(String username, Product p, int money);
//	public void setNewCartForSubscriber(String username, Cart cart);
	public int getAmountOfProduct(int storeId, int productId) throws Exception;
	public boolean isCategoryExists(String category);
	public List<StoreOwner> getStoreOwnersFromStore(int storeId) throws Exception;
	public List<StoreManager> getStoreManagersFromStore(int storeId) throws Exception;
	public PurchasePolicy getStorePolicy(int storeId) throws Exception;
	public Map<Category, PurchasePolicy> getStoreCategoryDiscount(int storeId) throws Exception;
	public Product getProductById(int productId) throws Exception;
	public int getNextProductId() throws Exception;
	public int getNextStoreId() throws Exception;
	public int getNextPolicyId() throws Exception;
	public int getNextPurchaseId() throws Exception;
	
	
}
