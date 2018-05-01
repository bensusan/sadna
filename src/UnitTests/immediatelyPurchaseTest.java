package unitTests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;
import java.sql.Date;


public class immediatelyPurchaseTest {

	private ImmediatelyPurchase ip;
	
	@Before
    public void oneTimeSetUp() {
		ip = new ImmediatelyPurchase();
	}
	
	@Test
	public void mainTest(){
		testpPurchase();
	}
	private void testpPurchase(){
		//costumer
		Guest g = new Guest();
		//initialize discount policy
		OvertDiscount od = new OvertDiscount(Date.valueOf("2019-01-01" ), 50);
		this.ip.setDiscountPolicy(od);
		//test null costumer
		assertFalse(BlMain.purchase(ip, null, 150, 5));
		//test neg price
		assertFalse(BlMain.purchase(ip, g, -100, 5));
		//test neg amount
		assertFalse(BlMain.purchase(ip, g, 100, -5));
		//test zero price 
		assertFalse(BlMain.purchase(ip, g, 0, 5));
		//test zero amount 
		assertFalse(BlMain.purchase(ip, g, 100, 0));
		//test good case
		assertTrue(BlMain.purchase(ip, g, 100, 5));
	}
	

}
