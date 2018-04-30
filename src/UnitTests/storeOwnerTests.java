package UnitTests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class storeOwnerTests {

	private static StoreOwner so;
	private static Store s;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		
		s=new Store(123, "hadekel 72 Ranana", "1542365985", 3, new HashMap<Product,Integer>(),new LinkedList<Purchase> (), true);
		so=new StoreOwner(s);
	}
	
	@Test
	public void mainTest()
	{
		testAddProductToStore();
		testUpdateProductDetails();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		testDeleteProductFromStore();
		testAddNewStoreOwner();
		testAddNewManager();
		testCloseStore();
		testOpenStore();
		testGetPurchaseHistory();
		testExpiredProducts();
	}
	private void testAddProductToStore() {
		Product product=new Product("234", "ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addProductToStore(so, product, -1));
		assertFalse(BlMain.addProductToStore(so, null, 1));
		StoreOwner so2=null;
		assertFalse(BlMain.addProductToStore(so2, product, 1));
		Store store=so.getStore();
		so.setStore(null);
		assertFalse(BlMain.addProductToStore(so, product, 1));
		so.setStore(store);
		assertTrue(BlMain.addProductToStore(so, product, 1));
		assertTrue(BlMain.addProductToStore(so, product, 1));
		assertFalse(BlMain.addProductToStore(so, product, 0));
		assertTrue(BlMain.addProductToStore(so, product, -1));
		assertFalse(BlMain.addProductToStore(so, product, -2));//there is only one product in stock
		Product product2=new Product("234", "iphone x", 250, 6, "cell phones", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addProductToStore(so, product2, 1));//same id
		
	}
	private void testUpdateProductDetails() {
		Product oldProduct=new Product("234", "ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		Product newProduct=new Product("234", "ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		
		assertFalse(BlMain.updateProductDetails(so, oldProduct, newProduct, -1));
		assertFalse(BlMain.updateProductDetails(so, oldProduct, null, 1));
		assertFalse(BlMain.updateProductDetails(so, null, newProduct, 1));
		StoreOwner so2=null;
		assertFalse(BlMain.updateProductDetails(so2, oldProduct, newProduct, 1));
		newProduct.setId("678");
		assertFalse(BlMain.updateProductDetails(so, oldProduct, newProduct, 1));//not same id
		assertFalse(BlMain.updateProductDetails(so, newProduct, oldProduct, 1));//product does not exist
		newProduct.setId("234");
		newProduct.setName("ping pong ball");
		assertTrue(BlMain.updateProductDetails(so, oldProduct, newProduct, 1));
		
	}
	private void testAddPolicyToProduct() {
		Product product=new Product("234", "ping pong ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addPolicyToProduct(so, null, product));
		assertFalse(BlMain.addPolicyToProduct(so, new PurchasePolicy(new LotteryPurchase(new java.sql.Date(0))), null));
		StoreOwner so2=null;
		assertFalse(BlMain.addPolicyToProduct(so2, new PurchasePolicy(new LotteryPurchase(new java.sql.Date(0))), product));
		product.setId("567");
		assertFalse(BlMain.addPolicyToProduct(so, new PurchasePolicy(new LotteryPurchase(new java.sql.Date(0))), product));//product does not exist 
		product.setId("234");
		assertTrue(BlMain.addPolicyToProduct(so, new PurchasePolicy(new LotteryPurchase(new java.sql.Date(0))), product));
	}
	private void testAddDiscountToProduct() {
		Product product=new Product("234", "ping pong ball", 5, 5, "toys", new PurchasePolicy(new LotteryPurchase(new java.sql.Date(0))));
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 30), product));
		BlMain.addPolicyToProduct(so, new PurchasePolicy(new ImmediatelyPurchase()), product);
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), -10), product));
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 130), product));
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 30), null));
		assertFalse(BlMain.addDiscountToProduct(so, new OvertDiscount(null, 30), product));
		assertFalse(BlMain.addDiscountToProduct(so, null, product));
		StoreOwner so2=null;
		assertFalse(BlMain.addDiscountToProduct(so2, new OvertDiscount(new java.sql.Date(0), 30), product));
		assertTrue(BlMain.addDiscountToProduct(so, new OvertDiscount(new java.sql.Date(0), 30), product));
	}
	private void testDeleteProductFromStore() {
		Product product2=new Product("234", "iphone x", 250, 6, "cell phones", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.deleteProductFromStore(so, product2));
		assertFalse(BlMain.deleteProductFromStore(so, null));
		StoreOwner so2=null;
		assertFalse(BlMain.deleteProductFromStore(so2, product2));
		Store store=so.getStore();
		so.setStore(null);
		Product product=new Product("234", "ping pong ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.deleteProductFromStore(so, product));
		so.setStore(store);
		assertTrue(BlMain.deleteProductFromStore(so, product));
		assertFalse(BlMain.deleteProductFromStore(so, product));
	}
	private void testAddNewStoreOwner() {
		assertFalse(BlMain.addNewStoreOwner(so, null));
		StoreOwner so2=null;
		assertFalse(BlMain.addNewStoreOwner(so2, so));
		so2=new StoreOwner(new Store(BlMain.getStoreId(), "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true));
		assertFalse(BlMain.addNewStoreOwner(so, so2));//not same store
		so2.setStore(so.getStore());
		assertTrue(BlMain.addNewStoreOwner(so, so2));
		assertTrue(so.getStore().getMyOwners().contains(so2));
	}
	private void testAddNewManager() {
		assertFalse(BlMain.addNewManager(so, null));
		StoreOwner so2=null;
		StoreManager sm=new StoreManager(so.getStore());
		assertFalse(BlMain.addNewManager(so2, sm));
		sm.setStore(new Store(BlMain.getStoreId(), "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true));
		assertFalse(BlMain.addNewManager(so, sm));//not same store
		sm.setStore(so.getStore());
		assertTrue(BlMain.addNewManager(so, sm));
		assertTrue(so.getStore().getMyManagers().contains(sm));
	}
	private void testCloseStore() {
		StoreOwner so2=null;
		assertFalse(BlMain.closeStore(so2));
		Store tempStore=so.getStore();
		so.setStore(null);
		assertFalse(BlMain.closeStore(so));
		so.setStore(tempStore);
		assertTrue(BlMain.closeStore(so));
		assertFalse(so.getStore().getIsOpen());
		assertFalse(BlMain.closeStore(so));//the store is close
		
	}
	private void testOpenStore() {
		StoreOwner so2=null;
		assertFalse(BlMain.openStore(so2));
		Store tempStore=so.getStore();
		so.setStore(null);
		assertFalse(BlMain.openStore(so));
		so.setStore(tempStore);
		assertTrue(BlMain.openStore(so));
		assertTrue(so.getStore().getIsOpen());
		assertFalse(BlMain.openStore(so));//the store is open
	}
	private void testGetPurchaseHistory() {
		assertTrue(BlMain.getPurchaseHistory(so).isEmpty());

	}
	private void testExpiredProducts() {
		fail("Not yet implemented");
	}

}
