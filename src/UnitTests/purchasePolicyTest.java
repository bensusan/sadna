package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class purchasePolicyTest {
	
	private PurchasePolicy pp;
	
	@Before
    public void oneTimeSetUp() {
		pp = new PurchasePolicy(new ImmediatelyPurchase());
	}
	
	@Test
	public void mainTest(){
		testPurchase();
	}
	private void testPurchase(){
		Guest g = new Guest();
		//Test Null Guest
		assertFalse(BlMain.purchase(pp.getPurchaseType(), null, 100, 3));
		//Test neg proce
		assertFalse(BlMain.purchase(pp.getPurchaseType(), g, -100, 3));
		//Test zero proce
		assertFalse(BlMain.purchase(pp.getPurchaseType(), g, 0, 3));
		//Test neg proce
		assertFalse(BlMain.purchase(pp.getPurchaseType(), g, 100, -3));
		//Test zero proce
		assertFalse(BlMain.purchase(pp.getPurchaseType(), g, 100, 0));
		//Test normal case
		assertTrue(BlMain.purchase(pp.getPurchaseType(), g, 100, 3));
	}

}
