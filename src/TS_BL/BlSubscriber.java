package TS_BL;

import TS_SharedClasses.*;

public class BlSubscriber {

	/**
	 * subscriber try to open a new store
	 * the address of the store will be the address of the subscriber
	 * the phone of the store will be the phone of the subscriber
	 * the id of the store will choose by the system 
	 * @param storeName
	 * @param Description
	 * @return new Store if succseed null otherwise
	 */
	public static Store openStore(Subscriber sub, String storeName,String Description)
	{
		//address and phone
		//TODO missing implementation
				return null;
	}
	
	/**
	 * add cart to history
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	public static boolean addPurchaseToHistory(Subscriber sub, Cart cart)
	{
		//TODO missing implementation
		return false;
	}
	
	/**
	 * add new ownership
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public static boolean addOwner(Subscriber sub, StoreOwner owner)
	{
		//TODO missing implementation
				return false;
	}
	/**
	 * add new manager responsibility
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public static boolean addManager(Subscriber sub, StoreManager manager)
	{
		//TODO missing implementation
				return false;
	}
	
	/**
	 * delete ownership
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public static boolean deleteOwner(Subscriber sub, StoreOwner owner)
	{
		//TODO missing implementation
		return false;
	}
	/**
	 * delete manager responsibility
	 * @param manager
	 * @return true if succseed false otherwise
	 */
	public static boolean deleteManager(Subscriber sub, StoreManager manager)
	{
		//TODO missing implementation
		return false;
	}
}
