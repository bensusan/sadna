package tests;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class immediatelyPurchaseTest {

	private ImmediatelyPurchase ip;
	
	@BeforeClass
    public  void oneTimeSetUp() {
		ip = new ImmediatelyPurchase();
	}
	
	@Test
	public void testpPurchase(){
		//costumer
		Guest g = new Guest();
		//initialize discount policy
		OvertDiscount od = new OvertDiscount(Date.valueOf("2019-01-01"), 50);
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
	}
	
	@Test
	public void testgGetDiscountedPrice(){
		//test neg price
		assertEquals(-1, BlMain.getDiscountedPrice(ip, -100));
		//test zero price
		assertEquals(-1, BlMain.getDiscountedPrice(ip, 0));
		//test null immidietly purchase
		assertEquals(-1, BlMain.getDiscountedPrice(null, 100));
		OvertDiscount od = new OvertDiscount(Date.valueOf("2019-01-01"), 50);
		this.ip.setDiscountPolicy(od);
		assertEquals(75, BlMain.getDiscountedPrice(ip, 150));
	}
}
