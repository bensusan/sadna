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
		s1 = BlMain.signUp(new Guest(), "first", "first", "nameA", "add1" , "012345678945", "987564321123");
		s2 = BlMain.signUp(new Guest(), "second", "second", "nameB", "add2" , "012345678945", "987564321123");
		s3 = BlMain.signUp(new Guest(), "third", "third", "nameC", "add3" , "012345678945", "987564321123");
		System.out.println(s1);
	}
	
	//5.2
	@Test
	public void testRemoveSubscriber(){
		//incorrect input
		assertFalse(BlMain.removeSubscriber(sa, null));
		SystemAdministrator sa2 = null;
		assertFalse(BlMain.removeSubscriber(sa2, s1));
		
		Subscriber notExits  = new Subscriber( new Cart(), "forth", "forth", "name4", "add4" , "012345678945", "987564321123", new ArrayList<Purchase>(), new ArrayList<StoreManager>(), new ArrayList<StoreOwner>());
		assertFalse(BlMain.removeSubscriber(sa, notExits));
		
		assertTrue(BlMain.removeSubscriber(sa, s1));
		
		assertFalse(BlMain.allSubscribers.contains(s1));
	}
	
	//5.4
	@Test
	public void testViewPurchaseHistory(){
		fail("need to change implement to retrun list of Purchase");
	}
}
