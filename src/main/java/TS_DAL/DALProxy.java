package TS_DAL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class DALProxy implements DAL {

	private DAL real = null;

	public DALProxy() {
	}

	private DAL getReal() {
		return real == null ? DALReal.getInstance() : real;
	}

	public List<Subscriber> allSubscribers() throws Exception {
		return getReal().allSubscribers();
	}

	public List<StoreOwner> getStoreOwners(String username) throws Exception {
		return getReal().getStoreOwners(username);
	}

	public Store getStoreByStoreId(int storeId) throws Exception {
		return getReal().getStoreByStoreId(storeId);
	}

	public List<StoreManager> getSubscriberManagers(String username) throws Exception {
		return getReal().getSubscriberManagers(username);
	}

	public List<Purchase> getMyPurchase(String username) throws Exception {
		return getReal().getMyPurchase(username);
	}

	public boolean isSubscriberExist(String username) throws Exception {
		return getReal().isSubscriberExist(username);
	}

	public boolean isAdmin(String username) throws Exception {
		return getReal().isAdmin(username);
	}

	public void removeSubscriber(Subscriber sub) throws Exception {
		getReal().removeSubscriber(sub);
	}

	public List<Purchase> getStorePurchase(Store store) {
		return getReal().getStorePurchase(store);
	}

	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception {
		getReal().addPurchaseToHistory(sub, p);
	}

	public void removeStoreOwner(String username, int storeId) throws Exception {
		getReal().removeStoreOwner(username, storeId);
	}

	public void deleteStoreManager(String username, int storeId) throws Exception {
		getReal().deleteStoreManager(username, storeId);
	}

	public boolean checkInStock(int productId, int amount) throws Exception {
		return getReal().checkInStock(productId, amount);
	}

	public Store getProductStore(Product p) throws Exception {
		return getReal().getProductStore(p);
	}

	public List<Product> getAllProductsOfStore(Store store) throws Exception {
		return getReal().getAllProductsOfStore(store);
	}

	public void stockUpdate(Product p, int amount, Store s) throws Exception {
		getReal().stockUpdate(p, amount, s);
	}

	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception {
		getReal().updateMoneyEarned(s, newMoneyEarend);
	}

	public List<Category> getAllCategory() {
		return getReal().getAllCategory();
	}

	public Category getCategory(String categoryName) {
		return getReal().getCategory(categoryName);
	}

	public void addProductToStore(Store s, Product product, int amount, String category) throws Exception {
		getReal().addProductToStore(s, product, amount, category);
	}

	public void deleteProductFromStore(Store s, Product product) throws Exception {
		getReal().deleteProductFromStore(s, product);
	}

	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,
			String newProductCategory) {
		getReal().updateProductDetails(s, oldProduct, newProduct, amount, newProductCategory);
	}

	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception {
		getReal().addNewStoreOwner(s, owner);
	}

	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception {
		getReal().addNewStoreManager(s, newMan, permission);
	}

	public void updateStore(Store s) throws Exception {
		getReal().updateStore(s);
	}

	public void addSubscriber(Subscriber s) throws Exception {
		getReal().addSubscriber(s);
	}

	public Subscriber getSubscriber(String username, String password) throws Exception {
		return getReal().getSubscriber(username, password);
	}

	public void addImeddiatleyProductToCart(String username, Product p, int amount, int code) throws Exception {
		getReal().addImeddiatleyProductToCart(username, p, amount, code);
	}

	public void removeProductFromCart(String username, Product p) throws Exception {
		getReal().removeProductFromCart(username, p);
	}

	public void editProductAmount(String username, Product p, int amount) throws Exception {
		getReal().editProductAmount(username, p, amount);
	}

	public void editProductCode(String username, Product p, int code) throws Exception {
		getReal().editProductCode(username, p, code);
	}

	public void editProductPrice(int productId, int price) throws Exception {
		getReal().editProductPrice(productId, price);
	}

	public void closeStore(int storeId) throws Exception {
		getReal().closeStore(storeId);
	}

	public void openStore(int storeId) throws Exception {
		getReal().openStore(storeId);
	}

	public void addStore(Store s) throws Exception {
		getReal().addStore(s);
	}

	public Map<Product, Integer> getProductAmount(int storeId) throws Exception {
		return getReal().getProductAmount(storeId);
	}

	public List<Purchase> getStorePurchase(int storeId) {
		return getReal().getStorePurchase(storeId);
	}

	public PurchasePolicy getPurchasePolicy(int policyId, int type) throws Exception {
		return getReal().getPurchasePolicy(policyId, type);
	}

	public List<PurchasePolicy> getSubPolicies(int policyId) {
		return getReal().getSubPolicies(policyId);
	}

	public void removePurchase(Subscriber s, Purchase p) {
		getReal().removePurchase(s, p);
	}

	public void deleteStore(int storeId) {
		getReal().deleteStore(storeId);
	}

	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception {
		return getReal().getDiscountPolicy(policyId);
	}

	public PurchaseType getPurchaseType(int productId) {
		return getReal().getPurchaseType(productId);
	}

	public Subscriber getSubscriberIfExists(String username) throws Exception {
		return getReal().getSubscriberIfExists(username);
	}

	public List<Store> getStores() throws Exception {
		return getReal().getStores();
	}

	public void addLotteryProductToCart(String username, Product p, int money) {
		getReal().addLotteryProductToCart(username, p, money);
	}

	public void setNewCartForSubscriber(String username, Cart cart) {
		getReal().setNewCartForSubscriber(username, cart);
	}

	public int getAmountOfProduct(Product myProduct) {
		return getReal().getAmountOfProduct(myProduct);
	}

	public boolean isCategoryExists(String category) {
		return getReal().isCategoryExists(category);
	}

	public boolean isProductExistsInStore(Store s, Product product) {
		return getReal().isProductExistsInStore(s, product);
	}

	public boolean isStoreOwnerExists(Store s, Subscriber owner) {
		return getReal().isStoreOwnerExists(s, owner);
	}

	public boolean isStoreManagerExists(Store s, Subscriber newMan) {
		return getReal().isStoreManagerExists(s, newMan);
	}

	public List<Purchase> getStoreHistory(Store s) {
		return getReal().getStoreHistory(s);
	}

}
