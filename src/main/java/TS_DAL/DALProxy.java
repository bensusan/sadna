package TS_DAL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class DALProxy implements DAL{

	private DALReal real = null;
	
	public DALProxy() {}
	
	public List<Subscriber> allSubscribers() throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.allSubscribers();
	}

	public List<StoreOwner> getStoreOwners(String username) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getStoreOwners(username);
	}

	public Store getStoreByStoreId(int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getStoreByStoreId(storeId);
	}

	public List<StoreManager> getSubscriberManagers(String username) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getSubscriberManagers(username);
	}

	public List<Purchase> getMyPurchase(String username) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getMyPurchase(username);
	}

	public boolean isSubscriberExist(String username) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.isSubscriberExist(username);
	}

	public boolean isAdmin(String username) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.isAdmin(username);
	}

	public void removeSubscriber(Subscriber sub) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.removeSubscriber(sub);
		
	}

	public List<Purchase> getStorePurchase(Store store) {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getStorePurchase(store);
	}

	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addPurchaseToHistory(sub,p);
		
	}

	public void removeStoreOwner(String username, int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.removeStoreOwner(username,storeId);
		
	}

	public void deleteStoreManager(String username, int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.deleteStoreManager(username, storeId);
		
	}

	public boolean checkInStock(int productId, int amount) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.checkInStock(productId,amount);
	}

	public Store getProductStore(Product p) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getProductStore(p);
	}

	public List<Product> getAllProductsOfStore(Store store) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getAllProductsOfStore(store);
	}

	public void stockUpdate(Product p, int amount, Store s) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.stockUpdate(p,amount,s);
		
	}

	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.updateMoneyEarned(s,newMoneyEarend);
		
	}

	public List<Category> getAllCategory() {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getAllCategory();
	}

	public Category getCategory(String categoryName) {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getCategory(categoryName);
	}

	public void addProductToStore(Store s, Product product, int amount, String category) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addProductToStore(s,product,amount,category);
		
	}

	public void deleteProductFromStore(Store s, Product product) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.deleteProductFromStore(s,product);
		
	}

	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,
			String newProductCategory) {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.updateProductDetails(s,oldProduct,newProduct,amount,newProductCategory);
		
	}

	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addNewStoreOwner(s,owner);
		
	}

	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addNewStoreManager(s,newMan,permission);
		
	}

	public void updateStore(Store s) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.updateStore(s);
		
	}

	public void addSubscriber(Subscriber s) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addSubscriber(s);
		
	}

	public Subscriber getSubscriber(String username, String password) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getSubscriber(username,password);
	}

	public void addImeddiatleyProductToCart(String username, int productId, int amount, int code) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addImeddiatleyProductToCart(username, productId, amount, code);
	}

	public void removeProductFromCart(String username, int productId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.removeProductFromCart(username,productId);
	}

	public void editProductAmount(String username, int productId, int amount) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.editProductAmount(username, productId, amount);
	}

	public void editProductCode(String username, int productId, int code) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.editProductCode(username, productId, code);
	}

	public void editProductPrice(int productId, int price) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.editProductPrice(productId, price);
	}

	public void closeStore(int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.closeStore(storeId);
	}

	public void openStore(int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.openStore(storeId);
	}

	public void addStore(Store s) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.addStore(s);
	}

	public Map<Product, Integer> getProductAmount(int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getProductAmount(storeId);
	}

	public List<Purchase> getStorePurchase(int storeId) {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getStorePurchase(storeId);
	}



	

	public void removePurchase(Subscriber s, Purchase p) {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.removePurchase(s,p);
	}

	

	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		return real.getDiscountPolicy(policyId);
	}

	public void deleteStore(int storeId) throws Exception {
		if (real == null) {
			real = DALReal.getInstance();
	    }
		real.deleteStore(storeId);
	}

	

}
