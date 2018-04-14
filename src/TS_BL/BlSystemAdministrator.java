package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlSystemAdministrator {
	
	/**
	 * remove subscriber from the system
	 * @param s
	 * @return true if succeed false otherwise
	 */
	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s)
	{
		List<Subscriber> subs = BlMain.allSubscribers;
		if(subs.contains(s)){
			subs.remove(s);
			BlMain.allSubscribers = subs;
			return true;
		}
		
		else
			return false;
	}
	/**
	 * @param s
	 * @return the purchase history that made by the subscriber
	 */
	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s)
	{
		List<Subscriber> subs = BlMain.allSubscribers;
		if(subs.contains(s)){
			return s.getPurchaseHistory();
		}
		
		else
			return null;
	}
	/**
	 * @param store
	 * @return the purchase history that made in the store
	 */
	public static List<Cart> viewStoreHistory(SystemAdministrator sa, Store store)
	{
		List<Store> stores = sa.getStores();
		if(stores.contains(store)){
			return store.getPurchaseHistory();
		}
		
		else
			return null;
	}
	
}
