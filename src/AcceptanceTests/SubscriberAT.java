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
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class SubscriberAT {
	private Guest signed = new Guest();
	private Subscriber sub;
	
	@Before
	public void beforeTests(){
		signed = new Guest();
		sub = BlMain.signUp(signed, "usr", "pass", "name", "address", "132412356", "1234567891011");
	}
	//2.1
	@Test
	public void testSignIn(){
		Guest notSigned = new Guest();
		
		//try to sign with not signed sub
		assertNull(BlMain.signIn(notSigned, "usr", "pass"));
		
		//incorrect usrname
		assertNull(BlMain.signIn(signed, "notExist", "pass"));
		//incorrect pass
		assertNull(BlMain.signIn(signed, "usr", "notExist"));
		
		//exists usr
		assertEquals(sub, BlMain.signIn(signed, "usr", "pass"));
		
	}
	
	//2.2
	@Test
	public void testOpenStore(){
		// incorrect inputs
		assertNull(BlMain.openStore(sub, -5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true));
		assertNull(BlMain.openStore(sub,  5, null, new ArrayList<Purchase>(), true));
		assertNull(BlMain.openStore(sub,  5, new HashMap<Product, Integer>(), null, true));
		assertNull(BlMain.openStore(null,  5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), false));
		
		//good case
		Store toCheck = BlMain.openStore(sub, 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		
		List<StoreOwner> ownStores = sub.getOwner();
		assertEquals(toCheck, ownStores.get(0).getStore());
		
	}
}















