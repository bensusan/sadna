package TS_BL;

import java.util.List;
import java.util.Map;
import TS_SharedClasses.*;

public class BlMain{	
	
	public static boolean puchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addProduct(Cart c, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean removeProduct(Cart c, Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean editProduct(Cart c, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean editCart(Cart c, Map<Product, Integer> newCart) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static int updatePrice(DiscountPolicy dp, int price) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public static boolean addProductToCart(Guest g, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean removeProductFromCart(Guest g, Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean editProductInCart(Guest g, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public static boolean purchase(PurchaseType ip, Guest g, int price, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public static boolean isLotteryDone(LotteryPurchase lp) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static void closeCurrentLottery(LotteryPurchase lp) {
		// TODO Auto-generated method stub
		
	}

	
	public static void openNewLottery(LotteryPurchase lp) {
		// TODO Auto-generated method stub
		
	}

	
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

	
	public static boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addNewManager(StoreOwner so, StoreManager manager) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean closeStore(StoreOwner so) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean openStore(StoreOwner so) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static List<Cart> getPurchaseHistory(StoreOwner so) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static Store openStore(Subscriber sub, String storeName, String Description) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static boolean addPurchaseToHistory(Subscriber sub, Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addOwner(Subscriber sub, StoreOwner owner) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean addManager(Subscriber sub, StoreManager manager) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean deleteOwner(Subscriber sub, StoreOwner owner) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean deleteManager(Subscriber sub, StoreManager manager) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static List<Cart> viewStoreHistory(SystemAdministrator sa, Store store) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//More functions that are necessary.
	public static boolean payToStore(Store s, int price){
		//TODO maybe later this function will return false
		s.setMoneyEarned(s.getMoneyEarned() + price);
		return true;
	}
}
