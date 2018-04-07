package TS_SharedClasses;

import java.util.List;

public class SystemAdministrator extends Subscriber {

	public SystemAdministrator(String username, String password, String fullName, String address, int phone,
			int creditCardNumber, List<Cart> purchaseHistory, List<StoreManager> manager, List<StoreOwner> owner) {
		super(username, password, fullName, address, phone, creditCardNumber, purchaseHistory, manager, owner);
		// TODO Auto-generated constructor stub
	}
}
