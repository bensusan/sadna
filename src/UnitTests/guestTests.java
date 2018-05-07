package UnitTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
		ofirOwnership=((Subscriber)ofir).getOwner().get(0);
		ball=new Product("ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
		ballwithDiscount=new Product("ball with Discount", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase(new HiddenDiscount(1212, new Date(12,12,2018), 20))));
		ballwithLottery=new Product("ball", 5, 3, "toyes", new PurchasePolicy(new LotteryPurchase(new Date(12,12,2018))));
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
		try {
			BlMain.addImmediatelyProduct(null, ball, 1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addImmediatelyProduct(g, null, 1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addImmediatelyProduct(g, ball, -1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "amount must be greater than 1");
		}
		try {
			BlMain.addImmediatelyProduct(g, ball, 0);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "amount must be greater than 1");
		}
		
		try {
			assertTrue(BlMain.addImmediatelyProduct(g, ball, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		try {
			BlMain.addImmediatelyProduct(null, ballwithDiscount, 1,1212);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addImmediatelyProduct(g, null, 1,1212);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addImmediatelyProduct(g, ballwithDiscount, -1,1212);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "amount must be greater than 1");
		}
		try {
			BlMain.addImmediatelyProduct(g, ballwithDiscount, 0,1212);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "amount must be greater than 1");
		}
		try {
			BlMain.addImmediatelyProduct(g, ballwithDiscount, 1,1212);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "the coupon has expired");
		}
		
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ballwithDiscount))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),4);
			}
		}
		
		try{
			BlMain.removeProductFromCart(g, ballwithDiscount);
		} catch(Exception e){
			assertEquals(e.getMessage(), "the product isn't belongs to the cart");
		}
		try{
			BlMain.addImmediatelyProduct(g, ballwithDiscount, 1,9999);//worng code
		} catch(Exception e){
			assertEquals(e.getMessage(), "wrong coupon code");
		}
		
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
		try {
			BlMain.addLotteryProduct(g, ballwithDiscount, 1);//not lottery
		} catch (Exception e) {
			assertEquals(e.getMessage() , "the product isn't belongs to any lottery");
		}
		try {
			BlMain.addLotteryProduct(null, ballwithLottery, 1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addLotteryProduct(g, null, 1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.addLotteryProduct(g, ballwithLottery, -1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "must be a positive number");
		}
		try {
			BlMain.addLotteryProduct(g, ballwithLottery, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.addLotteryProduct(g, ballwithLottery, 1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "the product alreadt exists in cart");
		}
	}
	
	
	private void testRemoveProductFromCart() {
		Guest g=new Guest();
		try {
			BlMain.removeProductFromCart(g, ball);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "the product isn't belongs to the cart");
		}//product not in cart
		try {
			BlMain.addImmediatelyProduct(g, ball, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.removeProductFromCart(g, null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.removeProductFromCart(null, ball);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			assertTrue(BlMain.removeProductFromCart(g, ball));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		try {
			BlMain.addImmediatelyProduct(g, ball, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.editProductAmount(null, ball, 2);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.editProductAmount(g, null, 2);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.editProductAmount(g, ball, -1);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "amout must be greater than 1");
		}
		try {
			BlMain.editProductAmount(g, ball, 0);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "amout must be greater than 1");
		}
		try {
			BlMain.editProductAmount(g, new Product("banana", 6, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase())), 2);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "the product isn't belongs to the cart");
		}//product not in cart
		try {
			BlMain.editProductAmount(g, ball, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(ball)){
				assertEquals(p2.getAmount(),2);
				assertEquals(p2.getPrice(),5);
			}
		}
	}
	
	private void testEditCart() {
		Guest g=null;
		try {
			assertFalse(BlMain.editCart(g, new Cart())); //guest is null
		} catch (Exception e) {
			e.printStackTrace();}
		
		g=new Guest();
		try {
			BlMain.editCart(g, null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		
		try {
			BlMain.addImmediatelyProduct(g, ball, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assertTrue(BlMain.editCart(g, new Cart()));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		try {
			BlMain.purchaseCart(g, "1234567890123456", "herzel 23 tel aviv");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "the cart is empty");
		}//empty cart
		try {
			assertTrue(BlMain.addImmediatelyProduct(g, ball, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.purchaseCart(g, "1", "herzel 23 tel aviv");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "ilegal credit card");
		}
		try {
			BlMain.purchaseCart(g, null, "herzel 23 tel aviv");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "ilegal credit card");
		}
		try {
			BlMain.purchaseCart(g, "1234567890123456", null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "ilegal address");
		}
		try {
			BlMain.purchaseCart(null, "1234567890123456", "herzel 23 tel aviv");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			assertTrue(BlMain.purchaseCart(g, "1234567890123456", "herzel 23 tel aviv"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testSignUp() {
		Guest g=new Guest();
		try{
			BlMain.signUp(null, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, "**ghju%", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, null, "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", "(T#ghn526?.<~", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", null, "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", "123", "oded 55menashe89", "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", "123", null, "herzel 23 herzelia", "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", "123", "oded menashe", "&&JKGjhg64?.}", "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", "123", "oded menashe", null, "0541234567", "1234567890123456");
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "-5", "1234567890123456");
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "1", "1234567890123456");
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1");
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "123456789o123456");
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", null);
			fail();
		}
		catch (Exception e) {}
		Subscriber s = null;
		try {
			s = BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		assertEquals(g.getCart(),s.getCart());
		assertEquals(s.getAddress(),"herzel 23 herzelia");
		assertEquals(s.getCreditCardNumber(),"1234567890123456");
		assertEquals(s.getFullName(),"oded menashe");
		assertTrue(s.getManager().isEmpty());
		assertTrue(s.getOwner().isEmpty());
		//assertEquals(s.getPassword(),"123"); //we no longer save the password in the system
		assertEquals(s.getPhone(),"0541234567");
		assertTrue(s.getPurchaseHistory().isEmpty());
		assertEquals(s.getUsername(),"abc");
		//exsist user
		try{
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
			fail();
		}catch (Exception e) {}
	}
	private void testSignIn() {
		Guest g=new Guest();
		List<Subscriber> temp = BlMain.allSubscribers;
		BlMain.allSubscribers = new ArrayList<Subscriber>();
		
		try{
			BlMain.signIn(g, null, "123");
			BlMain.signIn(g, "abc", null);
			BlMain.signIn(g, "%^Gsd{", "123");
			BlMain.signIn(g, "abc", "%Ffbf@{/");
			BlMain.signIn(g, "abc", "123");//user does not exist
			fail();
		}catch (Exception e) {}
		
		try {
			BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", "0541234567", "1234567890123456");
		} catch (Exception e1) {
		}
		
		try {
			BlMain.addImmediatelyProduct(g, ball, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Subscriber s1 = null;
		try {
			s1 = BlMain.signIn(g, "abc", "123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(s1.getUsername(),"abc");
		assertEquals(s1.getPhone(),"0541234567");
		//assertEquals(s1.getPassword(),"123"); //we no longer save the password
		assertEquals(s1.getFullName(),"oded menashe");
		assertEquals(s1.getCreditCardNumber(),"1234567890123456");
		assertEquals(s1.getAddress(),"herzel 23 herzelia");
		//assertNotEquals(g.getCart(),s1.getCart()); //broken test
		
		BlMain.allSubscribers = temp;
		
	}
}