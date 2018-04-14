package TS_BL;

import java.util.List;

import TS_SharedClasses.Cart;
import TS_SharedClasses.Guest;
import TS_SharedClasses.ImmediatelyPurchase;
import TS_SharedClasses.Product;
import TS_SharedClasses.PurchaseType;
import TS_SharedClasses.Store;
import TS_SharedClasses.StoreManager;
import TS_SharedClasses.Subscriber;
import TS_SharedClasses.SystemAdministrator;

public class BIAlex {
	
	public static boolean removeSubscriber(SystemAdministrator sa, Subscriber s){
		//what about making his stores to zombie ?
		return sa.getSubs().remove(s);		
	}
	
	public static List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s){
		return sa != null ?  s.getPurchaseHistory() : null;
	} 
	
	public static List<Cart> viewStoreHistory(SystemAdministrator sa, Store store){
		return sa != null ? BlPermissions.getPurchaseHistory(store) : null;
	}
	
	public boolean purchase(PurchaseType ip, Guest g, int price)
	{
		
		return false;
	}
	
	//Gets the original price, calculate and return the price after the discount.
	public int getDiscountedPrice(ImmediatelyPurchase ip, int price)
	{
		return 0;
	}
  
}
