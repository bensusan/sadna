package AcceptanceTests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.Date;

import org.junit.Before;
import org.junit.BeforeClass;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class SubscriberAT {
	private static Guest signed = new Guest();
	private static Subscriber sub;
	
	@BeforeClass
	public static void beforeTests(){
		signed = new Guest();
		sub = BlMain.signUp(signed, "usr", "pass", "name", "address", "132412356", "1234567891011");
	}
	@Test
	public void mainTest(){
		testSignIn();
		testOpenStore();
	}
	//2.1
	private void testSignIn(){
		
		//incorrect usrname
		assertNull(BlMain.signIn(signed, "notExist", "pass"));
		//incorrect pass
		assertNull(BlMain.signIn(signed, "usr", "notExist"));
		
		//exists usr
		//assertEquals(sub, BlMain.signIn(signed, "usr", "pass"));
		
	}
	//2.2
	private void testOpenStore(){
		// incorrect inputs
		assertNull(BlMain.openStore(sub,"store_name8", -5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true));
		assertNull(BlMain.openStore(null,"store_name7",  5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), false));
		
		//good case
		Store toCheck = BlMain.openStore(sub,"store_name9", 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		
		List<StoreOwner> ownStores = sub.getOwner();
		assertEquals(toCheck, ownStores.get(0).getStore());
		
	}
}















