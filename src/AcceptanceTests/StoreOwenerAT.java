package AcceptanceTests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class StoreOwenerAT {

	private Guest g;
	private Subscriber sub;
	private StoreOwner so;
	private Product prod1;
	private Product prod2;
	private Product prod3;
	private Product prod4;
	
	@Before
	public void beforeTests(){
		g = new Guest();
		sub = BlMain.signUp(g, "globUse", "globPass", "usr", "name", 132456, "123456");
		Store s1 = BlMain.openStore(sub, 123456, "Tel Aviv", 123654, 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		List<StoreOwner> own1 = sub.getOwner();
		so = own1.get(0);
		
		prod1 = new Product("111", "prod1", 200, 4, "test cat 1", 
				new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
		prod2 = new Product("222", "prod2", 200, 4, "test cat 2", 
				new PurchasePolicy(new ImmediatelyPurchase()));
		prod3 = new Product("333", "prod3", 100, 4, "test cat 3", 
				new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
		prod4 = new Product("444", "prod4", 200, 4, "test cat 4", 
				new PurchasePolicy(new ImmediatelyPurchase()));
	}
	
	//3.1.3
	@Test
	public void testAddProduct(){
		//test incorret input
		assertFalse(BlMain.addProductToStore(so, prod2, -1));
		assertFalse(BlMain.addProductToStore(so, prod2, 0));
		assertFalse(BlMain.addProductToStore(so, null, 1));
		StoreOwner so2 = null;
		assertFalse(BlMain.addProductToStore(so2, prod2, 1));
		
		//good case
		assertTrue(BlMain.addProductToStore(so, prod2, 1));
		//add the same product with diff amount
		assertTrue(BlMain.addProductToStore(so, prod2, 2));
		Store s = so.getStore();
		assertEquals(3, s.getProducts().get(prod2).intValue());
		
	}
	//3.1.2
	@Test
	public void testRemoveProduct(){
		BlMain.addProductToStore(so, prod4, 10);
		//test incorret input
		assertFalse(BlMain.deleteProductFromStore(so, null));
		StoreOwner so2 = null;
		assertFalse(BlMain.deleteProductFromStore(so2, prod4));
		
		//good case
		assertTrue(BlMain.deleteProductFromStore(so, prod4));
		Store s = so.getStore();
		assertFalse(s.getProducts().keySet().contains(prod4));
		
		//not exist product
		Product notExits = new Product("404", "notExits", 200, 4, "test cat 2", 
				new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.deleteProductFromStore(so, notExits));
		
	}
	
	//3.1.1
	@Test
	public void testEditProduct(){
		BlMain.addProductToStore(so, prod1, 10);
		Product newProduct = new Product("111", "newProduct", 200, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		
		//incorrect Input
		assertFalse(BlMain.updateProductDetails(so, prod1, newProduct, -1));
		assertFalse(BlMain.updateProductDetails(so, prod1, newProduct, 0));
		assertFalse(BlMain.updateProductDetails(so, prod1, null, 1));
		assertFalse(BlMain.updateProductDetails(so, null, newProduct, 1));
		StoreOwner so2 = null;
		assertFalse(BlMain.updateProductDetails(so2, prod1, newProduct, 1));
		
		//not Exist
		Product notExits = new Product("404", "notExits", 200, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.updateProductDetails(so, prod1, notExits, 1));
		
		//good case
		assertTrue(BlMain.updateProductDetails(so, prod1, newProduct, 1));
		Store s = so.getStore();
		assertFalse(s.getProducts().keySet().contains(prod1));
		assertTrue(s.getProducts().keySet().contains(newProduct));
	}
	
	//3.2.1
	@Test
	public void testDefinePurchasePolicy()
	

}
