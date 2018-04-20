package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class productTests {
	
	private Product p;
	private Store s;
	private StoreOwner so;
	
	@Before
    public  void oneTimeSetUp() {
		/*initiating product*/		
		String dateAsString = "2019-01-01";
		Date d = Date.valueOf(dateAsString);
		OvertDiscount od = new OvertDiscount(d, 50);
		ImmediatelyPurchase ip = new ImmediatelyPurchase(od);
		PurchasePolicy pp = new PurchasePolicy(ip);
		p = new Product( "01081991" , "test product", 150, 5, "test category", pp);
		
		/*initiating store*/
		s=new Store(123, "hadekel 72 Ranana", 1542365985, 3, new HashMap<>(),new LinkedList<Purchase> (), true);
		
		/*initiating store owner*/
		so=new StoreOwner(s);
		
		/*adding the product to store*/
		BlMain.addProductToStore(so, p, 2);
	}
	
	@Test
	public void testPurchase() {
		// initiate Guest
		Guest g = new Guest();
		
		//purchase with null guest
		assertFalse(BlMain.purchase(p, null, 10, 20));
		//purchase with neg price
		assertFalse(BlMain.purchase(p, g, -10, 20));
		//purchase with neg amount
		assertFalse(BlMain.purchase(p, g, 10, -20));
		//purchase with zero price
		assertFalse(BlMain.purchase(p, g, 0, 20));
		//purchase with zero amount
		assertFalse(BlMain.purchase(p, g, 10, 0));
		//regular case
		assertTrue(BlMain.purchase(p, g, 10, 20));
	}

}
