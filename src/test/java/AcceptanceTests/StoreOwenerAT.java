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

public class StoreOwenerAT {

	private static Guest g;
	private static Subscriber sub;
	private static StoreOwner so;
	private static Product prod1;
	private static Product prod11;
	private static Product prod111;
	private static Product prod2;
	private static Product prod3;
	private static Product prod33;
	private static Product prod4;
	
	@BeforeClass
	public static void beforeTests(){
		g = new Guest();
		sub = BlMain.signUp(g, "StoreOwenerAT", "globPass", "usr", "name", "132412356", "1234567891011");
		Store s1 = BlMain.openStore(sub,"store_name6", 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		List<StoreOwner> own1 = sub.getOwner();
		so = own1.get(0);
		
		try {
			prod1 = new Product("prod1", 200, 4, "test cat 1", 
					new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
			prod11 = new Product("prod1", 200, 4, "test cat 1", 
					new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
			prod111 = new Product("prod1", 200, 4, "test cat 1", 
					new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
			prod2 = new Product("prod2", 200, 4, "test cat 2", 
					new PurchasePolicy(new ImmediatelyPurchase()));
			prod3 = new Product("prod3", 100, 4, "test cat 3", 
					new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
			prod33 = new Product("prod3", 100, 4, "test cat 3", 
					new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
			prod4 = new Product("prod4", 200, 4, "test cat 4", 
					new PurchasePolicy(new ImmediatelyPurchase()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		

	}
	@Test
	public void mainTest(){
		testAddProduct();
		try {
			testRemoveProduct();
			testEditProduct();
			testDefinePurchasePolicy();
			testDefineDiscountPolicy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		testAddStoreOwner();
		testAddStoreManager();
		testWatchPurchaseHistory();
	}
	
	//3.1.3
	private void testAddProduct(){
		//test incorret input
		assertFalse(BlMain.addProductToStore(so, prod2, -1));
		assertFalse(BlMain.addProductToStore(so, prod2, 0));
		assertFalse(BlMain.addProductToStore(so, null, 1));
		StoreOwner so2 = null;
		assertFalse(BlMain.addProductToStore(so2, prod2, 1));
		
		//good case
 		assertTrue(BlMain.addProductToStore(so, prod2, 1));
		//add the same product with diff amount
		assertFalse(BlMain.addProductToStore(so, prod2, 2));
		Store s = so.getStore();
		assertEquals(1, s.getProducts().get(prod2).intValue());
		
	}
	//3.1.2
	private void testRemoveProduct() throws Exception{
		BlMain.addProductToStore(so, prod4, 10);
		//test incorret input
		assertFalse(BlMain.deleteProductFromStore(so, null));
		StoreOwner so2 = null;
		assertFalse(BlMain.deleteProductFromStore(so2, prod4));
		
		//good case
		assertTrue(BlMain.deleteProductFromStore(so, prod4));
		Store s = so.getStore();
		assertFalse(s.getProducts().keySet().contains(prod4));
		
		//not exist product
		Product notExits = new Product("notExits", 200, 4, "test cat 2", 
				new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.deleteProductFromStore(so, notExits));
		
	}
	//3.1.1
	private void testEditProduct() throws Exception{
		BlMain.addProductToStore(so, prod1, 10);
		Product newProduct = new Product("newProduct", 200, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		
		//incorrect Input
		assertFalse(BlMain.updateProductDetails(so, prod1, newProduct, -1));
		assertFalse(BlMain.updateProductDetails(so, prod1, newProduct, 0));
		assertFalse(BlMain.updateProductDetails(so, prod1, null, 1));
		assertFalse(BlMain.updateProductDetails(so, null, newProduct, 1));
		StoreOwner so2 = null;
		assertFalse(BlMain.updateProductDetails(so2, prod1, newProduct, 1));
		
		//not Exist
		Product notExits = new Product("notExits", 200, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		assertTrue(BlMain.updateProductDetails(so, prod1, notExits, 1));
		
		//good case
		assertTrue(BlMain.updateProductDetails(so, notExits, newProduct, 1));
		Store s = so.getStore();
		assertFalse(s.getProducts().keySet().contains(notExits));
		assertTrue(s.getProducts().keySet().contains(newProduct));
	}
	//3.2.1
	private void testDefinePurchasePolicy() throws Exception{
		BlMain.addProductToStore(so, prod1, 10);
		
		//incorrect Input
		assertFalse(BlMain.addPolicyToProduct(so, null, prod1));
		assertFalse(BlMain.addPolicyToProduct(so, new PurchasePolicy(new ImmediatelyPurchase()), null));
		StoreOwner so2 = null;
		assertFalse(BlMain.addPolicyToProduct(so2, new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))), prod1));
		
		//good case
		PurchasePolicy newPolicy = new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01")));
		assertTrue(BlMain.addPolicyToProduct(so, newPolicy, prod1));
		
		//add policy to non exists prod
		Product notExits = new Product("notExits", 200, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addPolicyToProduct(so, new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))), notExits));
		
	}
	//3.2.2
	private void testDefineDiscountPolicy() throws Exception{
		boolean a = BlMain.addProductToStore(so, prod11, 10);
		
		//incorrect Input
		assertFalse(BlMain.addDiscountToProduct(so, null, prod11));
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 10), null));
		StoreOwner so2 = null;
		assertFalse(BlMain.addDiscountToProduct(so2, new OvertDiscount(new java.sql.Date(0), 10), prod11));
		//not found
		Product notExits = new Product("notExits", 200, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 10), notExits));
		//good case
		assertTrue(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 10), prod11));
		
		BlMain.addProductToStore(so, prod2, 10);
		assertTrue(BlMain.addDiscountToProduct(so, new HiddenDiscount(123, Date.valueOf("2019-01-01"), 20), prod2));
	}
	//3.3
	private void testAddStoreOwner(){
		StoreOwner so2 = new StoreOwner(so.getStore());
		
		//incorrect Input
		assertFalse(BlMain.addNewStoreOwner(so, null));
		StoreOwner nullso = null;
		assertFalse(BlMain.addNewStoreOwner(nullso, sub));
		
		//need to add function add new store owner from subscriber
		assertTrue(BlMain.addNewStoreOwner(so, sub));
		
	}
	//3.4
	private void testAddStoreManager(){
		StoreManager sm = new StoreManager(so.getStore());
		
		//incorrect Input
		assertFalse(BlMain.addNewManager(so, null));
		StoreOwner nullso = null;
		assertFalse(BlMain.addNewManager(nullso, sub));
		
		
		//need to add function add new man owner from subscriber
		assertTrue(BlMain.addNewManager(so, sub));
	}
	//3.7
	private void testWatchPurchaseHistory(){
		BlMain.addProductToStore(so, prod33, 22);
		BlMain.addProductToStore(so, prod111, 22);
		Guest g1 = new Guest();
		Guest g2 = new Guest();
		
		BlMain.addImmediatelyProduct(g1, prod11, 1);
		BlMain.addLotteryProduct(g2, prod33, 40);
		
		BlMain.purchaseCart(g1, "123456789123", "holon");
		BlMain.purchaseCart(g2, "123456789123", "holon");
		
		List<Purchase> purchases = BlMain.getPurchaseHistory(so);
		boolean prod1_found = false;
		boolean prod3_found = false;
		for(Purchase p : purchases){
			if(prod11.equals(p.getPurchased().getMyProduct()))
				prod1_found = true;
			if(prod33.equals(p.getPurchased().getMyProduct()))
				prod3_found = true;
		}
		assertTrue(prod3_found);
		assertTrue(prod1_found);
		
	}
}










