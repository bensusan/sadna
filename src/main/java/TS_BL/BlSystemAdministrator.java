
package TS_BL;

import java.util.LinkedList;
import java.util.List;

import TS_SharedClasses.*;
import static TS_BL.BlMain.dalRef;

public class BlSystemAdministrator {

	/**
	 * remove subscriber from the system
	 * @param s
	 * @return true if succeed false otherwise
	 * @throws Exception 
	 */
	static boolean removeSubscriber(SystemAdministrator sa, Subscriber s) throws Exception {
		if(sa == null)
			throw new Exception("invalid admin");
		if(s == null)
			throw new Exception("invalid subscriber");

		if(!dalRef.isSubscriberExist(s.getUsername()))
			return false;
		s = dalRef.getSubscriber(s.getUsername(), s.getPassword());
		List<Subscriber> subList = dalRef.allSubscribers();
		
		if (s instanceof SystemAdministrator){
			boolean canRemoveAdmin = false;
			for (Subscriber subs : subList){
				if(!subs.getUsername().equals(s.getUsername()) && subs instanceof SystemAdministrator){
					canRemoveAdmin = true;
					break;
				}
			}
			if(!canRemoveAdmin)
				throw new Exception("something went wrong");
		}
//		if(!s.getOwner().isEmpty() && s.getOwner().size() == 1){
//			s.getOwner().get(0).getStore().setMyManagers(new LinkedList<StoreManager>());
//		}
		dalRef.removeSubscriber(s.getUsername());
		return true;
	}

	/**
	 * @param s
	 * @return the purchase history that made by the subscriber
	 * @throws Exception 
	 */	
	static List<Purchase> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) throws Exception {
		if(sa == null)
			throw new Exception("invalid admin");
		if(s == null)
			throw new Exception("invalid subscriber");
		
		if (!dalRef.isSubscriberExist(s.getUsername()))
			throw new Exception("this subscriber doesn't appear in the list of subscribers");

		return dalRef.getMyPurchase(s.getUsername());
	}

	/**
	 * @param store
	 * @return the purchase history that made in the store
	 * @throws Exception 
	 */
	static List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store) throws Exception{
		if(sa == null)
			throw new Exception("invalid admin");
		if(store == null)
			throw new Exception("invalid store");
		return dalRef.getStorePurchase(store.getStoreId());
	}

}