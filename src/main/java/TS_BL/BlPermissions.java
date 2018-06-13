package TS_BL;

import java.util.Calendar;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;
import static TS_BL.BlMain.dalRef;

public class BlPermissions {

	//Here we will implement Store's owner and Store's manager permissions
	static boolean addProductToStore(Store s, Product product, int amount,String category) throws Exception {
		if(s == null || product == null)
			throw new Exception("something went wrong");
		if(amount <= 0)
			throw new Exception("amount must be greater than 0");
		if(!BlMain.getAllCategorys().contains(category))
			throw new Exception("category does not exist");
		for(Category c:BlMain.allCategory)
		{
			if(c.getName().equals(category))
			{
				List<Product>prod=c.getProducts();
				prod.add(product);
				c.setProducts(prod);
				product.setCategory(c);
				break;
			}
		}
		product.setStore(s);
		if(s.getProducts().get(product) != null)
			return s.getProducts().put(product, s.getProducts().get(product)+ amount) != null;
		return s.getProducts().put(product, amount) == null;
	}


	static boolean deleteProductFromStore(Store s, Product product) throws Exception {
		if(s == null || product == null || s.getProducts().remove(product) == null)
			throw new Exception("something went wrong");
		product.getCategory().getProducts().remove(product);
		product.setStore(null);
		product.setCategory(null);
		return true;
	}


	static boolean updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory) throws Exception {
		if(s == null || oldProduct == null || newProduct == null)
			throw new Exception("something went wrong");
		if(amount <= 0)
			throw new Exception("amount must be greater than 0");
		if(!s.getProducts().containsKey(oldProduct))
			throw new Exception("this product doesn't belongs to this store");
		
		int temp = s.getProducts().get(oldProduct);
		
		try {
			return deleteProductFromStore(s, oldProduct) && addProductToStore(s, newProduct, amount,newProductCategory);
		} catch (Exception e) {
			addProductToStore(s, oldProduct, temp,newProductCategory);
			throw e;
		}
	}


	static boolean addPolicyToProduct(Store s, PurchasePolicy policy, Product product) throws Exception {
		if(s == null  || product == null ||s.getProducts().get(product) == null)
			throw new Exception("something went wrong");
		product.setPurchasePolicy(policy);
		return true;
	}


	static boolean addDiscountToProduct(Store s, PurchasePolicy discountTree, Product product) throws Exception {
		if(s == null || product == null||discountTree==null || s.getProducts().get(product) == null)
			throw new Exception("something went wrong");
		PurchaseType pt = product.getType(); 
		if(pt instanceof ImmediatelyPurchase){
			((ImmediatelyPurchase) pt).setDiscountTree(discountTree);
			return true;
		}
		throw new Exception("discount can be added only to products that are for immediate purchase");
	}


	static boolean addNewStoreOwner(Store s, Subscriber owner) throws Exception {
		if(s == null || owner == null)
			throw new Exception("something went wrong");
		StoreOwner so = new StoreOwner(s);
		List<StoreOwner> owners = s.getMyOwners();
		if(!owners.add(so))
			throw new Exception("couldn't add new owner");
		s.setMyOwners(owners);
		owners=owner.getOwner();
		if(!owners.add(so))
			throw new Exception("couldn't add new owner");
		owner.setOwner(owners);
	//	owner.setStore(s);
		return true;
	}


	static boolean addNewManager(Store s, Subscriber newMan) throws Exception {
		if(s == null || newMan == null)
			throw new Exception("something went wrong");
		StoreManager sm = new StoreManager(s);
		List<StoreManager> managers = s.getMyManagers();
		if(!managers.add(sm))
			throw new Exception("couldn't add new manager");
		 s.setMyManagers(managers);
		 managers=newMan.getManager();
		 if(!managers.add(sm))
				throw new Exception("couldn't add new manager");
		 newMan.setManager(managers);
		 
		//newMan.setStore(s);
		 return true;
	}

	static boolean closeStore(Store s) {
		if(s != null && s.getIsOpen()){
			s.setIsOpen(false);
			return true;
		}
		else
			return false;
	}

	static boolean openStore(Store s) {
		if(s != null && !s.getIsOpen()){
			s.setIsOpen(true);
			return true;
		}
		
		else
			return false;
	}

	static List<Purchase> getPurchaseHistory(Store s) {
		return s != null ? s.getPurchaseHistory() : null;
	}
	
	static void expiredProducts(Store s){
		if(s == null)
			return;
		for (Product product : s.getProducts().keySet()) {
			PurchaseType pt = product.getType(); 
			if(pt instanceof LotteryPurchase){
				Calendar today = Calendar.getInstance();
				today.set(Calendar.HOUR_OF_DAY, 0);
				LotteryPurchase lpt = ((LotteryPurchase)pt); 
				if(lpt.getLotteryEndDate().before(today.getTime())){
					BlLotteryPurchase.closeCurrentLottery(lpt);
				}
			}
		}
	}

	static boolean changeStorePurchasePolicy(Store s, PurchasePolicy pp) throws Exception{
		if(s == null || pp == null)
			throw new Exception("something went wrong");
		s.setStorePolicy(pp);
		dalRef.updateStore(s);
 		return true;
	}

	public static boolean addDiscountToCategoryStore(Store store, PurchasePolicy discountTree, String category) throws Exception {
		if(store == null || !BlMain.getAllCategorys().contains(category)||discountTree==null)
			throw new Exception("something went wrong");
		Map<Category, PurchasePolicy> categoryDiscounts = store.getCategoryDiscounts();
		Category cat=null;
		for (Category c:BlMain.allCategory)
		{
			if(c.getName().equals(category))
			{
				cat=c;
			}
		}
		if (cat==null)
			throw new Exception("something went wrong");
		categoryDiscounts.put(cat, discountTree);
		return true;
	}


	public static boolean changeProductType(Store s, PurchaseType type, Product product) throws Exception {
		if(s == null || type == null||product==null||!s.getProducts().containsKey(product))
			throw new Exception("something went wrong");
		product.setType(type);
		return true;
	}

}
