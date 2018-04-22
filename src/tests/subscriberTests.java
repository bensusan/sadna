package tests;

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
		owner=new StoreOwner(new Store(BlMain.getStoreId(), "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true));
		manager=new StoreManager(null,new Store(BlMain.getStoreId(), "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true));
	}
	

	@Test
	public void testAddPurchaseToHistory() {
		
		Cart cart=new Cart();
		Map<Product,Integer> products=new HashMap<Product,Integer>();
		products.put(new Product("456", "ping pong ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase())), 3);
		cart.setProducts(products);
		Purchase purchase = new Purchase(new Date(System.currentTimeMillis()), BlMain.getPurchaseId(), cart);
		Subscriber sub2 = null;
		assertFalse(BlMain.addPurchaseToHistory(sub2, purchase));
		sub.setCart(cart);
		assertFalse(BlMain.addPurchaseToHistory(sub, null));
		assertTrue(BlMain.addPurchaseToHistory(sub, purchase));
		assertTrue(sub.getPurchaseHistory().contains(purchase));
	}

	@Test
	public void testAddOwner() {
		Subscriber sub2=null;
		assertFalse(BlMain.addOwner(sub2, owner));
		assertFalse(BlMain.addOwner(sub, null));
		assertTrue(BlMain.addOwner(sub, owner));
		assertTrue(sub.getOwner().contains(owner));
	}

	@Test
	public void testAddManager() {
		Subscriber sub2=null;
		assertFalse(BlMain.addManager(sub2, manager));
		assertFalse(BlMain.addManager(sub, null));
		assertTrue(BlMain.addManager(sub, manager));
		assertTrue(sub.getManager().contains(manager));
	}

	@Test
	public void testDeleteOwner() {
		Subscriber sub2=null;
		assertFalse(BlMain.deleteOwner(sub2, owner));
		assertFalse(BlMain.deleteOwner(sub, null));
		assertTrue(BlMain.deleteOwner(sub, owner));
		assertFalse(sub.getOwner().contains(owner));
		assertFalse(BlMain.deleteOwner(sub, owner));//does not exist
	}

	@Test
	public void testDeleteManager() {
		Subscriber sub2=null;
		assertFalse(BlMain.deleteManager(sub2, manager));
		assertFalse(BlMain.deleteManager(sub, null));
		assertTrue(BlMain.deleteManager(sub, manager));
		assertFalse(sub.getManager().contains(manager));
		assertFalse(BlMain.deleteManager(sub, manager));//does not exist
	}

}
