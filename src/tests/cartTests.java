package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.*;

public class cartTests {

	private static Cart c;
	private static Product p;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		
		c=new Cart();
		p=new Product("123", "ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
	}
	@Test
	public void testAddProduct() {
		assertFalse(BlMain.addProduct(null, p, 1));
		assertFalse(BlMain.addProduct(c, null, 1));
		assertFalse(BlMain.addProduct(c, p, -1));
		
		assertTrue(BlMain.addProduct(c, p, 1));
		assertEquals(c.getProducts().get(p).intValue(),1);
		assertFalse(BlMain.addProduct(c, p, 1));
		assertEquals(c.getProducts().get(p).intValue(),1);
		Product p2=new Product("456", "ping pong ball", 5, 3, "toyes", new PurchasePolicy(new ImmediatelyPurchase()));
		assertTrue(BlMain.addProduct(c, p2, 1));
	}

	@Test
	public void testEditProduct() {
		assertFalse(BlMain.editProduct(null, p, 3));
		assertFalse(BlMain.editProduct(c, null, 3));
		assertFalse(BlMain.editProduct(c, p, -1));
		assertTrue(BlMain.editProduct(c, p, 3));
		assertEquals(c.getProducts().get(p).intValue(),3);
	}
	@Test
	public void testRemoveProduct() {
		assertFalse(BlMain.removeProduct(null, p));
		assertFalse(BlMain.removeProduct(c, null));
		assertTrue(BlMain.removeProduct(c, p));
		assertFalse(c.getProducts().containsKey(p));
		assertFalse(BlMain.removeProduct(c, p));//cart does not contains product
	}
	@Test
	public void testEditCart() {
		Map<Product,Integer> tempCart;
		Cart nCart=null;
		assertFalse(BlMain.editCart(nCart, c.getProducts()));
		assertFalse(BlMain.editCart(c, null));
		tempCart=new HashMap<Product,Integer>(c.getProducts());
		assertTrue(BlMain.editCart(c, new HashMap<Product,Integer>()));
		assertTrue(BlMain.editCart(c, tempCart));
	}

}
