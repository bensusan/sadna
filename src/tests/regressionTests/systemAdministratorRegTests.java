package tests.regressionTests;

import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;


public class systemAdministratorRegTests {

	private static SystemAdministrator amit;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
	}

	@Test
	public void testRemoveSubscriber() {
		Subscriber s=BlMain.signUp(new Guest (), "newUser", "newPass", "newName", "newAddress", "0948376767", "1234567890987654");
		Store newStore =BlMain.openStore(s,"ofir's store", 5, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		
		StoreOwner owner=new StoreOwner(newStore);
		Subscriber s2=BlMain.signUp(new Guest(), "newUser2", "newPass2", "new Name", "newAddress2", "0948376767", "1234567890987654");
		BlMain.addOwner(s2, owner);
		BlMain.addNewStoreOwner(s.getOwner().get(0), s2.getOwner().get(0));
		
		Subscriber s3=BlMain.signUp(new Guest(), "newUser3", "newPass3", "new new Name", "newAddress3", "0948376767", "1234567890987654");
		StoreManager manager=new StoreManager(newStore);
		BlMain.addManager(s3, manager);
		BlMain.addNewManager(s.getOwner().get(0), manager);
		
		BlMain.removeSubscriber(amit, s);
		assertFalse(BlMain.allSubscribers.contains(s));
		assertNull(BlMain.signIn(s, "newUser", "newPass"));
		assertTrue(BlMain.getAllStores().contains(newStore));
		
		BlMain.removeSubscriber(amit, s2);
		assertFalse(BlMain.allSubscribers.contains(s2));
		assertNull(BlMain.signIn(s2, "newUser2", "newPass2"));
		assertFalse(BlMain.getAllStores().contains(newStore));//we remove last owner
		assertFalse(s3.getManager().contains(manager));
		
		BlMain.removeSubscriber(amit, s3);//just to clear data
		
		int numberOfAdmin=0;
		for(Subscriber sub:BlMain.allSubscribers){
			if(sub instanceof SystemAdministrator){
				numberOfAdmin++;
			}
		}
		if(numberOfAdmin==1){
			assertFalse(BlMain.removeSubscriber(amit, amit));//there is only one admin
		}
	}
	
	 
	

}
