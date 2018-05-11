package IntegrationTests;

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
		try {
			Subscriber s = BlMain.signUp(new Guest (), "newUser", "newPass", "newName", "newAddress", "0948376767", "1234567890987654");
			
			try {
				Store newStore = BlMain.openStore(s,"ofir's store", 5, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
				Subscriber s2=BlMain.signUp(new Guest(), "newUser2", "newPass2", "new Name", "newAddress2", "0948376767", "1234567890987654");
				BlMain.addNewStoreOwner(s.getOwner().get(0), s2);
				Subscriber s3=BlMain.signUp(new Guest(), "newUser3", "newPass3", "new new Name", "newAddress3", "0948376767", "1234567890987654");
				BlMain.addNewManager(s.getOwner().get(0), s3);
				
				BlMain.removeSubscriber(amit, s);
				assertFalse(BlMain.allSubscribers.contains(s));
				try{
					BlMain.signIn(s, "newUser", "newPass");
					fail();
				}catch (Exception e) {}
				
				assertTrue(BlMain.getAllStores().contains(newStore));
				
				BlMain.removeSubscriber(amit, s2);
				assertFalse(BlMain.allSubscribers.contains(s2));
				try{
					BlMain.signIn(s2, "newUser2", "newPass2");
					fail();
				}catch (Exception e) {}
				assertFalse(BlMain.getAllStores().contains(newStore));//we remove last owner
				//assertEquals(s3.getManager().size(),0);
				//TODO remove all storemanager if last owner deleted
				
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	 
	

}
