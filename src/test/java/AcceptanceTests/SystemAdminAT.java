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

public class SystemAdminAT {

	private static SystemAdministrator sa;
	private static Subscriber s1;
	private static Subscriber s2;
	private static Subscriber s3;

	@BeforeClass
	public static void beforeTests(){
		sa = new SystemAdministrator("admin", "nimda", "sagivmap", "Ashdod", "088559977", "132456789987", new ArrayList<Purchase>(), new ArrayList<StoreManager>(), new ArrayList<StoreOwner>());
		try {
			s1 = BlMain.signUp(new Guest(), "first", "first", "nameA", "add1" , "012345678945", "987564321123");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			s2 = BlMain.signUp(new Guest(), "second", "second", "nameB", "add2" , "012345678945", "987564321123");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			s3 = BlMain.signUp(new Guest(), "third", "third", "nameC", "add3" , "012345678945", "987564321123");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void mainTest(){
		testRemoveSubscriber();
		/*testViewPurchaseHistory();*/
	}
	//5.2
	private void testRemoveSubscriber(){
		//incorrect input
		try {
			BlMain.removeSubscriber(sa, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "invalid subscriber");
		}
		SystemAdministrator sa2 = null;
		try {
			BlMain.removeSubscriber(sa2, s1);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "invalid admin");
		}

		Subscriber notExits  = new Subscriber( new Cart(), "forth", "forth", "name4", "add4" , "012345678945", "987564321123", new ArrayList<Purchase>(), new ArrayList<StoreManager>(), new ArrayList<StoreOwner>());
		try {
			assertFalse(BlMain.removeSubscriber(sa, notExits));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		try {
			assertTrue(BlMain.removeSubscriber(sa, s1));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		try {
			assertNull(BlMain.getSubscriberFromUsername(s1.getUsername()));
		} catch (Exception e) {
			fail();
		}
	}
	//5.4
	private void testViewPurchaseHistory(){
		fail("need to change implement to retrun list of Purchase");
	}
}
