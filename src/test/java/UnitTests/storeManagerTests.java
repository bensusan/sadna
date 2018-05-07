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
		s=new Store("newStore", "4444", "0548787878", 1, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
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
		try {
			assertFalse(BlMain.addProductToStore(sm, product, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.addProductToStore, true);
		try {
			assertTrue(BlMain.addProductToStore(sm, product, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testUpdateProductDetails() {
		
		Product oldProduct=new Product("ball", 10, 7, "toys", null);
		Product newProduct=new Product("ball", 10, 7, "toys", null);
		try {
			BlMain.addProductToStore(sm, oldProduct, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		newProduct.setPrice(8);
		try {
			assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.updateProductDetails, true);
		try {
			assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testDeleteProductFromStore() {
		Product product=new Product("ball", 8, 7, "toys", null);
		try {
			BlMain.addProductToStore(sm, product, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assertFalse(BlMain.deleteProductFromStore(sm, product));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.deleteProductFromStore, true);
		try {
			assertTrue(BlMain.deleteProductFromStore(sm, product));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testAddPolicyToProduct() {
		PurchasePolicy policy = null;
		try {
			policy = new PurchasePolicy(new ImmediatelyPurchase());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Product product=new Product("ball", 8, 7, "toys", null);
		try {
			BlMain.addProductToStore(sm, product, 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.addPolicyToProduct, true);
		try {
			assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testAddDiscountToProduct() {
		Product product =null;
		try {
			product = new Product("ball", 8, 7, "toys",new PurchasePolicy(new ImmediatelyPurchase()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			BlMain.addProductToStore(sm, product, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DiscountPolicy discount  = null;
		try {
			discount = new OvertDiscount(null, 30);
		} catch (Exception e1) {
		}
		try {
			assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.addDiscountToProduct, true);
		try {
			assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testAddNewManager() {
		Subscriber newSub = null;
		try {
			newSub = BlMain.signUp(new Guest(), "newSub", "newPass", "newName", "newAdd", "0547878987", "7876543212345678");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assertFalse(BlMain.addNewManager(sm, newSub));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.addNewManager, true);
		try {
			assertTrue(BlMain.addNewManager(sm, newSub));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.removeSubscriber(new SystemAdministrator(null, null, null, null, null, null, null, null, null), newSub);
		} catch (Exception e) {
		}
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
		try {
			assertFalse(BlMain.changeStorePurchasePolicy(sm, new PurchasePolicy(new ImmediatelyPurchase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sm.setSpecificPermission(BlMain.changeStorePurchasePolicy, true);
		try {
			assertTrue(BlMain.changeStorePurchasePolicy(sm, new PurchasePolicy(new ImmediatelyPurchase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
