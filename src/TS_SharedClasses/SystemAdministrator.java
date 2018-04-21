package TS_SharedClasses;

import java.util.ArrayList;
import java.util.List;

import TS_BL.BlMain;

public class SystemAdministrator extends Subscriber {
	private List<Subscriber> listOfSubs;
	private List<Store> listOfStores;

	public SystemAdministrator(String username, String password, String fullName, String address, String phone,
			String creditCardNumber, List<Cart> purchaseHistory, List<StoreManager> manager, List<StoreOwner> owner) {
		super(username, password, fullName, address, phone, creditCardNumber, purchaseHistory, manager, owner);
		
		listOfSubs = BlMain.allSubscribers;
		listOfStores = new ArrayList<Store>();
	}
	
	public void addSub(Subscriber s){
		listOfSubs.add(s);
	}
	
	public List<Subscriber> getSubs(){
		return listOfSubs;
	}
	
	public void setStores(List<Store> stores){
		this.listOfStores = stores;
	}
	
	public void addStore(Store s){
		listOfStores.add(s);
	}
	
	public List<Store> getStores(){
		return listOfStores;
	}
	
}
