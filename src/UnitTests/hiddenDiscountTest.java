package UnitTests;
import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class hiddenDiscountTest {
	
	private HiddenDiscount hd;
	
	@Before
    public  void oneTimeSetUp() {
		String dateAsString = "2019-01-01";
		Date d = Date.valueOf(dateAsString);
		
		this.hd = new HiddenDiscount(123 ,d, 50);
	}
	
	@Test
	public void mainTest()
	{
		testUpdatePrice();
	}
	private void testUpdatePrice(){
		//test negetive price
		assertEquals(-1, BlMain.updatePrice(hd, -1, 123));
		//test expired end date
		hd.setDiscountEndDate(Date.valueOf("2016-01-01"));
		assertEquals(-1, BlMain.updatePrice(hd, 100, 123));
		//test wrong code
		hd.setDiscountEndDate(Date.valueOf("2019-01-01"));
		assertEquals(-1, BlMain.updatePrice(hd, 100, 321));
		//test good case
		assertEquals(50, BlMain.updatePrice(hd, 100, 123));
	}
	
	

}
