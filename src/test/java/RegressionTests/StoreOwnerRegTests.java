package RegressionTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;


public class StoreOwnerRegTests {

	private static Subscriber ofir;
	private static Subscriber or;
	private static Subscriber sagiv;
	private static Store ofirStore;
	private static StoreOwner ofirOwnership;
	private static Product tennisProduct;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		ofirStore=BlMain.openStore((Subscriber) ofir,"ofir's store",5,true);
		ofirOwnership=((Subscriber)ofir).getOwner().get(0);
		tennisProduct=new Product("tennis ball", 5, 1, new EmptyPolicy(),new ImmediatelyPurchase());
		
	}
	@Test
	public void mainTests(){
		testAddProductToStore();
		testUpdateProductDetails();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		testAddDiscountToCategoryStore();
		testChangeStorePurchasePolicy();
		testDeleteProductFromStore();
		testAddNewStoreOwner();
		testAddNewManager();
	}
	private void testAddProductToStore() {
		try {
			BlMain.addProductToStore(ofirOwnership, tennisProduct, 20,"toys");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertTrue(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).containsKey(tennisProduct));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),20);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testUpdateProductDetails() {
		Guest g=new Guest();
		try {
			BlMain.addImmediatelyProduct(g, tennisProduct, 10);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Product newTennisProduct = new Product("new tennis ball", 10, 1, new EmptyPolicy(), new ImmediatelyPurchase());
			
			try {
				BlMain.updateProductDetails(ofirOwnership, tennisProduct, newTennisProduct, 30,"toys");
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			assertTrue(g.getCart().getProducts().size()>0);
			for (Product p:BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet()){
				if (p.getName().equals("new tennis ball"))
				{
					assertEquals(p.getPrice(),10);
					assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(p).intValue(),30);
				}
			}
			tennisProduct=newTennisProduct;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void testAddPolicyToProduct() {
		try {
			BlMain.addPolicyToProduct(ofirOwnership, new MaxPolicy(null,3), tennisProduct);
			for(Product p:BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet())
			{
				if (p.getName().equals("new tennis ball")){
					assertTrue(p.getPurchasePolicy() instanceof MaxPolicy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testAddDiscountToProduct() {
		try {
			BlMain.addDiscountToProduct(ofirOwnership, new EmptyPolicy(new OvertDiscount(new Date(2019,10,3), 20)), tennisProduct);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try{
			for(Product p:BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet())
			{
				if (p.getName().equals("new tennis ball")){
					assertEquals(((ImmediatelyPurchase)p.getType()).getDiscountTree().getCurrDiscount().getDiscountPrecentage(),20);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testAddDiscountToCategoryStore(){
		try {
			BlMain.addDiscountToCategoryStore(ofirOwnership, new EmptyPolicy(new OvertDiscount(new Date(2019,10,3), 20)), "toys");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		for(Category c:ofirStore.getCategoryDiscounts().keySet())
		{
			if(c.getName().equals("toys"))
			{
				assertTrue(ofirStore.getCategoryDiscounts().get(c).getCurrDiscount() instanceof  OvertDiscount);
			}
		}
		
	}
	private void testChangeStorePurchasePolicy(){
		try {
			BlMain.changeStorePurchasePolicy(ofirOwnership, new MinPolicy(null,3));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertTrue(ofirStore.getStorePolicy() instanceof MinPolicy);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void testDeleteProductFromStore() {
		Guest g=new Guest();
		try {
			BlMain.addImmediatelyProduct(g, tennisProduct, 10);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.deleteProductFromStore(ofirOwnership, tennisProduct);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertFalse(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).containsKey(tennisProduct));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		boolean productdelete=true;
		for(ProductInCart p2 :g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(tennisProduct))
			{
				productdelete=false;
			}
		}
		assertFalse(productdelete);
	}
	private void testAddNewStoreOwner() {
		try {
			sagiv=BlMain.signUp(new Guest(), "sagiv123", "sagiv123", "sagiv mapgavker", "herzel 12 Tel Aviv", "0526988521", "4444444444444444");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		int storeown=ofirStore.getMyOwners().size();
		try{
			Subscriber s=BlMain.getSubscriberFromUsername("sagiv123");
			int before=s.getOwner().size();
			try {
				BlMain.addNewStoreOwner(ofirOwnership, s);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			assertEquals(s.getOwner().size(),before+1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(ofirStore.getMyOwners().size(),storeown+1);
	}
	private void testAddNewManager() {
		try {
			or=BlMain.signUp(new Guest(), "or123", "or123", "or ben susan", "raul valenberg 4 Rehovot", "0547217189", "3333333333333333");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		int manBef=ofirStore.getMyManagers().size();
		try{
			Subscriber s=BlMain.getSubscriberFromUsername("or123");
			int before=s.getManager().size();
			try {
				BlMain.addNewManager(ofirOwnership, s);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			assertEquals(s.getManager().size(),before+1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	
		assertEquals(ofirStore.getMyManagers().size(),manBef+1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		 SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		 BlMain.removeSubscriber(amit, ofir);
		 BlMain.removeSubscriber(amit, or);
		 BlMain.removeSubscriber(amit, sagiv);
	}
	
}
