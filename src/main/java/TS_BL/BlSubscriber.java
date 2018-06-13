package TS_BL;

import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;
import static TS_BL.BlMain.dalRef;

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
	 * @throws Exception 
	 */
	static Store openStore(Subscriber sub, String storeName, int gradeing, Map<Product, Integer> products,
			List<Purchase> purchaseHistory, boolean isOpen) throws Exception
	{
		if(sub == null)
			throw new Exception("invalid subscriber");
		if(gradeing < 0 || gradeing > 5)
			throw new Exception("something went wrong with the grading");
		List<Subscriber> sub1 = BlMain.allSubscribers;
		
		List<StoreOwner> own=sub.getOwner();
		Store s = new Store(storeName, sub.getAddress(), sub.getPhone(), gradeing, products, purchaseHistory, isOpen);
		dalRef.addStore(s);
		StoreOwner so=new StoreOwner(s);
		own.add(so);
		
		BlMain.allSubscribers.get(BlMain.getIndexByUN(sub)).setOwner(own);
		sub.setOwner(own); //for safety
		
		own=s.getMyOwners();
		own.add(so);
		s.setMyOwners(own);
		
		List<Subscriber> sub2 = BlMain.allSubscribers;
		return s;
	}
	
	/**
	 * add cart to history
	 * @param sub
	 * @param cart
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception
	{
		if(p == null)
			throw new Exception("something went wrong with the purchase");
		if(sub == null)
			throw new Exception("invalid subscriber");
		
		return sub.getPurchaseHistory().add(p);
	}
	
	/**
	 * add new ownership
	 * @param sub
	 * @param owner
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addOwner(Subscriber sub, StoreOwner owner) throws Exception
	{
		if(owner == null)
			throw new Exception("invalid owner");
		if(sub == null)
			throw new Exception("invalid subscriber");
		
		
		List<StoreOwner> sOwn = sub.getOwner();
		if (sOwn.contains(owner))
			throw new Exception("this owner already appear in the list of owners");

		sOwn.add(owner);
		sub.setOwner(sOwn);
		return true;
	}
	/**
	 * add new manager responsibility
	 * @param sub
	 * @param manager
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addManager(Subscriber sub, StoreManager manager) throws Exception
	{
		if(sub == null)
			throw new Exception("invalid subscriber");
		if(manager == null)
			throw new Exception("invalid manager");
		
		List<StoreManager> sMng = sub.getManager();
		if (sMng.contains(manager))
			throw new Exception("this manager already appear in the list of store managers");


		sMng.add(manager);
		sub.setManager(sMng);
		return true;
	}
	
	/**
	 * delete ownership
	 * @param owner
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean deleteOwner(Subscriber sub, StoreOwner owner) throws Exception
	{
		if(sub == null) 
			throw new Exception("invalid subscriber");
		if(owner == null)
			throw new Exception("invalid store owner");
		
		List<StoreOwner> sOwn = sub.getOwner();
		if (!sOwn.contains(owner))
			throw new Exception("the owner doesn't appear in the list of store owners");

		sOwn.remove(owner);
		sub.setOwner(sOwn);
		return true;
	}
	/**
	 * delete manager responsibility
	 * @param sub
	 * @param manager
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean deleteManager(Subscriber sub, StoreManager manager) throws Exception
	{
		if(sub == null) 
			throw new Exception("invalid subscriber");
		if(manager == null)
			throw new Exception("invalid store manager");

		List<StoreManager> sMng = sub.getManager();
		if (!sMng.contains(manager))
			throw new Exception("the manager doesn't appear in the list of store managers");

		sMng.remove(manager);
		sub.setManager(sMng);
		return true;
	}

}
