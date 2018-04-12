package TS_BL;

import java.util.List;

import TS_SharedClasses.*;

public class BlSystemAdministrator {
	
	/**
	 * remove subscriber from the system
	 * @param s
	 * @return true if succseed false otherwise
	 */
	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * @param s
	 * @return the purchase history that made by the subscriber
	 */
	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s)
	{
		//TODO missing implementation
				return null;
	}
	/**
	 * @param store
	 * @return the purchase history that made in the store
	 */
	public static List<Cart> viewStoreHistory(SystemAdministrator sa, Store store)
	{
		//TODO missing implementation
				return null;
	}
	
}
