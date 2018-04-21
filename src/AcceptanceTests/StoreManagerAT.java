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
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class StoreManagerAT {

	private Guest g;
	private Subscriber sub;
	private StoreOwner so;
	private StoreManager sm;
	private Product prod1;
	private Product prod2;
	private Product prod3;
	private Product prod4;
	private boolean[] arr;
	@Before
	public void beforeTests(){
		g = new Guest();
		sub = BlMain.signUp(g, "StoreManagerAT", "globPass", "usr", "name", "132412356", "1234567891011");
		Store s1 = BlMain.openStore(sub,  5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
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
		
		BlMain.addProductToStore(so, prod1, 10);
		BlMain.addProductToStore(so, prod2, 10);
		BlMain.addProductToStore(so, prod3, 10);
		BlMain.addProductToStore(so, prod4, 10);
		
		arr=new boolean[12];
		for (int i=0;i<arr.length;i++)
			arr[i]=false;
		
		sm = new StoreManager(arr, so.getStore());
		BlMain.addNewManager(so, sm);
		
	}
	
	//4.1
	
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
