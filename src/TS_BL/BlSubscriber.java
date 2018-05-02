package TS_BL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class BlSubscriber {

	/**
	 * subscriber try to open a new store
	 * the address of the store will be the address of the subscriber
	 * the phone of the store will be the phone of the subscriber
	 * the id of the store will choose by the system 
	 * @param sub
	 * @param grading
	 * @param products
	 * @param purchaseHistory
	 * @param isOpen
	 * @return new Store if succseed null otherwise
	 */
	static Store openStore(Subscriber sub, String storeName, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen)
	{
		if(sub == null || gradeing < 0 || gradeing > 5)
			return null;
		List<StoreOwner> own=sub.getOwner();
		Store s = new Store(storeName, sub.getAddress(), sub.getPhone(), gradeing, products, purchaseHistory, isOpen);
		StoreOwner so=new StoreOwner(s);
		own.add(so);
		sub.setOwner(own);
		return s;
	}
	
	/**
	 * add cart to history
	 * @param sub
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	static boolean addPurchaseToHistory(Subscriber sub, Purchase p)
	{
		if (p == null || sub == null)
			return false;
		return sub.getPurchaseHistory().add(p);
	}
	
	/**
	 * add new ownership
	 * @param sub
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	static boolean addOwner(Subscriber sub, StoreOwner owner)
	{
		if(sub == null || owner == null)
			return false;
		
		List<StoreOwner> sOwn = sub.getOwner();
		if (sOwn.contains(owner))
			return false;

		sOwn.add(owner);
		sub.setOwner(sOwn);
		return true;
	}
	/**
	 * add new manager responsibility
	 * @param sub
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	static boolean addManager(Subscriber sub, StoreManager manager)
	{
		if(sub == null || manager == null)
			return false;
		
		List<StoreManager> sMng = sub.getManager();
		if (sMng.contains(manager))
			return false;

		sMng.add(manager);
		sub.setManager(sMng);
		return true;
	}
	
	/**
	 * delete ownership
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	static boolean deleteOwner(Subscriber sub, StoreOwner owner)
	{
		if(sub == null || owner == null)
			return false;
		
		List<StoreOwner> sOwn = sub.getOwner();
		if (!sOwn.contains(owner))
			return false;

		sOwn.remove(owner);
		sub.setOwner(sOwn);
		return true;
	}
	/**
	 * delete manager responsibility
	 * @param sub
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	static boolean deleteManager(Subscriber sub, StoreManager manager)
	{
		if(sub == null || manager == null)
			return false;

		List<StoreManager> sMng = sub.getManager();
		if (!sMng.contains(manager))
			return false;

		sMng.remove(manager);
		sub.setManager(sMng);
		return true;
	}
}
