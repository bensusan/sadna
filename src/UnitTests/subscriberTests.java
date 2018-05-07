package UnitTests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class subscriberTests {

	private static Subscriber sub;
	private static StoreOwner owner;
	private static StoreManager manager;
	@BeforeClass
    public static void oneTimeSetUp() {
		sub=new Subscriber(new Cart(), "abc123", "0987654", "moshe datz", "herzel 23 herzelia", "0541234567", "1234567890123456", new LinkedList(), new LinkedList(), new LinkedList());
		owner=new StoreOwner(new Store("new Store4", "4444", "0548787878", 1, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true));
		manager=new StoreManager(new Store("new Store4", "4444", "0548787878", 1, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true));
	}
	

	@Test
	public void mainTest(){
		testOpenStore();
		testAddOwner();
		testAddManager();
	}
	private void testOpenStore()
	{
		Subscriber ofir = null;
		try {
			ofir = BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try{
			BlMain.openStore(null,"ofir's store", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
			BlMain.openStore(ofir,"ofir's store", -1, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
			BlMain.openStore(ofir,"ofir's store", 3, null, new LinkedList<Purchase>(), true);
			BlMain.openStore(ofir,"ofir's store", 3, new HashMap<Product,Integer>(), null, true);
			//fail();
		}
		catch (Exception e) {}
		try {
			assertNotNull(BlMain.openStore(ofir,"ofir's store", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		try {
			BlMain.removeSubscriber(amit, ofir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testAddOwner() {
		Subscriber sub2=null;
		try {
			BlMain.addOwner(sub2, owner);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"invalid subscriber");
		}
		try {
			BlMain.addOwner(sub, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"invalid owner");
		}
		try {
			assertTrue(BlMain.addOwner(sub, owner));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(sub.getOwner().contains(owner));
	}
	private void testAddManager() {
		Subscriber sub2=null;
		try {
			BlMain.addManager(sub2, manager);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"invalid subscriber");
		}
		try {
			BlMain.addManager(sub, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"invalid manager");
		}
		try {
			assertTrue(BlMain.addManager(sub, manager));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(sub.getManager().contains(manager));
	}
	
	
	

}
