package tests;
import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class overtDiscountTest {

	private OvertDiscount od;
	
	@Before
    public  void oneTimeSetUp() {
		String dateAsString = "2019-01-01";
		Date d = Date.valueOf(dateAsString);
		
		this.od = new OvertDiscount(d, 50);
	}
	
	@Test
	public void mainTest(){
		testUpdatePrice();
	}
	
	private void testUpdatePrice(){
		//test negetive price
		assertEquals(-1, BlMain.updatePrice(od, -10, 10));
		//test zero price
		assertEquals(-1, BlMain.updatePrice(od, 0, 10));
		//test neg amount
		assertEquals(-1, BlMain.updatePrice(od, 10, -10));
		//test zero amount
		assertEquals(-1, BlMain.updatePrice(od, 10, 0));
		//test expired end date
		od.setDiscountEndDate(Date.valueOf("2016-01-01"));
		assertEquals(-1, BlMain.updatePrice(od, 100, 10));
		//test good case
		od.setDiscountEndDate(Date.valueOf("2019-01-01"));
		assertEquals(50, BlMain.updatePrice(od, 100, 10));
	}

}
