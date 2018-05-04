package TS_BL;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import TS_SharedClasses.*;

public class BlPermissions {

	//Here we will implement Store's owner and Store's manager permissions
	static boolean addProductToStore(Store s, Product product, int amount) {
		if(s == null || product == null || amount <= 0 || product.getStore() != null)
			return false;
		product.setStore(s);
		if(s.getProducts().get(product) != null)
			return s.getProducts().put(product, s.getProducts().get(product)+ amount) != null;
		return s.getProducts().put(product, amount) == null;
	}


	static boolean deleteProductFromStore(Store s, Product product) {
		if(s == null || product == null || s.getProducts().remove(product) == null)
			return false;
		product.setStore(null);
		return true;
	}


	static boolean updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount) {
		return s != null && oldProduct != null && newProduct != null && amount > 0 && deleteProductFromStore(s, oldProduct) && addProductToStore(s, newProduct, amount);
	}


	static boolean addPolicyToProduct(Store s, PurchasePolicy policy, Product product) {
		if(s == null || policy == null || product == null ||s.getProducts().get(product) == null)
			return false;
		product.setPurchasePolicy(policy);
		return true;
	}


	static boolean addDiscountToProduct(Store s, DiscountPolicy discount, Product product) {
		if(s == null || discount == null || product == null || s.getProducts().get(product) == null)
			return false;
		PurchaseType pt = product.getPurchasePolicy().getPurchaseType(); 
		if(pt instanceof ImmediatelyPurchase){
			((ImmediatelyPurchase) pt).setDiscountPolicy(discount);
			return true;
		}
		return false;
	}


	static boolean addNewStoreOwner(Store s, Subscriber owner) {
		if(s == null || owner == null)
			return false;
		StoreOwner so = new StoreOwner(s);
		List<StoreOwner> owners = s.getMyOwners();
		if(!owners.add(so))
			return false;
		s.setMyOwners(owners);
	//	owner.setStore(s);
		return BlMain.addOwner(owner, so);
	}


	static boolean addNewManager(Store s, Subscriber newMan) {
		if(s == null || newMan == null)
			return false;
		StoreManager sm = new StoreManager(s);
		List<StoreManager> managers = s.getMyManagers();
		if(!managers.add(sm))
			return false;
		s.setMyManagers(managers);
		//newMan.setStore(s);
		return BlMain.addManager(newMan, sm);
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
			PurchaseType pt = product.getPurchasePolicy().getPurchaseType(); 
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

	static boolean changeStorePurchasePolicy(Store s, PurchasePolicy pp){
		if(s == null || pp == null)
			return false;
		s.setStorePolicy(pp);
		return true;
	}

}
