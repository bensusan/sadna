package unitTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class guestTests {

	private static Guest ofir;
	private static Store ofirStore;
	private static StoreOwner ofirOwnership;
	private static Product ball;
	private static Product ballwithDiscount;
	private static Product ballwithLottery;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		ofirStore=BlMain.openStore((Subscriber) ofir,"ofir store",5, new HashMap<Product, Integer>(), new LinkedList<Purchase>(),true);
		ball=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
		ballwithDiscount=new Product("1234", "ball with Discount", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase(new HiddenDiscount(1212, new Date(12,12,2018), 20))));
		ballwithLottery=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new LotteryPurchase(new Date(12,12,2018))));
		BlMain.addProductToStore(ofirOwnership, ball, 10);
		BlMain.addProductToStore(ofirOwnership, ballwithDiscount, 10);
		BlMain.addProductToStore(ofirOwnership, ballwithLottery, 10);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		BlMain.removeSubscriber(amit, (Subscriber)ofir);
	}
	
	@Test
	public void mainTest()
	{
		testAddImmediatelyProduct();
		testAddImmediatelyProductWithCode();
		testAddLotteryProduct();
		testRemoveProductFromCart();
		editProductAmount();
		testEditCart();
		testPurchaseCart();
		testSignUp();
		testSignIn();
	}
	
	private void testAddImmediatelyProduct() {
		Guest g=new Guest();
		assertFalse(BlMain.addImmediatelyProduct(null, ball, 1));
		assertFalse(BlMain.addImmediatelyProduct(g, null, 1));
		assertFalse(BlMain.addImmediatelyProduct(g, ball, -1));
		assertFalse(BlMain.addImmediatelyProduct(g, ball, 0));
		assertTrue(BlMain.addImmediatelyProduct(g, ball, 1));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ball))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),5);
			}
		}
	}
	private void testAddImmediatelyProductWithCode() {
		Guest g=new Guest();
		assertFalse(BlMain.addImmediatelyProduct(null, ballwithDiscount, 1,1212));
		assertFalse(BlMain.addImmediatelyProduct(g, null, 1,1212));
		assertFalse(BlMain.addImmediatelyProduct(g, ballwithDiscount, -1,1212));
		assertFalse(BlMain.addImmediatelyProduct(g, ballwithDiscount, 0,1212));
		
		assertTrue(BlMain.addImmediatelyProduct(g, ballwithDiscount, 1,1212));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ballwithDiscount))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),4);
			}
		}
		BlMain.removeProductFromCart(g, ballwithDiscount);
		
		assertTrue(BlMain.addImmediatelyProduct(g, ballwithDiscount, 1,9999));//worng code
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ballwithDiscount))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),5);
			}
		}
	}
	private void testAddLotteryProduct() {
		Guest g=new Guest();
		assertFalse(BlMain.addLotteryProduct(g, ballwithDiscount, 1));//not lottery
		assertFalse(BlMain.addLotteryProduct(null, ballwithLottery, 1));
		assertFalse(BlMain.addLotteryProduct(g, null, 1));
		assertFalse(BlMain.addLotteryProduct(g, ballwithLottery, -1));
		assertFalse(BlMain.addLotteryProduct(g, ballwithLottery, 0));
		assertTrue(BlMain.addLotteryProduct(g, ballwithLottery, 1));
		assertEquals(((LotteryPurchase)ballwithLottery.getPurchasePolicy().getPurchaseType()).getParticipants().get(g).intValue(),1);
	}
	
	
	private void testRemoveProductFromCart() {
		Guest g=new Guest();
		assertFalse(BlMain.removeProductFromCart(g, ball));//product not in cart
		BlMain.addImmediatelyProduct(g, ball, 1);
		assertFalse(BlMain.removeProductFromCart(g, null));
		assertFalse(BlMain.removeProductFromCart(null, ball));
		assertTrue(BlMain.removeProductFromCart(g, ball));
		boolean containP=false;
		for(ProductInCart p2 :g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ball)){
				containP=true;
			}
		}
		assertFalse(containP);
		
	}
	
	private void editProductAmount(){
		Guest g=new Guest();
		BlMain.addImmediatelyProduct(g, ball, 1);

		assertFalse(BlMain.editProductAmount(null, ball, 2));
		assertFalse(BlMain.editProductAmount(g, null, 2));
		assertFalse(BlMain.editProductAmount(g, ball, -1));
		assertFalse(BlMain.editProductAmount(g, ball, 0));
		assertFalse(BlMain.editProductAmount(g, new Product("9999", "banana", 6, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase())), 2));//product not in cart
		assertFalse(BlMain.editProductAmount(g, ball, 2));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ball)){
				assertEquals(p2.getAmount(),2);
				assertEquals(p2.getPrice(),10);
			}
		}
	}
	
	private void testEditCart() {
		Guest g=null;
		assertFalse(BlMain.editCart(g, new Cart()));
		g=new Guest();
		assertFalse(BlMain.editCart(g, null));
		
		BlMain.addImmediatelyProduct(g, ball, 1);
		assertTrue(BlMain.editCart(g, new Cart()));
		boolean containP=false;
		for(ProductInCart p2 :g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ball)){
				containP=true;
			}
		}
		assertTrue(containP);
	}
	private void testPurchaseCart() {
		Guest g=new Guest();
		
		assertFalse(BlMain.purchaseCart(g, "1234567890123456", "herzel 23 tel aviv"));//empty cart
		BlMain.addImmediatelyProduct(g, ball, 1);
		assertFalse(BlMain.purchaseCart(g, "1", "herzel 23 tel aviv"));
		assertFalse(BlMain.purchaseCart(g, null, "herzel 23 tel aviv"));
		assertFalse(BlMain.purchaseCart(g, "1234567890123456", null));
		assertFalse(BlMain.purchaseCart(null, "1234567890123456", "herzel 23 tel aviv"));
		assertTrue(BlMain.purchaseCart(g, "1234567890123456", "herzel 23 tel aviv"));
	}
	
	private void testSignUp() {
		Guest g=new Guest();
		assertNull(BlMain.signUp(null, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "**ghju%", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, null, "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "(T#ghn526?.<~", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", null, "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded 55menashe89", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", null, "herzel 23 herzelia", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "&&JKGjhg64?.}", "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", null, "0541234567", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "-5", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "1", "1234567890123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "123456789o123456"));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", null));

		Subscriber s=BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
		assertEquals(g.getCart(),s.getCart());
		assertEquals(s.getAddress(),"herzel 23 herzelia");
		assertEquals(s.getCreditCardNumber(),"1234567890123456");
		assertEquals(s.getFullName(),"oded menashe");
		assertTrue(s.getManager().isEmpty());
		assertTrue(s.getOwner().isEmpty());
		assertEquals(s.getPassword(),"123");
		assertEquals(s.getPhone(),"0541234567");
		assertTrue(s.getPurchaseHistory().isEmpty());
		assertEquals(s.getUsername(),"abc");
		//exsist user
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456"));
		
	}
	private void testSignIn() {
		Guest g=new Guest();
		assertNull(BlMain.signIn(g, null, "123"));
		assertNull(BlMain.signIn(g, "abc", null));
		assertNull(BlMain.signIn(g, "%^Gsd{", "123"));
		assertNull(BlMain.signIn(g, "abc", "%Ffbf@{/"));
		assertNull(BlMain.signIn(g, "abc", "123"));//user does not exist
		
		BlMain.addImmediatelyProduct(g, ball, 1);
		Subscriber s1=BlMain.signIn(g, "abc", "123");
		
		assertEquals(s1.getUsername(),"abc");
		assertEquals(s1.getPhone(),"0541234567");
		assertEquals(s1.getPassword(),"123");
		assertEquals(s1.getFullName(),"oded menashe");
		assertEquals(s1.getCreditCardNumber(),"1234567890123456");
		assertEquals(s1.getAddress(),"herzel 23 herzelia");
		assertNotEquals(g.getCart(),s1.getCart());
		
		
	}
	


	



}
