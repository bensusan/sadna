package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class lotteryPurchaseTest {

	private LotteryPurchase lp;
	
	@Before
	public  void oneTimeSetUp() {
		lp = new LotteryPurchase(Date.valueOf("2019-01-01"));
	}
	
	@Test
	public void testPurchase(){
		Guest g = new Guest();
		//test guest null
		assertFalse(BlMain.purchase(lp, null, 100, 5));
		//test price neg null
		assertFalse(BlMain.purchase(lp, g, -100, 5));
		//test price zero null
		assertFalse(BlMain.purchase(lp, g, 0, 5));
		//test amount neg null
		assertFalse(BlMain.purchase(lp, g, 100, -5));
		//test amount zero null
		assertFalse(BlMain.purchase(lp, g, 100, 0));
		//test normal case
		assertTrue(BlMain.purchase(lp, g, 100, 5));
	}
	
	@Test
	public void testIsLotteryDone(){
		//test neg price
		assertFalse(BlMain.isLotteryDone(lp, -100));
		//test zero price
		assertFalse(BlMain.isLotteryDone(lp, 0));
		//test passed date of lottery
		lp.setLotteryEndDate(Date.valueOf("2018-01-01"));
		assertTrue(BlMain.isLotteryDone(lp, 100));
		
		//test not reached to price
		Map<Guest, Integer> p = new HashMap<Guest, Integer>();
		p.put(new Guest(), 30);
		p.put(new Guest(), 30);
		lp.setParticipants(p);
		lp.setLotteryEndDate(Date.valueOf("2019-01-01"));
		assertFalse(BlMain.isLotteryDone(lp, 100));
		
		//test reached to price
		p.put(new Guest(), 40);
		lp.setParticipants(p);
		assertTrue(BlMain.isLotteryDone(lp, 100));
	}
	
	@Test
	public void testOpenNewLottery(){
		Date d = Date.valueOf("2020-01-01");
		BlMain.openNewLottery(lp, d);
		assertEquals(d.getTime(), lp.getLotteryEndDate().getTime());
	}
}
