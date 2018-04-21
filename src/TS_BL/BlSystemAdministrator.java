
package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlSystemAdministrator {
	
	/**
	 * remove subscriber from the system
	 * @param s
	 * @return true if succeed false otherwise
	 */
	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
		if(sa == null || s == null)
			return false;

		List<Subscriber> subList = BlMain.allSubscribers;
		if (!subList.contains(s))
			return false;

		subList.remove(s);
		BlMain.allSubscribers = subList;
		return true;
	}
	
	/**
	 * @param s
	 * @return the purchase history that made by the subscriber
	 */	
	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
		if(sa == null || s == null)
			return null;
		
		List<Subscriber> subList = BlMain.allSubscribers;
		if (!subList.contains(s))
			return null;

		return subList.get(subList.indexOf(s)).getPurchaseHistory();
	}
	
	/**
	 * @param store
	 * @return the purchase history that made in the store
	 */
	public static List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store)
	{
		List<Store> stores = sa.getStores();
		if(stores.contains(store)){
			return store.getPurchaseHistory();
		}
		else
			return null;
	}
}