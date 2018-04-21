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

public class GuestAT {
	
	private Guest g;
	private Subscriber sub;
	private StoreOwner so;
	private Product immediateOvertProduct;
	private Product immediateNoDiscountProduct;
	private Product lotteryProduct;
	private Product zeroAmountProduct;
	
	@Before
	public void bef(){
		g = new Guest();
		sub = BlMain.signUp(g, "globUse", "globPass", "usr", "name", "132412356", "123456");
		Store s1 = BlMain.openStore(sub, 123456, "Tel Aviv", 123654, 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		List<StoreOwner> own1 = sub.getOwner();
		so = own1.get(0);
		
		immediateOvertProduct = new Product("111", "prod1", 200, 4, "test cat 1", 
				new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
		immediateNoDiscountProduct = new Product("222", "prod2", 200, 4, "test cat 2", 
				new PurchasePolicy(new ImmediatelyPurchase()));
		lotteryProduct = new Product("333", "prod3", 100, 4, "test cat 3", 
				new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
		zeroAmountProduct = new Product("444", "prod4", 200, 4, "test cat 4", 
				new PurchasePolicy(new ImmediatelyPurchase()));
		
		BlMain.addProductToStore(so, immediateOvertProduct, 10);
		BlMain.addProductToStore(so, immediateNoDiscountProduct, 10);
		BlMain.addProductToStore(so, lotteryProduct, 10);
		BlMain.addProductToStore(so, zeroAmountProduct, 0);
	}
	//1.1
	//could not test specificlly entry to system there is no entry to system yet
	
	//1.2
	@Test
	public void testGuestToSubscriber(){
		Guest g = new Guest();
		//good case - add new sub
		Subscriber s1 = BlMain.signUp(g, "sagivmap", "123123", "Sagiv Map", "Jerusalem", "132412356", "123456");
		assertNotNull(s1);
		assertEquals(g.getCart(), s1.getCart());
		assertEquals("sagivmap", s1.getUsername());
		assertEquals("123123", s1.getPassword());
		assertEquals("Sagiv Map", s1.getFullName());
		assertEquals("Jerusalem", s1.getAddress());
		assertEquals("Sagiv Map", s1.getFullName());
		assertEquals(88123132, s1.getPhone());
		assertEquals("123456", s1.getCreditCardNumber());
		
		//sad case - existing username
		Subscriber s2 = BlMain.signUp(g, "sagivmap", "45678999", "Sagiv Mag", "Be'er Sheva", "132412356", "654321");
		assertNull(s2);
		
		//bad case - corrupt input
		Subscriber s3 = BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", "789456");
		assertNull(BlMain.signUp(null, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", "789456"));
		assertNull(BlMain.signUp(g, null, "789789", "Adva San", "Be'er Sheva", "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", null, "Adva San", "Be'er Sheva", "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "", "Adva San", "Be'er Sheva", "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", null, "Be'er Sheva", "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", "", "Be'er Sheva", "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", "Adva San", "", "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", "Adva San", null, "132412356", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "dsa541a", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "", "789456"));
		assertNull(BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", null));
		assertNull(BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", ""));
	}
	
	//1.3
	@Test
	public void testGetInfoOnStoreAndProduct(){

		//sad case - no stores in the system
		Map<Store, Map<Product, Integer>> info = BlMain.getAllStoresWithThierProductsAndAmounts();
		assertTrue(info.size() == 0);
		
		Guest g1 = new Guest();
		Subscriber sub1 = BlMain.signUp(g1, "usr1", "pass1", "usr", "name", "132412356", "123456");
		Store s1 = BlMain.openStore(sub1, 123456, "Tel Aviv", 123654, 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		List<StoreOwner> own1 = sub1.getOwner();
		StoreOwner so1 = own1.get(0);
		
		Product p1 = new Product("111", "prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		Product p2 = new Product("222", "prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		
		BlMain.addProductToStore(so1, p1, 5);
		BlMain.addProductToStore(so1, p2, 3);
		
		Guest g2 = new Guest();
		Subscriber sub2 = BlMain.signUp(g2, "usr1", "pass1", "usr", "name", "123456789", "123456");
		Store s2 = BlMain.openStore(sub2, 123456, "Tel Aviv", 123654, 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
		List<StoreOwner> own2 = sub1.getOwner();
		StoreOwner so2 = own2.get(0);
		
		Product p3 = new Product("111", "prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		Product p4 = new Product("222", "prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		Product p5 = new Product("333", "prod3", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		
		BlMain.addProductToStore(so2, p3, 5);
		BlMain.addProductToStore(so2, p4, 3);
		BlMain.addProductToStore(so2, p5, 3);
		
		info = BlMain.getAllStoresWithThierProductsAndAmounts();
		assertTrue(info.containsKey(s1));
		Map<Product, Integer> prodDict1 = info.get(s1);
		assertTrue(prodDict1.containsKey(p1));
		assertTrue(prodDict1.get(p1)== 5);
		assertTrue(prodDict1.containsKey(p2));
		assertTrue(prodDict1.get(p1)== 3);
		
		assertTrue(info.containsKey(s2));
		Map<Product, Integer> prodDict2 = info.get(s2);
		assertTrue(prodDict2.containsKey(p3));
		assertTrue(prodDict2.get(p1)== 5);
		assertTrue(prodDict2.containsKey(p4));
		assertTrue(prodDict2.get(p1)== 3);
		assertTrue(prodDict2.containsKey(p5));
		assertTrue(prodDict2.get(p1)== 3);
		
		
	}
	
	//1.5
	@Test
	public void testKeepProductsInCart(){
		Guest g = new Guest();
		Product p1 = new Product("111", "prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		Product p2 = new Product("222", "prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addProductToCart(g, p1, 1);
		BlMain.addProductToCart(g, p2, 3);
		
		Cart c = g.getCart();
		Map<Product, Integer> cartContent = c.getProducts();
		assertTrue(cartContent.containsKey(p1));
		assertTrue(cartContent.get(p1) == 1);
		assertTrue(cartContent.containsKey(p2));
		assertTrue(cartContent.get(p2) == 3);
	}
	
	//1.6.1
	@Test
	public void testRemoveFromCart(){
		Guest g = new Guest();
		Product p1 = new Product("111", "prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		Product p2 = new Product("222", "prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addProductToCart(g, p1, 1);
		BlMain.addProductToCart(g, p2, 3);
		
		//item not found
		Product p3 = new Product("333", "prod3", 100, 4, "test cat 3", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.removeProductFromCart(g, p3));
		//check cart not damaged
		assertTrue(g.getCart().getProducts().size() == 2);
		assertTrue(g.getCart().getProducts().containsKey(p1));
		assertTrue(g.getCart().getProducts().containsKey(p2));
		assertTrue(g.getCart().getProducts().get(p1) == 1);
		assertTrue(g.getCart().getProducts().get(p2) == 3);
		
		//item found
		assertTrue(BlMain.removeProductFromCart(g, p1));
		assertFalse(g.getCart().getProducts().containsKey(p1));
		assertTrue(g.getCart().getProducts().containsKey(p2));
	}
	
	//1.6.2
	@Test
	public void testEditCart(){
		Guest g = new Guest();
		Product p1 = new Product("111", "prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
		Product p2 = new Product("222", "prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addProductToCart(g, p1, 1);
		BlMain.addProductToCart(g, p2, 3);
		
		//edit amount of product
		//item not found
		Product p3 = new Product("333", "prod3", 100, 4, "test cat 3", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.editProductInCart(g, p3, 3));
		
		//item found
		assertTrue(BlMain.editProductInCart(g, p1, 4));
		assertTrue(g.getCart().getProducts().containsKey(p1));
		assertTrue(g.getCart().getProducts().get(p1) == 4);
		
		//edit the whole cart
		Map<Product, Integer> newCart = new HashMap<Product, Integer>();
		newCart.put(p1, 10);
		newCart.put(p2, 20);
		
		assertTrue(BlMain.editCart(g, newCart));
		assertTrue(g.getCart().getProducts().containsKey(p1));
		assertTrue(g.getCart().getProducts().containsKey(p2));
		assertTrue(g.getCart().getProducts().get(p1) == 10);
		assertTrue(g.getCart().getProducts().get(p2) == 20);
	}
	
	//1.7.1
	@Test
	public void testLotteryPurchase(){
		fail("need to implement");
	}
	
	//1.7.2
	@Test
	public void testSinglePurchase(){
		//notice the store in @Before function
		//purchase zero amount of product in store
		Guest g1 = new Guest();
		assertFalse(BlMain.pruchaseProduct(g1,this.zeroAmountProduct, 1, "123456789", "rehovot"));
		
		//purchase more then amount
		Guest g2 = new Guest();
		assertFalse(BlMain.pruchaseProduct(g2, this.immediateNoDiscountProduct, 11, "132456" , "rehovot"));
		
		//test success purchase no discount
		assertTrue(BlMain.pruchaseProduct(g2, this.immediateNoDiscountProduct, 3, "132456" , "rehovot"));
		
		//test success purchase no discount
		assertTrue(BlMain.pruchaseProduct(g2, this.immediateOvertProduct, 6, "132456" , "rehovot"));
		
		Map<Product, Integer> prods = so.getStore().getProducts();
		assertEquals(7, prods.get(this.immediateNoDiscountProduct).intValue());
		assertEquals(6, prods.get(this.immediateOvertProduct).intValue());
		
	}
		
	//1.7.3
	@Test
	public void testCartPurchase(){
		//purchase zero amount of product in store
		Guest g1 = new Guest();
		BlMain.addProductToCart(g1, this.zeroAmountProduct, 1);
		assertFalse(BlMain.puchaseCart(g1, "132456", "rehovot"));
		
		//purchase more then amount
		Guest g2 = new Guest();
		BlMain.addProductToCart(g2, this.immediateNoDiscountProduct, 11);
		assertFalse(BlMain.puchaseCart(g2, "132456", "rehovot"));
		
		//test success purchase no discount
		Guest g3 = new Guest();
		BlMain.addProductToCart(g3, this.immediateNoDiscountProduct, 4);
		assertFalse(BlMain.puchaseCart(g3, "132456", "rehovot"));
		
		//test success purchase no discount
		Guest g4 = new Guest();
		BlMain.addProductToCart(g4, this.immediateOvertProduct, 4);
		assertFalse(BlMain.puchaseCart(g4, "132456", "rehovot"));
		
	}
		
	
	
	

}
