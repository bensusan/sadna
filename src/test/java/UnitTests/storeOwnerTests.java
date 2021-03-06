package UnitTests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class storeOwnerTests {

	private static StoreOwner so;
	private static Store s;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		
		s=new Store("new Store", "hadekel 72 Ranana", "1542365985", 3, new HashMap<Product,Integer>(),new LinkedList<Purchase> (), true);
		so=new StoreOwner(s);
	}
	
	@Test
	public void mainTest()
	{
		testAddProductToStore();
		testUpdateProductDetails();
		testAddPolicyToProduct();
		testAddDiscountToProduct();
		testChangeStorePurchasePolicy();
		testAddDiscountToCategoryStore();
		testDeleteProductFromStore();
		testAddNewStoreOwner();
		testAddNewManager();
		testCloseStore();
		testOpenStore();
		testGetPurchaseHistory();
		testChangeStorePurchasePolicy();
	}
	private void testAddProductToStore() {
		Product product = null;
		try {
			product = new Product( "ball", 5, 5, new EmptyPolicy(), new ImmediatelyPurchase());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			BlMain.addProductToStore(so, product, -1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"amount must be greater than 0");
		}
		try {
			BlMain.addProductToStore(so, null, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		StoreOwner so2=null;
		try {
			BlMain.addProductToStore(so2, product, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		Store store=so.getStore();
		so.setStore(null);
		try {
			BlMain.addProductToStore(so, product, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		so.setStore(store);
		try {
			assertTrue(BlMain.addProductToStore(so, product, 1,"toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	private void testUpdateProductDetails() {
		Product oldProduct=(Product) so.getStore().getProducts().keySet().toArray()[0];
		
		Product newProduct=new Product(oldProduct);
		newProduct.setName("new name");
		try {
			BlMain.updateProductDetails(so, oldProduct, newProduct, -1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"amount must be greater than 0");
		}
		try {
			BlMain.updateProductDetails(so, oldProduct, null, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");		
		}
		
		try {
			BlMain.updateProductDetails(so, null, newProduct, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		StoreOwner so2=null;
		try {
			assertFalse(BlMain.updateProductDetails(so2, oldProduct, newProduct, 1,"toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		Product new2=new Product("temp", 3, 3, new EmptyPolicy(), null);
		try {
			BlMain.updateProductDetails(so, new2, oldProduct, 1,"toys");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"this product doesn't belongs to this store");
		}//product does not exist
		
		try {
			assertTrue(BlMain.updateProductDetails(so, oldProduct, newProduct, 1,"toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	private void testAddPolicyToProduct() {
		Product product = null;
		try {
			product = new Product("ping pong ball", 5, 5, new EmptyPolicy(), new ImmediatelyPurchase());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			BlMain.addPolicyToProduct(so, new MaxPolicy(null,3), product);
		} catch (Exception e1) {
			assertEquals(e1.getMessage(),"something went wrong");
		}
		try {
			BlMain.addProductToStore(so, product, 10,"toys");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.addPolicyToProduct(so, null, product);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		try {
			BlMain.addPolicyToProduct(so, new MaxPolicy(null,3), null);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		StoreOwner so2=null;
		try {
			BlMain.addPolicyToProduct(so2, new MaxPolicy(null,3), product);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		try {
			assertTrue(BlMain.addPolicyToProduct(so,new MaxPolicy(null,3), product));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testAddDiscountToProduct() {
		Product product=new Product("ping pong ball", 5, 5, new EmptyPolicy(), new LotteryPurchase(new java.sql.Date(0)));
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(2018,7,9),30)), product);
			fail();
		} catch (Exception e2) {
			assertEquals(e2.getMessage(),"something went wrong");
		}
		try {
			BlMain.addProductToStore(so, product, 10,"toys");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(2018,12,12), 30)), product);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"discount can be added only to products that are for immediate purchase");
		}
		try {
			BlMain.changeProductType(so, new ImmediatelyPurchase(), product);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), -10)), product);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage() , "Invalid Discount precentage.");
		}
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 130)), product);
			fail();
		} catch (Exception e1) {
			assertEquals(e1.getMessage() , "Invalid Discount precentage.");
		}
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 30)), null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(null, 30)), product);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"discount can be added only to products that are for immediate purchase");
			
		}
		try {
			BlMain.addDiscountToProduct(so, null, product);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		StoreOwner so2=null;
		try {
			assertFalse(BlMain.addDiscountToProduct(so2, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 30)), product));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertTrue(BlMain.addDiscountToProduct(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(0), 30)), product));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testChangeStorePurchasePolicy()
	{
		StoreOwner so2=null;
		try {
			assertFalse(BlMain.changeStorePurchasePolicy(so2, new MaxPolicy(null,10)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		Store tempStore=so.getStore();
		so.setStore(null);
		try {
			BlMain.changeStorePurchasePolicy(so, new MaxPolicy(null,10));
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		so.setStore(tempStore);
		try {
			BlMain.changeStorePurchasePolicy(so, null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			assertTrue(BlMain.changeStorePurchasePolicy(so, new MaxPolicy(null,10)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testAddDiscountToCategoryStore(){
		try {
			BlMain.addDiscountToCategoryStore(so, null, "toys");
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addDiscountToCategoryStore(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(2019,1,1), -10)), "toys");
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage() , "Invalid Discount precentage.");
		}
		try {
			BlMain.addDiscountToCategoryStore(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(2019,1,1), 130)), "toys");
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage() , "Invalid Discount precentage.");
		}
		StoreOwner so2=null;
		try {
			assertFalse(BlMain.addDiscountToCategoryStore(so2, new EmptyPolicy(new OvertDiscount(new java.sql.Date(2019,1,1), 30)), "toys"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assertTrue(BlMain.addDiscountToCategoryStore(so, new EmptyPolicy(new OvertDiscount(new java.sql.Date(2019,1,1),30)), "toys"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	private void testDeleteProductFromStore() {
		Product product2 = null;
		try {
			product2 = new Product("iphone x", 250, 6, new EmptyPolicy(), new ImmediatelyPurchase());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			BlMain.deleteProductFromStore(so, product2);
		} catch (Exception e1) {
			assertEquals(e1.getMessage() , "something went wrong");
		}
		try {
			BlMain.deleteProductFromStore(so, null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		StoreOwner so2=null;
		try {
			assertFalse(BlMain.deleteProductFromStore(so2, product2));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Store store=so.getStore();
		so.setStore(null);
		Product product = null;
		try {
			product = new Product("ping pong ball", 5, 5, new EmptyPolicy(), new ImmediatelyPurchase());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			BlMain.deleteProductFromStore(so, product);
		} catch (Exception e1) {
			assertEquals(e1.getMessage() , "something went wrong");
		}
		so.setStore(store);
		try {
			BlMain.addProductToStore(so, product, 10,"toys");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			assertTrue(BlMain.deleteProductFromStore(so, product));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.deleteProductFromStore(so, product);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
	}
	private void testAddNewStoreOwner() {
		Subscriber newSub = null;
		try {
			newSub = BlMain.signUp(new Guest(), "newSub", "newPass", "newName", "newAdd", "0547878987", "7876543212345678");
		} catch (Exception e2) {
			e2.printStackTrace();
			fail();
		}
		try {
			assertFalse(BlMain.addNewStoreOwner(so, null));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		StoreOwner so2=null;
		try {
			assertFalse(BlMain.addNewStoreOwner(so2, newSub));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			assertTrue(BlMain.addNewStoreOwner(so, newSub));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.removeSubscriber(new SystemAdministrator(null, null, null, null, null, null, null, null, null), newSub);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testAddNewManager() {
		Subscriber newSub = null;
		try {
			newSub = BlMain.signUp(new Guest(), "newSub", "newPass", "newName", "newAdd", "0547878987", "7876543212345678");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		try {
			BlMain.addNewManager(so, null);
		} catch (Exception e1) {
			assertEquals(e1.getMessage() , "something went wrong");
		}
		StoreOwner so2=null;
		try {
			BlMain.addNewManager(so2, newSub);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			assertTrue(BlMain.addNewManager(so, newSub));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			BlMain.removeSubscriber(new SystemAdministrator(null, null, null, null, null, null, null, null, null), newSub);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	private void testCloseStore() {
		StoreOwner so2=null;
		try {
			BlMain.closeStore(so2);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		Store tempStore=so.getStore();
		so.setStore(null);
		try {
			BlMain.closeStore(so);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		so.setStore(tempStore);
		try {
			assertTrue(BlMain.closeStore(so));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertFalse(so.getStore().getIsOpen());
		try {
			assertFalse(BlMain.closeStore(so));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}//the store is close
		
	}
	private void testOpenStore() {
		StoreOwner so2=null;
		try {
			BlMain.openStore(so2);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		Store tempStore=so.getStore();
		so.setStore(null);
		try {
			BlMain.openStore(so);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"something went wrong");
		}
		so.setStore(tempStore);
		try {
			assertTrue(BlMain.openStore(so));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(so.getStore().getIsOpen());
		try {
			assertFalse(BlMain.openStore(so));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}//the store is open
	}
	private void testGetPurchaseHistory() {
		assertTrue(BlMain.getPurchaseHistory(so).isEmpty());
	}
	

}
