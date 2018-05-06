package AcceptanceTests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.Date;

import org.junit.Before;
import org.junit.BeforeClass;

import TS_BL.BlMain;
import TS_BL.BlStore;
import TS_SharedClasses.*;

public class ItemSupplyAT {

	private static Guest g;
	private static Subscriber sub;
	private static StoreOwner so;
	private static Product immediateOvertProduct;
	private static Product immediateNoDiscountProduct;
	private static Product lotteryProduct;
	private static Product zeroAmountProduct;
	
	@BeforeClass
	public static void bef(){
		g = new Guest();
		sub = BlMain.signUp(g, "globUse1", "globPass", "usr", "name", "132412356", "1234567891011");
		Store s1 = BlMain.openStore(sub, "store_name1", 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		List<StoreOwner> own1 = sub.getOwner();
		so = own1.get(0);
		
		try {
			immediateOvertProduct = new Product("prod1", 200, 4, "test cat 1", 
					new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
			immediateNoDiscountProduct = new Product("prod2", 200, 4, "test cat 2", 
					new PurchasePolicy(new ImmediatelyPurchase()));
			lotteryProduct = new Product("prod3", 100, 4, "test cat 3", 
					new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
			zeroAmountProduct = new Product("prod4", 200, 4, "test cat 4", 
					new PurchasePolicy(new ImmediatelyPurchase()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		
		BlMain.addProductToStore(so, immediateOvertProduct, 50);
		BlMain.addProductToStore(so, immediateNoDiscountProduct, 50);
		BlMain.addProductToStore(so, lotteryProduct, 50);
		BlMain.addProductToStore(so, zeroAmountProduct, 50);
	}
	//10
	@Test
	private void testSendProducts(){
		
		//test payment

		assertFalse(BlStore.sendTheProducts(g, "-asd45aDSA324FF@!#!@%"));
		Guest guestNull = null;
		assertFalse(BlStore.sendTheProducts(guestNull, "Tel aviv"));
		
		assertTrue(BlStore.sendTheProducts(g, "Tel aviv"));
		
	}

}
