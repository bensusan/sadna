package unitTests;

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
		owner=new StoreOwner(new Store("new Store4", "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true));
		manager=new StoreManager(new Store("new Store4", "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true));
	}
	

	@Test
	public void mainTest(){
		testAddPurchaseToHistory();
		testOpenStore();
		testAddOwner();
		testAddManager();
	}
	private void testAddPurchaseToHistory() {
		
		Guest guest=new Guest();

		Product p=new Product("456", "ping pong ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addImmediatelyProduct(guest, p, 3);
		
		ProductInCart pic=new ProductInCart(p, 5, 1);
		Purchase purchase = new Purchase(new Date(2,5,2017), pic);
		Subscriber sub2 = null;
		assertFalse(BlMain.addPurchaseToHistory(sub2, purchase));
		sub.setCart(guest.getCart());
		assertFalse(BlMain.addPurchaseToHistory(sub, null));
		assertTrue(BlMain.addPurchaseToHistory(sub, purchase));
		assertTrue(sub.getPurchaseHistory().contains(purchase));
	}
	private void testOpenStore()
	{
		Subscriber ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		assertNull(BlMain.openStore(null,"ofir's store", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true));
		assertNull(BlMain.openStore(ofir,"ofir's store", -1, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true));
		assertNull(BlMain.openStore(ofir,"ofir's store", 3, null, new LinkedList<Purchase>(), true));
		assertNull(BlMain.openStore(ofir,"ofir's store", 3, new HashMap<Product,Integer>(), null, true));
		assertNotNull(BlMain.openStore(ofir,"ofir's store", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true));
		
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		BlMain.removeSubscriber(amit, ofir);
	}
	
	private void testAddOwner() {
		Subscriber sub2=null;
		assertFalse(BlMain.addOwner(sub2, owner));
		assertFalse(BlMain.addOwner(sub, null));
		assertTrue(BlMain.addOwner(sub, owner));
		assertTrue(sub.getOwner().contains(owner));
	}
	private void testAddManager() {
		Subscriber sub2=null;
		assertFalse(BlMain.addManager(sub2, manager));
		assertFalse(BlMain.addManager(sub, null));
		assertTrue(BlMain.addManager(sub, manager));
		assertTrue(sub.getManager().contains(manager));
	}
	
	
	

}
