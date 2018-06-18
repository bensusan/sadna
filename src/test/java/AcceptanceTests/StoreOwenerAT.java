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
		try{
			sub = BlMain.signUp(g, "StoreOwenerAT111", "globPass", "usr", "name", "132412356", "1234567891011");
			Store s1 = BlMain.openStore(sub,"store_name6", 5, true);
			List<StoreOwner> own1 = sub.getOwner();
			so = own1.get(0);

			try {
				prod1 = new Product("prod1", 200, 4, new EmptyPolicy(), 
						new ImmediatelyPurchase(new EmptyPolicy(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
				prod11 = new Product("prod1", 200, 4, new EmptyPolicy(),
						new ImmediatelyPurchase(new EmptyPolicy(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
				prod111 = new Product("prod1", 200, 4, new EmptyPolicy(),
						new ImmediatelyPurchase(new EmptyPolicy(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
				prod2 = new Product("prod2", 200, 4, new EmptyPolicy(),
						new ImmediatelyPurchase());
				prod3 = new Product("prod3", 100, 4, new EmptyPolicy(),
						new LotteryPurchase(Date.valueOf("2019-01-01")));
				prod33 = new Product("prod3", 100, 4, new EmptyPolicy(), 
						new LotteryPurchase(Date.valueOf("2019-01-01")));
				prod4 = new Product("prod4", 200, 4, new EmptyPolicy(),
						new ImmediatelyPurchase());
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
	@Test
	public void mainTest(){
		testAddProduct();
		testRemoveProduct();
		testEditProduct();
		testDefinePurchasePolicy();
		testDefineDiscountPolicy();
		testAddStoreOwner();
		testAddStoreManager();
		testWatchPurchaseHistory();
	}

	//3.1.3
	private void testAddProduct(){
		//test incorret input
		try {
			BlMain.addProductToStore(so, prod2, -1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "amount must be greater than 0");
		}
		try {
			BlMain.addProductToStore(so, prod2, 0,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "amount must be greater than 0");
		}
		try {
			BlMain.addProductToStore(so, null, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		StoreOwner so2 = null;
		try {
			BlMain.addProductToStore(so2, prod2, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}

		//good case
		try {
			assertTrue(BlMain.addProductToStore(so, prod2, 1,"toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//add the same product with diff amount
		try {
			assertTrue(BlMain.addProductToStore(so, prod2, 2,"toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		Store s = so.getStore();
		assertEquals(3, s.getProducts().get(prod2).intValue());

	}
	//3.1.2
	private void testRemoveProduct(){
		try{
			BlMain.addProductToStore(so, prod4, 10,"toys");
			//test incorret input
			try{
				BlMain.deleteProductFromStore(so, null);
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "something went wrong");
			}
			StoreOwner so2 = null;
			try{
				assertFalse(BlMain.deleteProductFromStore(so2, prod4));
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "something went wrong");
			}
			//good case
			assertTrue(BlMain.deleteProductFromStore(so, prod4));
			Store s = so.getStore();
			assertFalse(s.getProducts().keySet().contains(prod4));

			//not exist product
			Product notExits = new Product("notExits", 200, 4, new EmptyPolicy(), 
					new ImmediatelyPurchase());
			try{
				assertFalse(BlMain.deleteProductFromStore(so, notExits));
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "something went wrong");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	//3.1.1
	private void testEditProduct(){
		try {
			BlMain.addProductToStore(so, prod1, 10,"toys");
			Product newProduct = new Product("newProduct", 200, 4, new EmptyPolicy(), new ImmediatelyPurchase());

			//incorrect Input
			try{
				BlMain.updateProductDetails(so, prod1, newProduct, -1,"toys");
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "amount must be greater than 0");
			}
			try{
				BlMain.updateProductDetails(so, prod1, newProduct, 0,"toys");
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "amount must be greater than 0");
			}
			try{
				BlMain.updateProductDetails(so, prod1, null, 1,"toys");
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "something went wrong");
			}
			try{
				BlMain.updateProductDetails(so, null, newProduct, 1,"toys");
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "something went wrong");
			}
			StoreOwner so2 = null;
			try{
				BlMain.updateProductDetails(so2, prod1, newProduct, 1,"toys");
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "something went wrong");
			}

			//not Exist
			Product notExits = new Product("notExits", 200, 4, new EmptyPolicy(), new ImmediatelyPurchase());
			assertTrue(BlMain.updateProductDetails(so, prod1, notExits, 1,"toys"));

			//good case
			assertTrue(BlMain.updateProductDetails(so, notExits, newProduct, 1,"toys"));
			Store s = so.getStore();
			assertFalse(s.getProducts().keySet().contains(notExits));
			assertTrue(s.getProducts().keySet().contains(newProduct));
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	//3.2.1
	private void testDefinePurchasePolicy(){
		try {
			BlMain.addProductToStore(so, prod1, 10,"toys");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//incorrect Input
		try {
			BlMain.addPolicyToProduct(so, null, prod1);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		try {
			BlMain.addPolicyToProduct(so, new EmptyPolicy(), null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		StoreOwner so2 = null;
		try {
			BlMain.addPolicyToProduct(so2, new EmptyPolicy(), prod1);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}

		//good case
		try{
			PurchasePolicy newPolicy =new EmptyPolicy();
			try {
				assertTrue(BlMain.addPolicyToProduct(so, newPolicy, prod1));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//add policy to non exists prod
		try{
		Product notExits = new Product("notExits", 200, 4, new EmptyPolicy(), new ImmediatelyPurchase());
		BlMain.addPolicyToProduct(so, new MaxPolicy(null,3), notExits);
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "something went wrong");
		}
	}
	//3.2.2
	private void testDefineDiscountPolicy(){
		try {
			boolean a = BlMain.addProductToStore(so, prod11, 10,"toys");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		//incorrect Input
		try {
			BlMain.addDiscountToProduct(so, null, prod11);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 10)), null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		StoreOwner so2 = null;
		try {
			BlMain.addDiscountToProduct(so2, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 10)), prod11);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		//not found
		Product notExits;
		try {
			notExits = new Product("notExits", 200, 4, new EmptyPolicy(),new ImmediatelyPurchase());

			try {
				BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 10)), notExits);
			} catch (Exception e) {
				assertEquals(e.getMessage(), "something went wrong");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//good case
		try {
			assertTrue(BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 10)), prod11));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		try {
			BlMain.addProductToStore(so, prod2, 10,"toys");
			assertTrue(BlMain.addDiscountToProduct(so, new EmptyPolicy(new HiddenDiscount(123, Date.valueOf("2019-01-01"), 20)), prod2));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	//3.3
	private void testAddStoreOwner(){
		StoreOwner so2 = new StoreOwner(so.getStore());

		//incorrect Input
		try {
			BlMain.addNewStoreOwner(so, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		StoreOwner nullso = null;
		try {
			BlMain.addNewStoreOwner(nullso, sub);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}

		//need to add function add new store owner from subscriber
		try {
			assertTrue(BlMain.addNewStoreOwner(so2, sub));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	//3.4
	private void testAddStoreManager(){
		StoreManager sm = new StoreManager(so.getStore());

		//incorrect Input
		try {
			assertFalse(BlMain.addNewManager(so, null));
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}
		StoreOwner nullso = null;
		try {
			assertFalse(BlMain.addNewManager(nullso, sub));
		} catch (Exception e) {
			assertEquals(e.getMessage(), "something went wrong");
		}


		//need to add function add new man owner from subscriber
		try {
			assertTrue(BlMain.addNewManager(so, sub));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	//3.7
	private void testWatchPurchaseHistory(){
		try{
			BlMain.addProductToStore(so, prod33, 22,"toys");
			BlMain.addProductToStore(so, prod111, 22,"toys");
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
		catch(Exception e){
			e.printStackTrace();
			fail();
		}

	}
}











