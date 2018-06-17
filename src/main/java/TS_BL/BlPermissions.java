package TS_BL;

import java.util.Calendar;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;
import static TS_BL.BlMain.dalRef;

public class BlPermissions {

	// Here we will implement Store's owner and Store's manager permissions
	static boolean addProductToStore(Store s, Product product, int amount, String category) throws Exception {
		if (s == null || product == null)
			throw new Exception("something went wrong");
		if (amount <= 0)
			throw new Exception("amount must be greater than 0");
		if (!dalRef.isCategoryExists(category))
			throw new Exception("category does not exist");
		List<Category> allCategory = dalRef.getAllCategory();
		for (Category c : allCategory) {
			if (c.getName().equals(category)) {
				List<Product> prod = c.getProducts();
				prod.add(product);
				c.setProducts(prod);
				product.setCategory(c);
				break;
			}
		}
		product.setStore(s);

		if (dalRef.isProductExistsInStore(s, product)) {
			int currentAmount = dalRef.getAmountOfProduct(product);
			dalRef.updateProductDetails(s, product, product, currentAmount + amount, category);
			return true;
		}
		dalRef.addProductToStore(s, product, amount, category);
		return true;
	}

	static boolean deleteProductFromStore(Store s, Product product) throws Exception {
		if (s == null || product == null || !dalRef.isProductExistsInStore(s, product))
			throw new Exception("something went wrong");
		dalRef.deleteProductFromStore(s, product);
		// product.getCategory().getProducts().remove(product);
		product.setStore(null);
		product.setCategory(null);
		return true;
	}

	static boolean updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,
			String newProductCategory) throws Exception {
		if (s == null || oldProduct == null || newProduct == null)
			throw new Exception("something went wrong");
		if (amount <= 0)
			throw new Exception("amount must be greater than 0");
		if (!dalRef.isProductExistsInStore(s, oldProduct))
			throw new Exception("this product doesn't belongs to this store");

		int temp = dalRef.getAmountOfProduct(oldProduct);

		try {
			return deleteProductFromStore(s, oldProduct)
					&& addProductToStore(s, newProduct, amount, newProductCategory);
		} catch (Exception e) {
			addProductToStore(s, oldProduct, temp, newProductCategory);
			throw e;
		}
	}

	static boolean addPolicyToProduct(Store s, PurchasePolicy policy, Product product) throws Exception {
		if (s == null || product == null || !dalRef.isProductExistsInStore(s, product))
			throw new Exception("something went wrong");
		String cTemp = product.getCategory().getName();
		int temp = dalRef.getAmountOfProduct(product);
		if (deleteProductFromStore(s, product)) {
			product.setPurchasePolicy(policy);
			addProductToStore(s, product, temp, cTemp);
			return true;
		}
		return false;
	}

	static boolean addDiscountToProduct(Store s, PurchasePolicy discountTree, Product product) throws Exception {
		if (s == null || product == null || discountTree == null || !dalRef.isProductExistsInStore(s, product))
			throw new Exception("something went wrong");
//		PurchaseType pt = product.getType();
		PurchaseType pt = dalRef.getPurchaseType(product.getId());
		if (pt instanceof ImmediatelyPurchase) {
			String cTemp = product.getCategory().getName();
			int temp = dalRef.getAmountOfProduct(product);
			if (deleteProductFromStore(s, product)) {
				((ImmediatelyPurchase) pt).setDiscountTree(discountTree);
				addProductToStore(s, product, temp, cTemp);
				return true;
			}
			return false;
		}
		throw new Exception("Discount can be added only to products that are for immediate purchase");
	}

	static boolean addNewStoreOwner(Store s, Subscriber owner) throws Exception {
		if (s == null || owner == null)
			throw new Exception("something went wrong");
		// StoreOwner so = new StoreOwner(s);
		if (dalRef.isStoreOwnerExists(s, owner))
			throw new Exception("couldn't add new owner");
		dalRef.addNewStoreOwner(s, owner);
		// List<StoreOwner> owners = s.getMyOwners();
		// if(!owners.add(so))
		// throw new Exception("couldn't add new owner");
		// s.setMyOwners(owners);
		// owners=owner.getOwner();
		// if(!owners.add(so))
		// throw new Exception("couldn't add new owner");
		// owner.setOwner(owners);
		// owner.setStore(s);
		return true;
	}

	static boolean addNewManager(Store s, Subscriber newMan) throws Exception {
		if (s == null || newMan == null)
			throw new Exception("something went wrong");

		if (dalRef.isStoreManagerExists(s, newMan))
			throw new Exception("couldn't add new owner");
		dalRef.addNewStoreOwner(s, newMan);
		// StoreManager sm = new StoreManager(s);
		// List<StoreManager> managers = s.getMyManagers();
		// if(!managers.add(sm))
		// throw new Exception("couldn't add new manager");
		// s.setMyManagers(managers);
		// managers=newMan.getManager();
		// if(!managers.add(sm))
		// throw new Exception("couldn't add new manager");
		// newMan.setManager(managers);

		// newMan.setStore(s);
		return true;
	}

	static boolean closeStore(Store s) throws Exception {
		Store sFromDal = dalRef.getStoreByStoreId(s.getStoreId());
		if (s != null && sFromDal.getIsOpen()) {
			sFromDal.setIsOpen(false);
			dalRef.closeStore(sFromDal.getStoreId());
			s = sFromDal;
			return true;
		} else
			return false;
	}

	static boolean openStore(Store s) throws Exception {
		Store sFromDal = dalRef.getStoreByStoreId(s.getStoreId());
		if (s != null && !sFromDal.getIsOpen()) {
			sFromDal.setIsOpen(true);
			dalRef.openStore(sFromDal.getStoreId());
			s = sFromDal;
			return true;
		}
		else
			return false;
	}

	static List<Purchase> getPurchaseHistory(Store s) {
		return s != null ? dalRef.getStoreHistory(s) : null;
	}

	static void expiredProducts(Store s) throws Exception {
		if (s == null)
			return;
		List<Product> allProducts = dalRef.getAllProductsOfStore(s);
		for (Product product : allProducts) {
			PurchaseType pt = product.getType();
			if (pt instanceof LotteryPurchase) {
				Calendar today = Calendar.getInstance();
				today.set(Calendar.HOUR_OF_DAY, 0);
				LotteryPurchase lpt = ((LotteryPurchase) pt);
				if (lpt.getLotteryEndDate().before(today.getTime())) {
					BlLotteryPurchase.closeCurrentLottery(lpt);
				}
			}
		}
	}

	static boolean changeStorePurchasePolicy(Store s, PurchasePolicy pp) throws Exception {
		if (s == null || pp == null)
			throw new Exception("something went wrong");
		s.setStorePolicy(pp);
		dalRef.updateStore(s);
		return true;
	}
	public static boolean addPolicyToCategoryStore(Store store, PurchasePolicy policy, String category)throws Exception {
		if (store == null || !dalRef.isCategoryExists(category) || policy == null)
			throw new Exception("something went wrong");
		Store s = dalRef.getStoreByStoreId(store.getStoreId());
		Map<Category, PurchasePolicy> categoryPolicys = s.getCategoryPolicy();
		Category cat = null;
		List<Category> allCategory = dalRef.getAllCategory();
		for (Category c : allCategory) {
			if (c.getName().equals(category)) {
				cat = c;
				break;
			}
		}
		if (cat == null)
			throw new Exception("something went wrong");
		categoryPolicys.put(cat, policy);
		dalRef.updateStore(s);
		store = s;
		return true;
	}

	public static boolean addDiscountToCategoryStore(Store store, PurchasePolicy discountTree, String category)
			throws Exception {
		if (store == null || !dalRef.isCategoryExists(category) || discountTree == null)
			throw new Exception("something went wrong");
		Store s = dalRef.getStoreByStoreId(store.getStoreId());
		Map<Category, PurchasePolicy> categoryDiscounts = s.getCategoryDiscounts();
		Category cat = null;
		List<Category> allCategory = dalRef.getAllCategory();
		for (Category c : allCategory) {
			if (c.getName().equals(category)) {
				cat = c;
				break;
			}
		}
		if (cat == null)
			throw new Exception("something went wrong");
		categoryDiscounts.put(cat, discountTree);
		dalRef.updateStore(s);
		store = s;
		return true;
	}

	public static boolean changeProductType(Store s, PurchaseType type, Product product) throws Exception {
		if (s == null || type == null || product == null || !dalRef.isProductExistsInStore(s, product))
			throw new Exception("something went wrong");
		String cTemp = product.getCategory().getName();
		int temp = dalRef.getAmountOfProduct(product);
		if (deleteProductFromStore(s, product)) {
			product.setType(type);
			addProductToStore(s, product, temp, cTemp);
			return true;
		}
		return false;
	}

	
}
