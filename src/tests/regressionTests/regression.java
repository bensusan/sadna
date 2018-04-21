package tests.regressionTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class regression {
	
	private static Guest alex;
	private static SystemAdministrator amit;
	private static Guest ofir;
	private static Guest or;
	private static Guest sagiv;
	private static Store ofirStore;
	private static Store orStore;
	private static StoreManager orAtOfir;
	private static StoreManager sagivAtOr;
	private static StoreOwner sagivAtOfir;
	private static StoreOwner ofirOwnership;
	private static StoreOwner orOwnership;
	private static Product tennisProduct;
	private static Product pingpongProduct;
	private static Product golfProduct;
	private static Product finalMondialProduct;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		alex=new Guest();
		amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Cart>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		ofir=new Guest();
		or=new Guest();
		sagiv=new Guest();
	}

	@Test
	public void testSignUp()
	{
		ofir=BlMain.signUp(amit, "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		or=BlMain.signUp(or, "or123", "or123", "or ben susan", "raul valenberg 4 Rehovot", "0547217189", "3333333333333333");
		sagiv=BlMain.signUp(sagiv, "sagiv123", "sagiv123", "sagiv mapgavker", "herzel 12 Tel Aviv", "0526988521", "4444444444444444");
		assertTrue(BlMain.allSubscribers.contains(ofir));
		assertTrue(BlMain.allSubscribers.contains(or));
		assertTrue(BlMain.allSubscribers.contains(sagiv));
		assertFalse(BlMain.allSubscribers.contains(alex));
	}
	@Test
	public void testOpenStore() {
		ofirStore=BlMain.openStore((Subscriber) ofir, "ofir's store", "ofir's store");
		orStore=BlMain.openStore((Subscriber) or,"or's store","or's store");
		assertTrue(BlMain.getAllStores().contains(ofirStore));
		assertTrue(BlMain.getAllStores().contains(orStore));
		assertTrue(BlMain.getAllStoresWithThierProductsAndAmounts().keySet().contains(ofirStore));
		assertTrue(BlMain.getAllStoresWithThierProductsAndAmounts().keySet().contains(orStore));
		assertFalse(((Subscriber)ofir).getOwner().isEmpty());
		assertTrue(ofirStore.getMyOwners().contains(ofirOwnership));
		assertFalse(((Subscriber)or).getOwner().isEmpty());
		assertTrue(orStore.getMyOwners().contains(orOwnership));
		
	}
	@Test
	public void testAddNewManager() {
		boolean[]orPremissionAtOfir={false,false,false,false,false,false,false,false,false,false,false,false};
		orAtOfir =new StoreManager(orPremissionAtOfir, ofirStore);
		boolean[]sagivPremissionAtOr={true,true,true,true,true,false,false,false,false,false,false,false};
		sagivAtOr =new StoreManager(sagivPremissionAtOr, orStore);
		sagivAtOfir=new StoreOwner(ofirStore);
		List<StoreManager> sagivManagers=new LinkedList<StoreManager>();
		sagivManagers.add(sagivAtOr);
		List<StoreManager> orManagers=new LinkedList<StoreManager>();
		orManagers.add(orAtOfir);
		((Subscriber)or).setManager(orManagers);
		((Subscriber)sagiv).setManager(sagivManagers);
		List<StoreOwner> sagivOwners=new LinkedList<StoreOwner>();
		sagivOwners.add(sagivAtOfir);
		((Subscriber)sagiv).setOwner(sagivOwners);
		
		ofirOwnership=((Subscriber)ofir).getOwner().get(0);
		orOwnership=((Subscriber)or).getOwner().get(0);
		BlMain.addNewManager(ofirOwnership,orAtOfir);
		BlMain.addNewManager(orOwnership,sagivAtOr);
		assertTrue(ofirStore.getMyManagers().contains(orAtOfir));
		assertTrue(((Subscriber)or).getManager().contains(orAtOfir));
		assertTrue(orStore.getMyManagers().contains(sagivAtOr));
		assertTrue(((Subscriber)sagiv).getManager().contains(sagivAtOr));
	}

	@Test
	public void testAddNewStoreOwner() {
		BlMain.addNewStoreOwner(ofirOwnership,sagivAtOfir);
		assertTrue(ofirStore.getMyOwners().contains(sagivAtOfir));
		assertTrue(((Subscriber)sagiv).getOwner().contains(sagivAtOfir));
		assertTrue(ofirStore.getMyOwners().contains(ofirOwnership));
	}

	@Test
	public void testChangeStorePurchasePolicy() {
		BlMain.changeStorePurchasePolicy(ofirOwnership, new PurchasePolicy(null));
		assertEquals(ofirStore.getStorePolicy().getPurchaseType(),null);
	}
	@Test
	public void testAddProductToStore() {
		tennisProduct=new Product("111", "tennis ball", 5, 1, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		pingpongProduct=new Product("112", "ping pong ball", 1, 2, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		golfProduct=new Product("113", "golf ball", 5, 3, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		finalMondialProduct=new Product("114", "ball from final mondial", 500, 5, "collectibles", new PurchasePolicy(new LotteryPurchase(new Date(2018,4,25))));
		BlMain.addProductToStore(ofirOwnership, tennisProduct, 50);
		BlMain.addProductToStore(ofirOwnership, pingpongProduct, 30);
		BlMain.addProductToStore(orOwnership,golfProduct,20);
		BlMain.addProductToStore(orOwnership, finalMondialProduct, 1);
		
		assertTrue(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet().contains(tennisProduct));
		assertTrue(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).keySet().contains(pingpongProduct));
		assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),50);
		assertTrue(orStore.getProducts().keySet().contains(finalMondialProduct));
		assertTrue(BlMain.findProductByCategory("toys").contains(golfProduct));
		assertTrue(BlMain.findProductByName("tennis ball").contains(tennisProduct));
	}
	
	@Test
	public void testAddDiscountToProduct() {
		BlMain.addDiscountToProduct(ofirOwnership, new OvertDiscount(new Date(2019,10,3), 20), tennisProduct);
		BlMain.addDiscountToProduct(orOwnership, new HiddenDiscount(336, new Date(2019,12,3), 40), golfProduct);
		if (tennisProduct.getPurchasePolicy().getPurchaseType() instanceof ImmediatelyPurchase)
		{
			assertEquals(((ImmediatelyPurchase)tennisProduct.getPurchasePolicy().getPurchaseType()).getDiscountPolicy().getDiscountPrecentage(),20);
			assertEquals(BlMain.getDiscountedPrice(((ImmediatelyPurchase)tennisProduct.getPurchasePolicy().getPurchaseType()), tennisProduct.getPrice()),4);
			if (golfProduct.getPurchasePolicy().getPurchaseType() instanceof ImmediatelyPurchase)
			{
				assertEquals(((ImmediatelyPurchase)golfProduct.getPurchasePolicy().getPurchaseType()).getDiscountPolicy().getDiscountPrecentage(),20);
				assertEquals(BlMain.updatePrice(((ImmediatelyPurchase)golfProduct.getPurchasePolicy().getPurchaseType()).getDiscountPolicy(), golfProduct.getPrice(), 336),3);
			}
			else{fail("error");}
		}
		else{fail("error");}
	}

	@Test
	public void testAddPolicyToProduct() {
		BlMain.addPolicyToProduct(sagivAtOfir, new PurchasePolicy(new ImmediatelyPurchase()), tennisProduct);
		assertTrue(tennisProduct.getPurchasePolicy().getPurchaseType()instanceof ImmediatelyPurchase);
		assertTrue(tennisProduct.getPurchasePolicy().getPurchaseType()instanceof ImmediatelyPurchase);
		assertTrue(tennisProduct.getPurchasePolicy().getPurchaseType()instanceof ImmediatelyPurchase);
		assertTrue(tennisProduct.getPurchasePolicy().getPurchaseType()instanceof LotteryPurchase);
	}


	@Test
	public void testAddProductToCart()
	{
		BlMain.addProductToCart(alex, tennisProduct, 10);
		assertEquals(ofirStore.getProducts().get(tennisProduct).intValue(),50);
	}
	

	@Test
	public void testPruchaseCart() {
		BlMain.purchaseCart(alex, "1111111111111111", "max shein 22 nes ziona");
		assertTrue(alex.getCart().getProducts().isEmpty());
		assertEquals(ofirStore.getProducts().get(tennisProduct).intValue(),40);
		assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(ofirStore).get(tennisProduct).intValue(),40);
		assertEquals(BlMain.viewStoreHistory(amit, ofirStore).get(0).getPurchased().getProducts().get(tennisProduct).intValue(),10);
		assertEquals(ofirStore.getMoneyEarned(),10*4);
		BlMain.addProductToCart(or, tennisProduct, 50);
		assertFalse(BlMain.purchaseCart(or, ((Subscriber)or).getCreditCardNumber(), ((Subscriber)or).getAddress()));
	}
	
	@Test
	public void testPurchaseProduct() {
		BlMain.pruchaseProduct(ofir, golfProduct, 10, ((Subscriber)ofir).getCreditCardNumber(), ((Subscriber)ofir).getAddress());
		assertEquals(orStore.getProducts().get(golfProduct).intValue(),10);
		assertEquals(BlMain.getAllStoresWithThierProductsAndAmounts().get(orStore).get(golfProduct).intValue(),10);
		assertEquals(BlMain.viewStoreHistory(amit, orStore).get(0).getPurchased().getProducts().get(golfProduct).intValue(),10);
		assertEquals(ofirStore.getMoneyEarned(),10*5);
	}
	
	@Test
	public void testRemoveSubscriber() {
		assertFalse(BlMain.removeSubscriber(amit, amit));
		assertTrue(BlMain.removeSubscriber(amit, (Subscriber)or));
		assertFalse(BlMain.getAllStores().contains(orStore));
		assertFalse(BlMain.getAllStoresWithThierProductsAndAmounts().containsKey(orStore));
		assertTrue(BlMain.findProductByName("golf ball").isEmpty());
		assertFalse(BlMain.allSubscribers.contains(or));
		for(Subscriber s:BlMain.allSubscribers)
		{
			if (s.getUsername().equals("sagiv123"))
			{
				assertFalse(s.getManager().contains(sagivAtOr));
			}
		}
	}
	


	//update
	
	@Test
	public void testUpdateProductDetails() {
		fail("Not yet implemented");
	}


	//delete
	@Test
	public void testRemoveProduct() {
		fail("Not yet implemented");
	}
	@Test
	public void testDeleteProductFromStoreStoreManagerProduct() {
		fail("Not yet implemented");
	}
	@Test
	public void testDeleteProductFromStoreStoreOwnerProduct() {
		fail("Not yet implemented");
	}
	@Test
	public void testDeleteOwner() {
		fail("Not yet implemented");
	}
	@Test
	public void testDeleteManager() {
		fail("Not yet implemented");
	}


}
