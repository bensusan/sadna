package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class storeManagerTests {
	
	private static StoreManager sm;
	private static boolean arr[];
	private static Store s;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		arr=new boolean[11];
		for (int i=0;i<arr.length;i++)
			arr[i]=false;
		s=new Store();
		sm=new StoreManager(arr, s);
	}
	@Test
	public void testAddProductToStore() {
		Product product=new Product("569", "ball", 10, 7, "toys", null);
		assertFalse(BlMain.addProductToStore(sm, product, 5));
		arr[BlMain.addProductToStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addProductToStore(sm, product, 5));
	}

	@Test
	public void testUpdateProductDetails() {
		
		Product oldProduct=new Product("569", "ball", 10, 7, "toys", null);
		Product newProduct=new Product("569", "ball", 10, 7, "toys", null);
		newProduct.setPrice(8);
		assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
		arr[BlMain.updateProductDetails]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
	}
	
	@Test
	public void testDeleteProductFromStore() {
		Product product=new Product("569", "ball", 8, 7, "toys", null);
		assertFalse(BlMain.deleteProductFromStore(sm, product));
		arr[BlMain.deleteProductFromStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.deleteProductFromStore(sm, product));
	}


	@Test
	public void testAddPolicyToProduct() {
		PurchasePolicy policy=new PurchasePolicy(new ImmediatelyPurchase());
		Product product=new Product("569", "ball", 8, 7, "toys", null);
		BlMain.addProductToStore(sm, product, 5);
		assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
		arr[BlMain.addPolicyToProduct]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
	}

	@Test
	public void testAddDiscountToProduct() {
		Product product=new Product("569", "ball", 8, 7, "toys", null);
		DiscountPolicy discount=new OvertDiscount(null, 30);
		assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
		arr[BlMain.addDiscountToProduct]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
	}

	@Test
	public void testAddNewStoreOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewManager() {
		StoreManager nsm=new StoreManager(arr, sm.getStore());
		assertFalse(BlMain.addNewManager(sm, nsm));
		arr[BlMain.addNewManager]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addNewManager(sm, nsm));
	}

	@Test
	public void testCloseStore() {
		assertFalse(BlMain.closeStore(sm));
		arr[BlMain.closeStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.closeStore(sm));
	}

	@Test
	public void testOpenStore() {
		assertFalse(BlMain.openStore(sm));
		arr[BlMain.openStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.openStore(sm));
	}

	@Test
	public void testGetPurchaseHistory() {
		assertTrue(BlMain.getPurchaseHistory(sm).isEmpty());
	}

	@Test
	public void testExpiredProducts() {
		fail("Not yet implemented");
	}

}
