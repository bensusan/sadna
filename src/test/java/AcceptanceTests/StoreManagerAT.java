package AcceptanceTests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.Date;


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

	@BeforeClass
	public static void beforeTests(){
		g = new Guest();
		try{
			sub = BlMain.signUp(g, "StoreManagerAT", "globPass", "usr", "name", "132412356", "1234567891011");
			try{
				Store s1 = BlMain.openStore(sub,"store_name5",  5, true);
				sm = new StoreManager(s1);
				List<StoreOwner> own1 = sub.getOwner();
				so = own1.get(0);


				try {
					prod1 = new Product("prod1", 200, 4, new EmptyPolicy(), 
							new ImmediatelyPurchase(new EmptyPolicy(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
					prod2 = new Product("prod2", 200, 4,new EmptyPolicy(), 
							new ImmediatelyPurchase());
					prod3 = new Product("prod3", 100, 4,new EmptyPolicy(), 
							new LotteryPurchase(Date.valueOf("2019-01-01")));
					prod4 = new Product("prod4", 200, 4,new EmptyPolicy(),
							new ImmediatelyPurchase());
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}


				try {
					BlMain.addProductToStore(so, prod1, 10,"toys");
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
				try {
					BlMain.addProductToStore(so, prod2, 10,"toys");
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
				try {
					BlMain.addProductToStore(so, prod3, 10,"toys");
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
				try {
					BlMain.addProductToStore(so, prod4, 10,"toys");
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}


				for (int i=0;i<12 ;i++)
					sm.setSpecificPermission(i, false);
				try {
					BlMain.addNewManager(so, sub);
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	@Test
	public void mainTest(){
		testAddProductToStore();
		testUpdateProductDetails();
		testDeleteProductFromStore();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		//testAddNewStoreOwner();
		testAddNewManager();
		testCloseStore();
		testOpenStore();
		testGetPurchaseHistory();
	}
	//4.1
	private void testAddProductToStore() {
		try{
			Product product=new Product("ball", 10, 7, new EmptyPolicy(), null);
			try{
				assertFalse(BlMain.addProductToStore(sm, product, 5,"toys"));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
	
			sm.setSpecificPermission(BlMain.addProductToStore, true);
			try{
				assertTrue(BlMain.addProductToStore(sm, product, 5,"toys"));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testUpdateProductDetails() {

		try{
			Product oldProduct=new Product("ball", 10, 7, new EmptyPolicy(), null);
			Product newProduct=new Product("ball", 10, 7, new EmptyPolicy(), null);
			newProduct.setPrice(8);
			try{
				BlMain.addProductToStore(sm, oldProduct, 1,"toys");
				assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5,"toys"));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			sm.setSpecificPermission(BlMain.updateProductDetails, true);
			try{
				BlMain.addProductToStore(sm, oldProduct, 1,"toys");
				assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5,"toys"));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testDeleteProductFromStore() {
		try{
			Product product=new Product("ball", 8, 7, new EmptyPolicy(), null);
		
			try{
				BlMain.addProductToStore(sm, product, 1,"toys");
				BlMain.deleteProductFromStore(sm, product);
			}
			catch (Exception e) {
				assertEquals(e.getMessage(), "something went wrong");
			}
			sm.setSpecificPermission(BlMain.deleteProductFromStore, true);
			try{
				BlMain.addProductToStore(sm, product, 1,"toys");
				assertTrue(BlMain.deleteProductFromStore(sm, product));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testAddPolicyToProduct() {
		PurchasePolicy policy;
		try {
			policy = new EmptyPolicy();
			Product product=new Product("ball", 8, 7, new MaxPolicy(null, 3), new LotteryPurchase(Date.valueOf("2019-01-01")));
			BlMain.addProductToStore(sm, product, 5,"toys");
			assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
			sm.setSpecificPermission(BlMain.addPolicyToProduct, true);
			assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
		} catch (Exception e) {
		}

	}

	private void testAddDiscountToProduct() {
		try{
		Product product=new Product("ball", 8, 7, new EmptyPolicy(), new ImmediatelyPurchase());
		PurchasePolicy discount;
		try {
			discount=new EmptyPolicy(new OvertDiscount(null, 30));
			
			BlMain.addProductToStore(sm, product, 1,"toys");
			try{
			assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			sm.setSpecificPermission(BlMain.addDiscountToProduct, true);
			try{
			assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
			}
			catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	
	private void testAddNewManager() {
		//StoreManager nsm=new StoreManager(sm.getStore());
		try{
			assertFalse(BlMain.addNewManager(sm, sub));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.addNewManager, true);
		try{
			assertTrue(BlMain.addNewManager(sm, sub));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testCloseStore() {
		try {
			assertFalse(BlMain.closeStore(sm));
		} catch (Exception e) {
			fail();
		}
		sm.setSpecificPermission(BlMain.closeStore, true);
		try {
			assertTrue(BlMain.closeStore(sm));
		} catch (Exception e) {
			fail();
		}
	}

	private void testOpenStore() {
		try {
			assertFalse(BlMain.openStore(sm));
		} catch (Exception e) {
			fail();
		}
		sm.setSpecificPermission(BlMain.openStore, true);
		try {
			assertTrue(BlMain.openStore(sm));
		} catch (Exception e) {
			fail();
		}
	}

	private void testGetPurchaseHistory() {
		try {
			assertTrue(BlMain.getPurchaseHistory(sm).isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}



}
