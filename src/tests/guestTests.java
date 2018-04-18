package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class guestTests {

	@Test
	public void testAddProductToCart() {
		Guest g=new Guest();
		Product p=new Product(null, null, 0, 0, null, null);
		assertFalse(BlMain.addProductToCart(null, p, 1));
		assertFalse(BlMain.addProductToCart(g, null, 1));
		assertFalse(BlMain.addProductToCart(g, p, -1));
		assertFalse(BlMain.addProductToCart(g, p, 0));
	}

	@Test
	public void testRemoveProductFromCart() {
		Guest g=new Guest();
		Product p=new Product("995", "apple", 5, 3, "fruits", null);
		assertFalse(BlMain.removeProductFromCart(g, null));
		assertFalse(BlMain.removeProductFromCart(null, p));
		assertFalse(BlMain.removeProductFromCart(g, p));
		BlMain.addProductToCart(g, p, 1);
		assertTrue(BlMain.removeProductFromCart(g, p));
		
	}

	@Test
	public void testEditProductInCart() {
		Guest g=new Guest();
		Product p=new Product("995", "apple", 5, 3, "fruits", null);
		assertFalse(BlMain.editProductInCart(null, p, 1));
		assertFalse(BlMain.editProductInCart(g, null, 1));
		assertFalse(BlMain.editProductInCart(g, p, 1));
		BlMain.addProductToCart(g, p, 1);
		assertFalse(BlMain.editProductInCart(g, p, -2));
		assertTrue(BlMain.editProductInCart(g, p, 3));
		assertFalse(BlMain.editProductInCart(g, p, 0));
	}

	@Test
	public void testEditCart() {
		fail("Not yet implemented");
	}

	@Test
	public void testPuchaseCart() {
		fail("Not yet implemented");
	}

	@Test
	public void testPruchaseProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testSignUp() {
		Guest g=new Guest();
		//bad
		assertNull(BlMain.signUp(null, "abc", "123", "oded menashe", "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, "**ghju%", "123", "oded menashe", "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, null, "123", "oded menashe", "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", "(T#ghn526?.<~", "oded menashe", "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", null, "oded menashe", "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", "123", "oded 55menashe89", "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", "123", null, "herzel 23 herzelia", 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "&&JKGjhg64?.}", 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", null, 0541234567, 12));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", -5, 12));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", 054124567, 12));
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", 0541234567, -3));
		//good
		Subscriber s=BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", 0541234567, 12);
		assertEquals(g.getCart(),s.getCart());
		assertEquals(s.getAddress(),"herzel 23 herzelia");
		assertEquals(s.getCreditCardNumber(),12);
		assertEquals(s.getFullName(),"oded menashe");
		assertTrue(s.getManager().isEmpty());
		assertTrue(s.getOwner().isEmpty());
		assertEquals(s.getPassword(),"123");
		assertEquals(s.getPhone(),0541234567);
		assertTrue(s.getPurchaseHistory().isEmpty());
		assertEquals(s.getUsername(),"abc");
		//exsist user
		assertNull(BlMain.signUp(g, "abc", "123", "oded menashe", "herzel 23 herzelia", 0541234567, 12));
		
	}

	@Test
	public void testSignIn() {
		Guest g=new Guest();
		assertNull(BlMain.signIn(g, null, "123"));
		assertNull(BlMain.signIn(g, "abc", null));
		assertNull(BlMain.signIn(g, "%^Gsd{", "123"));
		assertNull(BlMain.signIn(g, "abc", "%Ffbf@{/"));
		assertNull(BlMain.signIn(g, "abc", "123"));//user does not exist
		
		BlMain.addProductToCart(g, new Product("995", "apple", 5, 3, "fruits", null), 1);
		Subscriber s1=BlMain.signIn(g, "abc", "123");
		
		assertEquals(s1.getUsername(),"abc");
		assertEquals(s1.getPhone(),0541234567);
		assertEquals(s1.getPassword(),"123");
		assertEquals(s1.getFullName(),"oded menashe");
		assertEquals(s1.getCreditCardNumber(),12);
		assertEquals(s1.getAddress(),"herzel 23 herzelia");
		assertNull(BlMain.signIn(g, "abc", "123"));//signIn twice
		assertNotEquals(g.getCart(),s1.getCart());
		
		
	}

}
