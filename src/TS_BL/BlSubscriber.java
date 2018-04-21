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
	public static Store openStore(Subscriber sub, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen)
	{
		Store s = new Store(BlMain.getStoreId(), sub.getAddress(), sub.getPhone(), gradeing, products, purchaseHistory, isOpen);
		return s;
	}
	
	/**
	 * add cart to history
	 * @param sub
	 * @param cart
	 * @return true if succseed false otherwise
	 */
	public static boolean addPurchaseToHistory(Subscriber sub, Cart cart)
	{
		if (cart == null || sub == null)
			return false;
	
		List<Cart> allCarts = sub.getPurchaseHistory();
		allCarts.add(cart);
		sub.setPurchaseHistory(allCarts);
		
		return true;
	}
	
	/**
	 * add new ownership
	 * @param sub
	 * @param owner
	 * @return true if succseed false otherwise
	 */
	public static boolean addOwner(Subscriber sub, StoreOwner owner)
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
	public static boolean addManager(Subscriber sub, StoreManager manager)
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
	public static boolean deleteOwner(Subscriber sub, StoreOwner owner)
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
	public static boolean deleteManager(Subscriber sub, StoreManager manager)
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
