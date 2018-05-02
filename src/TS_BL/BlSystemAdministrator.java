
package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlSystemAdministrator {
	
	/**
	 * remove subscriber from the system
	 * @param s
	 * @return true if succeed false otherwise
	 */
	static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
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
	static List<Purchase> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
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
	static List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store)
	{
		if(sa == null || store == null)
			return null;
		
		List<Store> stores = sa.getStores();
		if(stores.contains(store)){
			return store.getPurchaseHistory();
		}
		else
			return null;
	}

	public static boolean createAdmin(SystemAdministrator admin) {
		if(BlMain.allAdmins.contains(admin))
			return false; //admin already exists
		return BlMain.allAdmins.add(admin);
	}

	public static boolean removeAdmin(SystemAdministrator admin) {
		if(BlMain.allAdmins.size() <= 1)
			return false; //must be at least one admin
		return BlMain.allAdmins.remove(admin);
	}
}