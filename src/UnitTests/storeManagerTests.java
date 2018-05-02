package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

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
		s=new Store("newStore", "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true);
		sm=new StoreManager (s);
	}
	@Test
	public void mainTest(){
		testAddProductToStore();
		testUpdateProductDetails();
		testDeleteProductFromStore();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		testAddNewManager();
		testCloseStore();
		testOpenStore();
		testGetPurchaseHistory();
		testChangeStorePurchasePolicy();
	}
	private void testAddProductToStore() {
		Product product=new Product( "ball", 10, 7, "toys", null);
		assertFalse(BlMain.addProductToStore(sm, product, 5));
		sm.setSpecificPermission(BlMain.addProductToStore, true);
		assertTrue(BlMain.addProductToStore(sm, product, 5));
	}

	private void testUpdateProductDetails() {
		
		Product oldProduct=new Product("ball", 10, 7, "toys", null);
		Product newProduct=new Product("ball", 10, 7, "toys", null);
		newProduct.setPrice(8);
		assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
		sm.setSpecificPermission(BlMain.updateProductDetails, true);
		assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
	}
	
	private void testDeleteProductFromStore() {
		Product product=new Product("ball", 8, 7, "toys", null);
		assertFalse(BlMain.deleteProductFromStore(sm, product));
		sm.setSpecificPermission(BlMain.deleteProductFromStore, true);
		assertTrue(BlMain.deleteProductFromStore(sm, product));
	}
	
	private void testAddPolicyToProduct() {
		PurchasePolicy policy=new PurchasePolicy(new ImmediatelyPurchase());
		Product product=new Product("ball", 8, 7, "toys", null);
		BlMain.addProductToStore(sm, product, 5);
		assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
		sm.setSpecificPermission(BlMain.addPolicyToProduct, true);
		assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
	}

	private void testAddDiscountToProduct() {
		Product product=new Product("ball", 8, 7, "toys", null);
		DiscountPolicy discount=new OvertDiscount(null, 30);
		assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
		sm.setSpecificPermission(BlMain.addDiscountToProduct, true);
		assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
	}

	private void testAddNewManager() {
		StoreManager nsm=new StoreManager(sm.getStore());
		assertFalse(BlMain.addNewManager(sm, nsm));
		sm.setSpecificPermission(BlMain.addNewManager, true);
		assertTrue(BlMain.addNewManager(sm, nsm));
	}

	private void testCloseStore() {
		assertFalse(BlMain.closeStore(sm));
		sm.setSpecificPermission(BlMain.closeStore, true);
		assertTrue(BlMain.closeStore(sm));
	}

	private void testOpenStore() {
		assertFalse(BlMain.openStore(sm));
		sm.setSpecificPermission(BlMain.openStore, true);
		assertTrue(BlMain.openStore(sm));
	}

	private void testGetPurchaseHistory() {
		assertTrue(BlMain.getPurchaseHistory(sm).isEmpty());
	}
	
	private void testChangeStorePurchasePolicy()
	{
		assertFalse(BlMain.changeStorePurchasePolicy(sm, new PurchasePolicy(new ImmediatelyPurchase())));
		sm.setSpecificPermission(BlMain.changeStorePurchasePolicy, true);
		assertTrue(BlMain.changeStorePurchasePolicy(sm, new PurchasePolicy(new ImmediatelyPurchase())));
	}

}
