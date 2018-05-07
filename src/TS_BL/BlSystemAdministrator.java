
package TS_BL;

import java.util.List;

import IntegrationTests.systemAdministratorRegTests;
import TS_SharedClasses.*;

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

		List<Subscriber> subList = BlMain.allSubscribers;
		if (!subList.contains(s))
			return false;
		if (s instanceof SystemAdministrator){
			boolean canRemoveAdmin = false;
			for (Subscriber subs : BlMain.allSubscribers){
				if(!subs.equals(s) && subs instanceof SystemAdministrator){
					canRemoveAdmin = true;
					break;
				}
			}
			if(!canRemoveAdmin)
				throw new Exception("something went wrong");
		}
		subList.remove(s);
		BlMain.allSubscribers = subList;
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

		List<Subscriber> subList = BlMain.allSubscribers;
		if (!subList.contains(s))
			throw new Exception("this subscriber doesn't appear in the list of subscribers");

		return subList.get(subList.indexOf(s)).getPurchaseHistory();
	}

	/**
	 * @param store
	 * @return the purchase history that made in the store
	 * @throws Exception 
	 */
	static List<Purchase> viewStoreHistory(SystemAdministrator sa, Store store) throws Exception
	{
		if(sa == null)
			throw new Exception("invalid admin");
		if(store == null)
			throw new Exception("invalid store");

		List<Store> stores = sa.getStores();
		if(stores.contains(store)){
			return store.getPurchaseHistory();
		}
		else
			throw new Exception("this store doesn't appears in the list of stores");
	}

}