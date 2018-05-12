package IntegrationTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.Guest;
import TS_SharedClasses.Product;
import TS_SharedClasses.Purchase;
import TS_SharedClasses.Store;
import TS_SharedClasses.StoreManager;
import TS_SharedClasses.StoreOwner;
import TS_SharedClasses.Subscriber;
import TS_SharedClasses.SystemAdministrator;

public class SubscriberRegTests {

	private static SystemAdministrator amit;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		
	}

	@Test
	public void openStore() {
		try {
			Subscriber s = BlMain.signUp(new Guest (), "newUser", "newPass", "newName", "newAddress", "0948376767", "1234567890987654");
			
			try {
				Store newStore = BlMain.openStore(s,"ofir's store", 5, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
				assertTrue(BlMain.getAllStores().contains(newStore));
				assertTrue(s.getOwner().size()>0);
				assertTrue(newStore.getMyOwners().size()>0);
				
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			BlMain.removeSubscriber(amit, s);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
