package main;

import java.util.List;

public class SystemAdministrator extends Subscriber {
	
	/**
	 * remove subscriber from the system
	 * @param s
	 * @return true if succseed false otherwise
	 */
	public boolean removeSubscriber(Subscriber s)
	{
		//TODO missing implementation
				return true;
	}
	/**
	 * @param s
	 * @return the purchase history that made by the subscriber
	 */
	public List<Cart> viewSubscriberHistory(Subscriber s)
	{
		//TODO missing implementation
				return null;
	}
	/**
	 * @param store
	 * @return the purchase history that made in the store
	 */
	public List<Cart> viewStoreHistory(Store store)
	{
		//TODO missing implementation
				return null;
	}

}
