package tests;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class purchasePolicyTest {
	
	private PurchasePolicy pp;
	
	@BeforeClass
    public void oneTimeSetUp() {
		pp = new PurchasePolicy(new ImmediatelyPurchase());
	}
	
	@Test
	public void testPurchase(){
		Guest g = new Guest();
		//Test Null Guest
		assertFalse(BlMain.purchase(pp, null, 100, 3));
		//Test neg proce
		assertFalse(BlMain.purchase(pp, g, -100, 3));
		//Test zero proce
		assertFalse(BlMain.purchase(pp, g, 0, 3));
		//Test neg proce
		assertFalse(BlMain.purchase(pp, g, 100, -3));
		//Test zero proce
		assertFalse(BlMain.purchase(pp, g, 100, 0));
		//Test normal case
		assertFalse(BlMain.purchase(pp, g, 100, 3));
	}
	

}