package IntegrationTests;

import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.Cart;
import TS_SharedClasses.Guest;
import TS_SharedClasses.ImmediatelyPurchase;
import TS_SharedClasses.Product;
import TS_SharedClasses.ProductInCart;
import TS_SharedClasses.Purchase;
import TS_SharedClasses.PurchasePolicy;
import TS_SharedClasses.Store;
import TS_SharedClasses.StoreManager;
import TS_SharedClasses.StoreOwner;
import TS_SharedClasses.Subscriber;
import TS_SharedClasses.SystemAdministrator;

public class GuestRegTests {

	private static Guest ofir;
	private static Store ofirStore;
	private static StoreOwner ofirOwnership;
	private static Product tennisProduct;
	private static Guest alex;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		ofirStore=BlMain.openStore((Subscriber) ofir,"ofir's store",5, new HashMap<Product, Integer>(), new LinkedList<Purchase>(),true);
		tennisProduct=new Product("tennis ball", 5, 1, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		ofirOwnership=((Subscriber)ofir).getOwner().get(0);
		BlMain.addProductToStore(ofirOwnership, tennisProduct, 50);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		BlMain.removeSubscriber(amit, (Subscriber)ofir);
	}
	@Test
	public void testSignUp() {
		alex=BlMain.signUp(alex, "alexuser", "alexpass", "alex", "alex address", "0543545678", "9876543212345678");
		assertTrue(BlMain.allSubscribers.contains(alex));
		assertEquals(BlMain.signIn(alex, "alexuser", "alexpass"),alex);
	}
	@Test
	public void testAddProductToCart() {
		BlMain.addImmediatelyProduct(alex, tennisProduct, 10);
		for(ProductInCart p :alex.getCart().getProducts())
		{
			if(p.getMyProduct().equals(tennisProduct))
			{
				assertEquals(p.getAmount(),10);
			}
		}
		assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),50);
		BlMain.removeProductFromCart(alex, tennisProduct);
	}
	
	@Test
	public void testLogIn() {
		alex=new Guest();
		BlMain.addImmediatelyProduct(alex, tennisProduct, 20);
		alex=BlMain.signIn(alex, "alexuser", "alexpass");
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
	

	@Test
	public void testPuchaseCart() {
		BlMain.purchaseCart(alex, ((Subscriber)alex).getCreditCardNumber(), ((Subscriber)alex).getAddress());
		assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),30);
		assertTrue(alex.getCart().getProducts().isEmpty());
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		
		List<Purchase>l=BlMain.viewStoreHistory(amit, ofirStore);
		for (Purchase p:l)
		{
			if(p.getPurchased().getMyProduct().equals(tennisProduct)){
				assertEquals(p.getPurchased().getAmount(),20);
			}
		}
		l=BlMain.viewSubscriberHistory(amit, (Subscriber)alex);
		for(Purchase p:l)
		{
			if(p.getPurchased().getMyProduct().equals(tennisProduct)){
				assertEquals(p.getPurchased().getAmount(),20);
			}
		}
	}

	

	

}
