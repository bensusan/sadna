package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

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
		s=new Store(BlMain.getStoreId(), "4444", "0548787878", 1, new HashMap<>(), new LinkedList<>(), true);
		sm=new StoreManager (s);
		sm.setPremisions(arr);
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
	}
	private void testAddProductToStore() {
		Product product=new Product("569", "ball", 10, 7, "toys", null);
		assertFalse(BlMain.addProductToStore(sm, product, 5));
		arr[BlMain.addProductToStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addProductToStore(sm, product, 5));
	}

	private void testUpdateProductDetails() {
		
		Product oldProduct=new Product("569", "ball", 10, 7, "toys", null);
		Product newProduct=new Product("569", "ball", 10, 7, "toys", null);
		newProduct.setPrice(8);
		assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
		arr[BlMain.updateProductDetails]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
	}
	
	private void testDeleteProductFromStore() {
		Product product=new Product("569", "ball", 8, 7, "toys", null);
		assertFalse(BlMain.deleteProductFromStore(sm, product));
		arr[BlMain.deleteProductFromStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.deleteProductFromStore(sm, product));
	}
	
	private void testAddPolicyToProduct() {
		PurchasePolicy policy=new PurchasePolicy(new ImmediatelyPurchase());
		Product product=new Product("569", "ball", 8, 7, "toys", null);
		BlMain.addProductToStore(sm, product, 5);
		assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
		arr[BlMain.addPolicyToProduct]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
	}

	private void testAddDiscountToProduct() {
		Product product=new Product("569", "ball", 8, 7, "toys", null);
		DiscountPolicy discount=new OvertDiscount(null, 30);
		assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
		arr[BlMain.addDiscountToProduct]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
	}

	private void testAddNewManager() {
		StoreManager nsm=new StoreManager(sm.getStore());
		nsm.setPremisions(arr);
		assertFalse(BlMain.addNewManager(sm, nsm));
		arr[BlMain.addNewManager]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.addNewManager(sm, nsm));
	}

	private void testCloseStore() {
		assertFalse(BlMain.closeStore(sm));
		arr[BlMain.closeStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.closeStore(sm));
	}

	private void testOpenStore() {
		assertFalse(BlMain.openStore(sm));
		arr[BlMain.openStore]=true;
		sm.setPremisions(arr);
		assertTrue(BlMain.openStore(sm));
	}

	private void testGetPurchaseHistory() {
		assertTrue(BlMain.getPurchaseHistory(sm).isEmpty());
	}


}
