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
import TS_SharedClasses.*;

public class PurchaseTypeAT {

	private static Guest g;
	private static Subscriber sub;
	private static StoreOwner so;
	private static Product prod1;
	private static Product prod2;
	private static Product prod3;
	private static Product prod4;

	@BeforeClass
	public static void beforeTests(){
		g = new Guest();
		try{
			sub = BlMain.signUp(g, "StoreOwenerAT", "globPass", "usr", "name", "132412356", "1234567891011");
			try{
				Store s1 = BlMain.openStore(sub,"store_name4", 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
				List<StoreOwner> own1 = sub.getOwner();
				so = own1.get(0);

				try {
					prod1 = new Product("prod1", 200, 4, "test cat 1", 
							new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
					prod2 = new Product("prod2", 200, 4, "test cat 2", 
							new PurchasePolicy(new ImmediatelyPurchase()));
					prod3 = new Product("prod3", 100, 4, "test cat 3", 
							new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
					prod4 = new Product("prod4", 200, 4, "test cat 4", 
							new PurchasePolicy(new ImmediatelyPurchase()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.addProductToStore(so, prod1, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.addProductToStore(so, prod2, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.addProductToStore(so, prod3, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.addProductToStore(so, prod4, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void mainTest()
	{
		testOvertPurchase();
		testLottryPurchase();
	}
	//7.1 
	private void testOvertPurchase(){
		Guest buyer1 = new Guest();
		try{
			BlMain.addImmediatelyProduct(buyer1,prod1, 1 );
			//test buy all products
			try{
				assertTrue(BlMain.purchaseCart(buyer1, "132456789113223", "Ashdod"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//7.2 
	private void testLottryPurchase(){
		Guest buyer2 = new Guest();
		try{
			BlMain.addLotteryProduct(buyer2,prod3, 40 );
			//test buy all products
			try{
				assertTrue(BlMain.purchaseCart(buyer2, "132456789165423", "Ashdod"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
