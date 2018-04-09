package TS_BL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.Cart;
import TS_SharedClasses.DiscountPolicy;
import TS_SharedClasses.Guest;
import TS_SharedClasses.Product;
import TS_SharedClasses.PurchasePolicy;
import TS_SharedClasses.Store;
import TS_SharedClasses.StoreManager;
import TS_SharedClasses.StoreOwner;

public class BlAmit {

	//start new functions
	public static boolean payToStore(Store s, int price){
		//TODO maybe later this function will return false
		s.setMoneyEarned(s.getMoneyEarned() + price);
		return true;
	}
	
	//end of new functions
	
	
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
		return s.getProducts().get(p) != null && s.getProducts().get(p) >= amount;
	}

	public static boolean addPurchaseToHistory(Store s, Cart cart) {
		List<Cart> res = s.getPurchaseHistory();
		if(!res.add(cart))
			return false;
		s.setPurchaseHistory(res);
		return true;
	}


	public static boolean buyProduct(Store s, Product p, int amount) {
		return BlMain.checkInStock(s, p, amount) && BlMain.stockUpdate(s, p, s.getProducts().get(p) - amount) ;  
	}


	public static boolean stockUpdate(Store s, Product p, int amount) {
		if(amount < 0 || !BlMain.checkInStock(s, p, amount))
			return false;
		Map<Product, Integer> products = s.getProducts();
		products.put(p, products.get(p) - amount);
		if(products.get(p) == 0)
			products.remove(p);
		return true;
	}
	
	public static boolean addProductToStore(StoreManager sm, Product product, int amount) {
		return sm.getPremisions()[BlPermissions.addDiscountToProduct] && BlPermissions.addProductToStore(sm.getStore(), product, amount);
	}


	public static boolean deleteProductFromStore(StoreManager sm, Product product) {
		return sm.getPremisions()[BlPermissions.deleteProductFromStore] && BlPermissions.deleteProductFromStore(sm.getStore(), product);
	}

	public static boolean updateProductDetails(StoreManager sm, Product oldProduct, Product newProduct, int amount) {
		return sm.getPremisions()[BlPermissions.updateProductDetails] && BlPermissions.updateProductDetails(sm.getStore(), oldProduct, newProduct, amount);
	}


	public static boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) {
		return sm.getPremisions()[BlPermissions.addPolicyToProduct] && BlPermissions.addPolicyToProduct(sm.getStore(), policy, product);
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
}
