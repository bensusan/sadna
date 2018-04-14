package tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class storeOwnerTests {

	private StoreOwner so;
	private Store s;
	
	@BeforeClass
    public  void oneTimeSetUp() {
		
		s=new Store(123, "hadekel 72 Ranana", 1542365985, 3, new HashMap<>(),new LinkedList<Cart> (), true);
		so=new StoreOwner(s);
	}
	
	@Test
	public void testAddProductToStore() {
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
		assertFalse(BlMain.addProductToStore(so, product, -2));
		Product product2=new Product("234", "iphone x", 250, 6, "cell phones", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addProductToStore(so, product2, 1));
		
	}
	@Test
	public void testUpdateProductDetails() {
		Product oldProduct=new Product("234", "ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		Product newProduct=new Product("895", "ball", 5, 5, "toys", new PurchasePolicy(new ImmediatelyPurchase()));
		
		assertFalse(BlMain.updateProductDetails(so, oldProduct, newProduct, -1));
		assertFalse(BlMain.updateProductDetails(so, oldProduct, null, 1));
		assertFalse(BlMain.updateProductDetails(so, null, newProduct, 1));
		StoreOwner so2=null;
		assertFalse(BlMain.updateProductDetails(so2, oldProduct, newProduct, 1));
		assertFalse(BlMain.updateProductDetails(so, oldProduct, newProduct, 1));//not same id
		assertFalse(BlMain.updateProductDetails(so2, newProduct, oldProduct, 1));//product does not exist
		newProduct.setId("234");
		newProduct.setName("ping pong ball");
		assertTrue(BlMain.updateProductDetails(so, oldProduct, newProduct, 1));
		
	}

	@Test
	public void testDeleteProductFromStore() {
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

	@Test
	public void testAddPolicyToProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDiscountToProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewStoreOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseStore() {
		fail("Not yet implemented");
	}

	@Test
	public void testOpenStore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPurchaseHistory() {
		fail("Not yet implemented");
	}

	@Test
	public void testExpiredProducts() {
		fail("Not yet implemented");
	}

}
