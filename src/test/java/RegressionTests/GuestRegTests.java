package RegressionTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;


public class GuestRegTests {

	private static Guest ofir;
	private static Store ofirStore;
	private static StoreOwner ofirOwnership;
	private static Product tennisProduct;
	private static Guest alex;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		ofirStore=BlMain.openStore((Subscriber) ofir,"ofir's store",5,true);
		tennisProduct=new Product("tennis ball", 5, 1, new EmptyPolicy(), new ImmediatelyPurchase());
		ofirOwnership=((Subscriber)ofir).getOwner().get(0);
		BlMain.addProductToStore(ofirOwnership, tennisProduct, 50,"toys");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		BlMain.removeSubscriber(amit, (Subscriber)ofir);
	}
	@Test
	public void mainTest()
	{
		testSignUp();
		testAddProductToCart();
		testLogIn();
		testPuchaseCart();
	}
	private void testSignUp() {
		try {
			alex=BlMain.signUp(new Guest(), "alexuser", "alexpass", "alex", "alex address", "0543545678", "9876543212345678");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertNotNull(BlMain.getSubscriberFromUsername("alexuser"));
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		try {
			assertEquals(BlMain.signIn(alex, "alexuser", "alexpass"),alex);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private void testAddProductToCart() {
		try {
			BlMain.addImmediatelyProduct(alex, tennisProduct, 10);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		for(ProductInCart p :alex.getCart().getProducts())
		{
			if(p.getMyProduct().equals(tennisProduct))
			{
				assertEquals(p.getAmount(),10);
			}
		}
		try {
			assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),50);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		try {
			BlMain.removeProductFromCart(alex, tennisProduct);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	private void testLogIn() {
		alex=new Guest();
		try {
			BlMain.addImmediatelyProduct(alex, tennisProduct, 20);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			alex=BlMain.signIn(alex, "alexuser", "alexpass");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		boolean containProduct= false;
		for(ProductInCart p :alex.getCart().getProducts())
		{
			if(p.getMyProduct().equals(tennisProduct)){
				containProduct=true;
				assertEquals(p.getAmount(),20);
			}
		}
		assertTrue(containProduct);
	}
	
	private void testPuchaseCart() {
		try {
			BlMain.addPolicyToProduct(ofirOwnership, new AndPolicy(null,new LinkedList<PurchasePolicy>()
				{{add(new MinPolicy(null,3));add(new MaxPolicy(null,30));}}), tennisProduct);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		try {
			BlMain.addDiscountToProduct(ofirOwnership, new OrPolicy(new OvertDiscount(new Date(2019,7,7),20),new LinkedList<PurchasePolicy>()
					{{add(new MinPolicy(null,3));add(new MaxPolicy(null,30));}}), tennisProduct);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		try {
			BlMain.purchaseCart(alex, ((Subscriber)alex).getCreditCardNumber(), ((Subscriber)alex).getAddress());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),30);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		assertTrue(alex.getCart().getProducts().isEmpty());
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		
		List<Purchase> l=null;
		try {
			l = BlMain.viewStoreHistory(amit, ofirStore);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(l);
		for (Purchase p:l)
		{
			if(p.getPurchased().getMyProduct().equals(tennisProduct)){
				assertEquals(p.getPurchased().getAmount(),20);
			}
		}
		try {
			l=BlMain.viewSubscriberHistory(amit, (Subscriber)alex);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(l);
		for(Purchase p:l)
		{
			if(p.getPurchased().getMyProduct().equals(tennisProduct)){
				assertEquals(p.getPurchased().getAmount(),20);
			}
		}
	}

	

	

}
