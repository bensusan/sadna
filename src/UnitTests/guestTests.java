package unitTests;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class guestTests {

	
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
		Product p=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addImmediatelyProduct(null, p, 1));
		assertFalse(BlMain.addImmediatelyProduct(g, null, 1));
		assertFalse(BlMain.addImmediatelyProduct(g, p, -1));
		assertFalse(BlMain.addImmediatelyProduct(g, p, 0));
		assertTrue(BlMain.addImmediatelyProduct(g, p, 1));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(p))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),5);
			}
		}
	}
	private void testAddImmediatelyProductWithCode() {
		Guest g=new Guest();
		Product p=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase(new HiddenDiscount(1212, new Date(12,12,2018), 20))));
		assertFalse(BlMain.addImmediatelyProduct(null, p, 1,1212));
		assertFalse(BlMain.addImmediatelyProduct(g, null, 1,1212));
		assertFalse(BlMain.addImmediatelyProduct(g, p, -1,1212));
		assertFalse(BlMain.addImmediatelyProduct(g, p, 0,1212));
		
		assertTrue(BlMain.addImmediatelyProduct(g, p, 1,1212));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(p))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),4);
			}
		}
		p=new Product("132", "balls", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase(new HiddenDiscount(1212, new Date(12,12,2018), 20))));
		assertTrue(BlMain.addImmediatelyProduct(g, p, 1,9999));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(p))
			{
				assertEquals(p2.getAmount(),1);
				assertEquals(p2.getPrice(),5);
			}
		}
	}
	private void testAddLotteryProduct() {
		Guest g=new Guest();
		Product p=new Product("132", "balls", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase(new HiddenDiscount(1212, new Date(12,12,2018), 20))));
		assertFalse(BlMain.addLotteryProduct(g, p, 1));//not lottery
		p=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new LotteryPurchase(new Date(12,12,2018))));
		assertFalse(BlMain.addLotteryProduct(null, p, 1));
		assertFalse(BlMain.addLotteryProduct(g, null, 1));
		assertFalse(BlMain.addLotteryProduct(g, p, -1));
		assertFalse(BlMain.addLotteryProduct(g, p, 0));
		assertTrue(BlMain.addLotteryProduct(g, p, 1));
		assertEquals(((LotteryPurchase)p.getPurchasePolicy().getPurchaseType()).getParticipants().get(g).intValue(),1);
	}
	
	
	private void testRemoveProductFromCart() {
		Guest g=new Guest();
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.removeProductFromCart(g, null));
		assertFalse(BlMain.removeProductFromCart(null, p));
		assertFalse(BlMain.removeProductFromCart(g, p));
		BlMain.addImmediatelyProduct(g, p, 1);
		assertTrue(BlMain.removeProductFromCart(g, p));
		boolean containP=false;
		for(ProductInCart p2 :g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(p)){
				containP=true;
			}
		}
		assertFalse(containP);
		
	}
	
	private void editProductAmount(){
		Guest g=new Guest();
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addImmediatelyProduct(g, p, 1);

		assertFalse(BlMain.editProductAmount(null, p, 2));
		assertFalse(BlMain.editProductAmount(g, null, 2));
		assertFalse(BlMain.editProductAmount(g, p, -1));
		assertFalse(BlMain.editProductAmount(g, p, 0));
		assertFalse(BlMain.editProductAmount(g, new Product("9999", "banana", 6, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase())), 2));
		assertFalse(BlMain.editProductAmount(g, p, 2));
		for(ProductInCart p2:g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(p)){
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
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addImmediatelyProduct(g, p, 1);
		assertTrue(BlMain.editCart(g, new Cart()));
		boolean containP=false;
		for(ProductInCart p2 :g.getCart().getProducts())
		{
			if(p2.getMyProduct().equals(p)){
				containP=true;
			}
		}
		assertTrue(containP);
	}
	private void testPurchaseCart() {
		Guest g=new Guest();
		
		assertFalse(BlMain.purchaseCart(g, "1234567890123456", "herzel 23 tel aviv"));//empty cart
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addImmediatelyProduct(g, p, 1);
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
		
		BlMain.addImmediatelyProduct(g, new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase())), 1);
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
