package tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashMap;

import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class guestTests {

	
	@Test
	public void mainTest()
	{
		testAddProductToCart();
		testRemoveProductFromCart();
		testEditProductInCart();
		testEditCart();
		testPurchaseCart();
		testSignUp();
		testSignIn();
	}
	private void testAddProductToCart() {
		Guest g=new Guest();
		Product p=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.addProductToCart(null, p, 1));
		assertFalse(BlMain.addProductToCart(g, null, 1));
		assertFalse(BlMain.addProductToCart(g, p, -1));
		assertFalse(BlMain.addProductToCart(g, p, 0));
		assertTrue(BlMain.addProductToCart(g, p, 1));
		assertTrue(g.getCart().getProducts().containsKey(p));
		assertEquals(g.getCart().getProducts().get(p).intValue(),1);
	}
	private void testRemoveProductFromCart() {
		Guest g=new Guest();
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.removeProductFromCart(g, null));
		assertFalse(BlMain.removeProductFromCart(null, p));
		assertFalse(BlMain.removeProductFromCart(g, p));
		BlMain.addProductToCart(g, p, 1);
		assertTrue(BlMain.removeProductFromCart(g, p));
		assertFalse(g.getCart().getProducts().containsKey(p));
		
	}
	private void testEditProductInCart() {
		Guest g=new Guest();
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		assertFalse(BlMain.editProductInCart(null, p, 1));
		assertFalse(BlMain.editProductInCart(g, null, 1));
		assertFalse(BlMain.editProductInCart(g, p, 1));
		BlMain.addProductToCart(g, p, 1);
		assertFalse(BlMain.editProductInCart(g, p, -2));
		assertTrue(BlMain.editProductInCart(g, p, 3));
		assertFalse(BlMain.editProductInCart(g, p, 0));
		assertEquals(g.getCart().getProducts().get(p).intValue(),3);
	}
	private void testEditCart() {
		Guest g=null;
		assertFalse(BlMain.editCart(g, new HashMap<Product,Integer>()));
		g=new Guest();
		assertFalse(BlMain.editCart(g, null));
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addProductToCart(g, p, 1);
		assertTrue(BlMain.editCart(g, new HashMap<Product,Integer>()));
		assertTrue(g.getCart().getProducts().containsKey(p));
	}
	private void testPurchaseCart() {
		Guest g=new Guest();
		
		assertFalse(BlMain.purchaseCart(g, "1234567890123456", "herzel 23 tel aviv"));//empty cart
		Product p=new Product("995", "apple", 5, 3, "fruits", new PurchasePolicy(new ImmediatelyPurchase()));
		BlMain.addProductToCart(g, p, 1);
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
		
		BlMain.addProductToCart(g, new Product("995", "apple", 5, 3, "fruits", null), 1);
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
