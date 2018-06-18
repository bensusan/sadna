package UnitTests;

import static org.junit.Assert.*;

import java.sql.Date;
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
		try {
			s=new Store("newStore", "4444", "0548787878", 1, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
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
		testAddDiscountToCategoryStore();
	}
	private void testAddProductToStore() {
		try{
			Product product=new Product( "ball", 10, 7, new EmptyPolicy(), null);
		
			try {
				assertFalse(BlMain.addProductToStore(sm, product, 5,"toys"));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			sm.setSpecificPermission(BlMain.addProductToStore, true);
			try {
				assertTrue(BlMain.addProductToStore(sm, product, 5,"toys"));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testUpdateProductDetails() {
		try{
			Product oldProduct=new Product("ball", 10, 7, new EmptyPolicy(), null);
			Product newProduct=new Product("ball", 10, 7, new EmptyPolicy(), null);
			try {
				BlMain.addProductToStore(sm, oldProduct, 10,"toys");
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			newProduct.setPrice(8);
			try {
				assertFalse(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5,"toys"));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			sm.setSpecificPermission(BlMain.updateProductDetails, true);
			try {
				assertTrue(BlMain.updateProductDetails(sm, oldProduct, newProduct, 5,"toys"));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private void testDeleteProductFromStore() {
		try{
			Product product=new Product("ball", 8, 7, new EmptyPolicy(), null);
			try {
				BlMain.addProductToStore(sm, product, 10,"toys");
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			try {
				assertFalse(BlMain.deleteProductFromStore(sm, product));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			sm.setSpecificPermission(BlMain.deleteProductFromStore, true);
			try {
				assertTrue(BlMain.deleteProductFromStore(sm, product));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private void testAddPolicyToProduct() {
		PurchasePolicy policy = null;
		try {
			policy = new MinPolicy(null,1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try{
			Product product=new Product("ball", 8, 7, new EmptyPolicy(), new ImmediatelyPurchase(new EmptyPolicy()));
			try {
				BlMain.addProductToStore(sm, product, 5,"toys");
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			try {
				assertFalse(BlMain.addPolicyToProduct(sm, policy, product));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			sm.setSpecificPermission(BlMain.addPolicyToProduct, true);
			try {
				assertTrue(BlMain.addPolicyToProduct(sm, policy, product));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testAddDiscountToProduct() {
		Product product =null;
		try {
			product = new Product("ball", 8, 7, new EmptyPolicy(),new ImmediatelyPurchase());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			BlMain.addProductToStore(sm, product, 10,"toys");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		PurchasePolicy discount  = null;
		try {
			discount = new EmptyPolicy(new OvertDiscount(null, 30));
		} catch (Exception e1) {
		}
		try {
			assertFalse(BlMain.addDiscountToProduct(sm, discount, product));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.addDiscountToProduct, true);
		try {
			assertTrue(BlMain.addDiscountToProduct(sm, discount, product));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testAddNewManager() {
		Subscriber newSub = null;
		try {
			newSub = BlMain.signUp(new Guest(), "newSub", "newPass", "newName", "newAdd", "0547878987", "7876543212345678");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertFalse(BlMain.addNewManager(sm, newSub));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.addNewManager, true);
		try {
			assertTrue(BlMain.addNewManager(sm, newSub));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.removeSubscriber(new SystemAdministrator(null, null, null, null, null, null, null, null, null), newSub);
		} catch (Exception e) {
		}
	}

	private void testCloseStore() {
		try {
			assertFalse(BlMain.closeStore(sm));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.closeStore, true);
		try {
			assertTrue(BlMain.closeStore(sm));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testOpenStore() {
		try {
			assertFalse(BlMain.openStore(sm));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.openStore, true);
		try {
			assertTrue(BlMain.openStore(sm));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void testGetPurchaseHistory() {
		try {
			assertTrue(BlMain.getPurchaseHistory(sm).isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private void testChangeStorePurchasePolicy()
	{
		try {
			assertFalse(BlMain.changeStorePurchasePolicy(sm, new MinPolicy(null,3)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.changeStorePurchasePolicy, true);
		try {
			assertTrue(BlMain.changeStorePurchasePolicy(sm, new MinPolicy(null,3)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testAddDiscountToCategoryStore(){
		try {
			assertFalse(BlMain.addDiscountToCategoryStore(sm, new EmptyPolicy(new OvertDiscount(new Date(2019,9,8), 30)), "toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sm.setSpecificPermission(BlMain.addDiscountToCategoryStore, true);
		try {
			assertTrue(BlMain.addDiscountToCategoryStore(sm, new EmptyPolicy(new OvertDiscount(new Date(2019,9,8), 30)), "toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
