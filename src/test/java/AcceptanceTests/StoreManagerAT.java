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

public class StoreManagerAT {

	private static Guest g;
	private static Subscriber sub;
	private static StoreOwner so;
	private static StoreManager sm;
	private static Product prod1;
	private static Product prod2;
	private static Product prod3;
	private static Product prod4;
	private static boolean[] arr;
	
	@BeforeClass
	public static void beforeTests(){
		g = new Guest();
		sub = BlMain.signUp(g, "StoreManagerAT", "globPass", "usr", "name", "132412356", "1234567891011");
		Store s1 = BlMain.openStore(sub,"store_name5",  5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), false);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		BlMain.addProductToStore(so, prod1, 10);
		BlMain.addProductToStore(so, prod2, 10);
		BlMain.addProductToStore(so, prod3, 10);
		BlMain.addProductToStore(so, prod4, 10);
		
		
		for (int i=0;i<12 ;i++)
			sm.setSpecificPermission(i, false);
		BlMain.addNewManager(so, sub);
		
	}
	@Test
	public void mainTest(){
		testAddProductToStore();
		testUpdateProductDetails();
		testDeleteProductFromStore();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		testAddNewStoreOwner();
		testAddNewManager();
		testCloseStore();
		testOpenStore();
		testGetPurchaseHistory();
	}
	//4.1
	private void testAddProductToStore() {
		Product product=new Product("ball", 10, 7, "toys", null);
		assertFalse(BlMain.addProductToStore(sm, product, 5));

		sm.setSpecificPermission(BlMain.addProductToStore, true);
		assertTrue(BlMain.addProductToStore(sm, product, 5));
	}

	private void testUpdateProductDetails() {
		
		Product oldProduct=new Product("ball", 10, 7, "toys", null);
		Product newProduct=new Product("ball", 10, 7, "toys", null);
		newProduct.setPrice(8);
		assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
		sm.setSpecificPermission(BlMain.updateProductDetails, true);
		assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
	}
	
	private void testDeleteProductFromStore() {
		Product product=new Product("ball", 8, 7, "toys", null);
		assertFalse(BlMain.deleteProductFromStore(sm, product));
		sm.setSpecificPermission(BlMain.deleteProductFromStore, true);
		assertTrue(BlMain.deleteProductFromStore(sm, product));
	}

	private void testAddPolicyToProduct() {
		PurchasePolicy policy;
		try {
			policy = new PurchasePolicy(new ImmediatelyPurchase());
			Product product=new Product("ball", 8, 7, "toys", new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
			BlMain.addProductToStore(sm, product, 5);
			assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
			sm.setSpecificPermission(BlMain.addPolicyToProduct, true);
			assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
		} catch (Exception e) {
			fail();
		}
		
	}

	private void testAddDiscountToProduct() {
		Product product=new Product("ball", 8, 7, "toys", null);
		DiscountPolicy discount;
		try {
			discount = new OvertDiscount(null, 30);
			assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
			sm.setSpecificPermission(BlMain.addDiscountToProduct, true);
			assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	private void testAddNewStoreOwner() {
		fail("Not yet implemented");
	}

	private void testAddNewManager() {
		StoreManager nsm=new StoreManager(sm.getStore());
		assertFalse(BlMain.addNewManager(sm, sub));
		sm.setSpecificPermission(BlMain.addNewManager, true);
		assertTrue(BlMain.addNewManager(sm, sub));
	}

	private void testCloseStore() {
		assertFalse(BlMain.closeStore(sm));
		sm.setSpecificPermission(BlMain.closeStore, true);
		assertTrue(BlMain.closeStore(sm));
	}

	private void testOpenStore() {
		assertFalse(BlMain.openStore(sm));
		sm.setSpecificPermission(BlMain.openStore, true);
		assertTrue(BlMain.openStore(sm));
	}

	private void testGetPurchaseHistory() {
		assertTrue(BlMain.getPurchaseHistory(sm).isEmpty());
	}

	

}
