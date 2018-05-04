package IntegrationTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.Guest;
import TS_SharedClasses.ImmediatelyPurchase;
import TS_SharedClasses.OvertDiscount;
import TS_SharedClasses.Product;
import TS_SharedClasses.ProductInCart;
import TS_SharedClasses.Purchase;
import TS_SharedClasses.PurchasePolicy;
import TS_SharedClasses.Store;
import TS_SharedClasses.StoreManager;
import TS_SharedClasses.StoreOwner;
import TS_SharedClasses.Subscriber;
import TS_SharedClasses.SystemAdministrator;

public class StoreOwnerRegTests {

	private static Subscriber ofir;
	private static Subscriber or;
	private static Subscriber sagiv;
	private static Store ofirStore;
	private static StoreManager orAtOfir;
	private static StoreOwner sagivAtOfir;
	private static StoreOwner ofirOwnership;
	private static Product tennisProduct;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		ofirStore=BlMain.openStore((Subscriber) ofir,"ofir's store",5, new HashMap<Product, Integer>(), new LinkedList<Purchase>(),true);
		ofirOwnership=((Subscriber)ofir).getOwner().get(0);
		tennisProduct=new Product("tennis ball", 5, 1, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		
	}
	@Test
	public void mainTests(){
		testAddProductToStore();
		testUpdateProductDetails();
		testDeleteProductFromStore();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		testAddNewStoreOwner();
		testAddNewManager();
	}
	private void testAddProductToStore() {
		BlMain.addProductToStore(ofirOwnership, tennisProduct, 20);
		assertTrue(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).containsKey(tennisProduct));
		assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),20);
	}
	private void testUpdateProductDetails() {
		Guest g=new Guest();
		BlMain.addImmediatelyProduct(g, tennisProduct, 10);
		Product newTennisProduct =new Product("new tennis ball", 10, 1, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		
		BlMain.updateProductDetails(ofirOwnership, tennisProduct, newTennisProduct, 30);
		assertTrue(g.getCart().getProducts().size()>0);
		for (Product p:BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet()){
			if (p.getName().equals("new tennis ball"))
			{
				assertEquals(p.getPrice(),10);
				assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(p).intValue(),30);
			}
		}
		tennisProduct=newTennisProduct;
	}
	private void testDeleteProductFromStore() {
		Guest g=new Guest();
		BlMain.addImmediatelyProduct(g, tennisProduct, 10);
		BlMain.deleteProductFromStore(ofirOwnership, tennisProduct);
		assertFalse(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).containsKey(tennisProduct));
		boolean productdelete=true;
		for(ProductInCart p2 :g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(tennisProduct))
			{
				productdelete=false;
			}
		}
		assertTrue(productdelete);
	}
	private void testAddPolicyToProduct() {
		BlMain.addPolicyToProduct(ofirOwnership, new PurchasePolicy(new ImmediatelyPurchase()), tennisProduct);
		for(Product p:BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet())
		{
			if (p.getName().equals("new tennis ball")){
				assertTrue(p.getPurchasePolicy().getPurchaseType() instanceof ImmediatelyPurchase);
			}
		}
	}
	private void testAddDiscountToProduct() {
		BlMain.addDiscountToProduct(ofirOwnership, new OvertDiscount(new Date(2019,10,3), 20), tennisProduct);
		for(Product p:BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet())
		{
			if (p.getName().equals("new tennis ball")){
				assertEquals(((ImmediatelyPurchase)p.getPurchasePolicy().getPurchaseType()).getDiscountPolicy().getDiscountPrecentage(),20);
			}
		}
	}
	private void testAddNewStoreOwner() {
		sagiv=BlMain.signUp(sagiv, "sagiv123", "sagiv123", "sagiv mapgavker", "herzel 12 Tel Aviv", "0526988521", "4444444444444444");
		sagivAtOfir=new StoreOwner(ofirStore);
		BlMain.addNewStoreOwner(ofirOwnership, sagivAtOfir);
		for (Subscriber s:BlMain.allSubscribers)
		{
			if(s.getUsername().equals("sagiv123"))
			{
				assertTrue(s.getOwner().contains(sagivAtOfir));
			}
		}
		assertTrue(ofirStore.getMyOwners().contains(sagivAtOfir));
	}
	private void testAddNewManager() {
		or=BlMain.signUp(or, "or123", "or123", "or ben susan", "raul valenberg 4 Rehovot", "0547217189", "3333333333333333");
		orAtOfir =new StoreManager( ofirStore);
		BlMain.addNewManager(ofirOwnership, orAtOfir);
		for (Subscriber s:BlMain.allSubscribers)
		{
			if(s.getUsername().equals("or123"))
			{
				assertTrue(s.getManager().contains(orAtOfir));
			}
		}
		assertTrue(ofirStore.getMyManagers().contains(orAtOfir));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		 SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		 BlMain.removeSubscriber(amit, ofir);
		 BlMain.removeSubscriber(amit, or);
		 BlMain.removeSubscriber(amit, sagiv);
	}
	
}
