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
import org.junit.BeforeClass;

import TS_BL.BlMain;
import TS_BL.BlStore;
import TS_SharedClasses.*;

public class GuestAT {

	private static Guest g;
	private static Subscriber sub;
	private static StoreOwner so;
	private static Product immediateOvertProduct;
	private static Product immediateNoDiscountProduct;
	private static Product lotteryProduct;
	private static Product zeroAmountProduct;

	@BeforeClass
	public static void bef(){
		g = new Guest();
		try{
			sub = BlMain.signUp(g, "globUse1", "globPass", "usr", "name", "132412356", "1234567891011");
			try{
				Store s1 = BlMain.openStore(sub, "store_name1", 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
				List<StoreOwner> own1 = sub.getOwner();
				so = own1.get(0);

				try{
					immediateOvertProduct = new Product("prod1", 200, 4, "test cat 1", 
							new PurchasePolicy(new ImmediatelyPurchase(new OvertDiscount(Date.valueOf("2019-01-01"), 50))));
					try{
						immediateNoDiscountProduct = new Product("prod2", 200, 4, "test cat 2", 
								new PurchasePolicy(new ImmediatelyPurchase()));
						try{
							lotteryProduct = new Product("prod3", 100, 4, "test cat 3", 
									new PurchasePolicy(new LotteryPurchase(Date.valueOf("2019-01-01"))));
							try{
								zeroAmountProduct = new Product("prod4", 200, 4, "test cat 4", 
										new PurchasePolicy(new ImmediatelyPurchase()));



								try {
									BlMain.addProductToStore(so, immediateOvertProduct, 50);
								} catch (Exception e) {
									e.printStackTrace();
								}
								try {
									BlMain.addProductToStore(so, immediateNoDiscountProduct, 50);
								} catch (Exception e2) {
									e2.printStackTrace();
								}
								try {
									BlMain.addProductToStore(so, lotteryProduct, 50);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								try {
									BlMain.addProductToStore(so, zeroAmountProduct, 0);
								} catch (Exception e) {
									assertEquals(e.getMessage(), "amount must be greater than 0");
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void mainTest()
	{
		testGuestToSubscriber();
		testGetInfoOnStoreAndProduct();
		testKeepProductsInCart();
		testRemoveFromCart();
		testEditCart();
		//testLotteryPurchase();
		//testSinglePurchase();
		testCartPurchase();
	}
	//1.1
	//could not test specificlly entry to system there is no entry to system yet

	//1.2
	private void testGuestToSubscriber(){
		Guest g = new Guest();
		//good case - add new sub
		try{
			Subscriber s1 = BlMain.signUp(g, "sagivmap", "123123", "Sagiv Map", "Jerusalem", "132412356", "123451324656");
			//assertNotNull(s1);
			assertEquals(g.getCart(), s1.getCart());
			assertEquals("sagivmap", s1.getUsername());
			//assertEquals("123123", s1.getPassword());
			assertEquals("Sagiv Map", s1.getFullName());
			assertEquals("Jerusalem", s1.getAddress());
			assertEquals("Sagiv Map", s1.getFullName());
			assertEquals("132412356", s1.getPhone());
			assertEquals("123451324656", s1.getCreditCardNumber());
		}
		catch(Exception e){

		}
		//sad case - existing username
		try{
			Subscriber s2 = BlMain.signUp(g, "sagivmap", "45678999", "Sagiv Mag", "Be'er Sheva", "132412356", "123451324656");
		}catch (Exception e) {
			assertEquals(e.getMessage() , "the username is already taken, try again");
		}

		//bad case - corrupt input
		try {
			Subscriber s3 = BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		try {
			BlMain.signUp(null, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "something went wrong");
		}
		try {
			BlMain.signUp(g, null, "789789", "Adva San", "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}
		try {
			BlMain.signUp(g, "advasan", null, "Adva San", "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "illegal password, try again");

		}
		try {
			BlMain.signUp(g, "advasan", "", "Adva San", "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "illegal password, try again");

		}
		try {
			BlMain.signUp(g, "advasan", "789789", null, "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");

		}
		try {
			BlMain.signUp(g, "advasan", "789789", "", "Be'er Sheva", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");

		}
		try {
			BlMain.signUp(g, "advasan", "789789", "Adva San", "", "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}
		try {
			BlMain.signUp(g, "advasan", "789789", "Adva San", null, "132412356", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}
		try {
			BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "dsa541a", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}
		try {
			BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "", "1234567891011");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}
		try {
			BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", null);
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}
		try {
			BlMain.signUp(g, "advasan", "789789", "Adva San", "Be'er Sheva", "132412356", "");
		} catch (Exception e) {
			assertEquals(e.getMessage() , "problem with one of the fields,check spelling try again");
		}

	}
	//1.3
	private void testGetInfoOnStoreAndProduct(){

		//sad case - no stores in the system
		//Map<Store, Map<Product, Integer>> info = BlMain.getAllStoresWithThierProductsAndAmounts();
		//assertTrue(info.size() == 0);

		Guest g1 = new Guest();
		Subscriber sub1;
		try{
			sub1 = BlMain.signUp(g1, "usr1", "pass1", "usr", "name", "132412356", "121321233456");
			try{
				Store s1 = BlMain.openStore(sub1, "store_name2",5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
				List<StoreOwner> own1 = sub1.getOwner();
				StoreOwner so1 = own1.get(0);

				Product p1;
				Product p2;
				p1 = new Product("prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
				p2 = new Product("prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
				try {
					BlMain.addProductToStore(so1, p1, 5);
				} catch (Exception e) {
					assertEquals(so1.getStore().getProducts().get(p1) , ((Integer)5));
				}
				try {
					BlMain.addProductToStore(so1, p2, 3);
				} catch (Exception e) {
					assertEquals(so1.getStore().getProducts().get(p2) , ((Integer)3));
				}

				Guest g2 = new Guest();
				try{
					Subscriber sub2 = BlMain.signUp(g2, "usr2", "pass1", "user", "name", "123456789", "13332123132");
					try{
						Store s2 = BlMain.openStore(sub2,"store_name3", 5, new HashMap<Product, Integer>(), new ArrayList<Purchase>(), true);
						List<StoreOwner> own2 = sub2.getOwner();
						StoreOwner so2 = own2.get(0);
						try{
							Product p3 = new Product("prod1", 100, 4, "test cat 1", new PurchasePolicy(new ImmediatelyPurchase()));
							try{
								Product p4 = new Product("prod2", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));
								try{
									Product p5 = new Product("prod3", 100, 4, "test cat 2", new PurchasePolicy(new ImmediatelyPurchase()));

									try {
										BlMain.addProductToStore(so2, p3, 5);
									} catch (Exception e) {
										assertEquals(so2.getStore().getProducts().get(p3) , ((Integer)5));
									}
									try {
										BlMain.addProductToStore(so2, p4, 3);
									} catch (Exception e) {
										assertEquals(so2.getStore().getProducts().get(p4) , ((Integer)3));
									}
									try {
										BlMain.addProductToStore(so2, p5, 3);
									} catch (Exception e) {
										assertEquals(so2.getStore().getProducts().get(p5) , ((Integer)3));
									}

									Map<Store, Map<Product, Integer>> info = BlMain.getAllStoresWithThierProductsAndAmounts();
									assertTrue(info.containsKey(s1));
									Map<Product, Integer> prodDict1 = info.get(s1);
									assertTrue(prodDict1.containsKey(p1));
									assertEquals(5, prodDict1.get(p1).intValue());
									assertTrue(prodDict1.containsKey(p2));
									assertEquals(3, prodDict1.get(p2).intValue());

									assertTrue(info.containsKey(s2));
									Map<Product, Integer> prodDict2 = info.get(s2);
									assertTrue(prodDict2.containsKey(p3));
									assertTrue(prodDict2.get(p3)== 5);
									assertTrue(prodDict2.containsKey(p4));
									assertTrue(prodDict2.get(p4)== 3);
									assertTrue(prodDict2.containsKey(p5));
									assertTrue(prodDict2.get(p5)== 3);

								}catch (Exception e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							}
							catch (Exception e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
						catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	//1.5
	private void testKeepProductsInCart(){
		Guest g = new Guest();
		try {
			BlMain.addImmediatelyProduct(g, immediateOvertProduct, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BlMain.addImmediatelyProduct(g, immediateNoDiscountProduct, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Cart c = g.getCart();
		List<ProductInCart> cartContent = c.getProducts();
		boolean p1_found = false;
		boolean p2_found = false;
		for(ProductInCart p : cartContent){
			if(immediateOvertProduct.equals(p.getMyProduct()))
				p1_found = true;
			if(immediateNoDiscountProduct.equals(p.getMyProduct()))
				p2_found = true;
		}
		assertTrue(p1_found);
		assertTrue(p2_found);
	}
	//1.6.1
	private void testRemoveFromCart(){
		Guest g = new Guest();
		try {
			BlMain.addImmediatelyProduct(g, immediateOvertProduct, 1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			BlMain.addImmediatelyProduct(g, immediateNoDiscountProduct, 3);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		//item not found
		Product p3;
		try{
			p3 = new Product("prod3", 100, 4, "test cat 3", new PurchasePolicy(new ImmediatelyPurchase()));
			try{
				BlMain.removeProductFromCart(g, p3);
			}
			catch(Exception e){
				assertEquals(e.getMessage(), "the product isn't belongs to the cart");
			}
			//item found
			try{
				assertTrue(BlMain.removeProductFromCart(g, immediateOvertProduct));
				boolean p1_deleted = true;
				boolean p2_deleted = true;
				for(ProductInCart p : g.getCart().getProducts()){
					if(immediateOvertProduct.equals(p.getMyProduct()))
						p1_deleted = false;
					if(immediateNoDiscountProduct.equals(p.getMyProduct()))
						p2_deleted = false;
				}
				assertTrue(p1_deleted);
				assertFalse(p2_deleted);
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	//1.6.2
	private void testEditCart(){
		Guest g = new Guest();
		try {
			BlMain.addImmediatelyProduct(g, immediateOvertProduct, 1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			BlMain.addImmediatelyProduct(g, immediateNoDiscountProduct, 3);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		//edit amount of product
		//item not found
		Product p3;
		try{
			p3 = new Product("prod3", 100, 4, "test cat 3", new PurchasePolicy(new ImmediatelyPurchase()));
			try{
				BlMain.editProductAmount(g, p3, 3);
			}
<<<<<<< HEAD
			
			//edit the whole cart
			ProductInCart pic1 = new ProductInCart(immediateOvertProduct, 10, 20);
			ProductInCart pic2 = new ProductInCart(immediateNoDiscountProduct, 10, 15);
			List<ProductInCart> picl = new ArrayList<ProductInCart>();
			picl.add(pic1);
			picl.add(pic2);
			Cart newCart = new Cart(picl);

			
			assertTrue(BlMain.editCart(g, newCart));
			for(ProductInCart p : g.getCart().getProducts()){
				if(immediateOvertProduct.equals(p.getMyProduct())){
					assertEquals(20, p.getAmount());
					assertEquals(10, p.getDiscountOrPrice());
				}
				if(immediateNoDiscountProduct.equals(p.getMyProduct())){
					assertEquals(15, p.getAmount());
					assertEquals(10, p.getDiscountOrPrice());
=======
			catch(Exception e){
				assertEquals(e.getMessage(), "the product isn't belongs to the cart");
			}
			//item found
			try{
				assertTrue(BlMain.editProductAmount(g, immediateOvertProduct, 4));
				for(ProductInCart p : g.getCart().getProducts()){
					if(immediateOvertProduct.equals(p.getMyProduct()))
						assertEquals(4, p.getAmount());
				}

				//edit the whole cart
				ProductInCart pic1 = new ProductInCart(immediateOvertProduct, 10, 20);
				ProductInCart pic2 = new ProductInCart(immediateNoDiscountProduct, 10, 15);
				List<ProductInCart> picl = new ArrayList<ProductInCart>();
				picl.add(pic1);
				picl.add(pic2);
				Cart newCart = new Cart(picl);


				try{
					assertTrue(BlMain.editCart(g, newCart));
					for(ProductInCart p : g.getCart().getProducts()){
						if(immediateOvertProduct.equals(p.getMyProduct())){
							assertEquals(20, p.getAmount());
							assertEquals(10, p.getPrice());
						}
						if(immediateNoDiscountProduct.equals(p.getMyProduct())){
							assertEquals(15, p.getAmount());
							assertEquals(10, p.getPrice());
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
>>>>>>> Ofir
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}




	//1.7.1
	private void testLotteryPurchase(){
		fail("need to implement");
	}
	/*//1.7.2
	private void testSinglePurchase(){
		//notice the store in @Before function
		//purchase zero amount of product in store
		Guest g1 = new Guest();
		assertFalse(BlMain.pruchaseProduct(g1,zeroAmountProduct, 1, "1234512316789", "rehovot"));

		//purchase more then amount
		Guest g2 = new Guest();
		assertFalse(BlMain.pruchaseProduct(g2, immediateNoDiscountProduct, 11, "1234512316789" , "rehovot"));

		//test success purchase no discount
		assertTrue(BlMain.pruchaseProduct(g2, immediateNoDiscountProduct, 3, "1234512316789" , "rehovot"));

		//test success purchase no discount
		assertTrue(BlMain.pruchaseProduct(g2, immediateOvertProduct, 6, "1234512316789" , "rehovot"));

		Map<Product, Integer> prods = so.getStore().getProducts();
		assertEquals(7, prods.get(immediateNoDiscountProduct).intValue());
		assertEquals(6, prods.get(immediateOvertProduct).intValue());

	}*/
	//1.7.3
	private void testCartPurchase(){
		//purchase zero amount of product in store
		Guest g1 = new Guest();
		/*try{		
			BlMain.addImmediatelyProduct(g1,zeroAmountProduct, 1);
			try {
				assertFalse(BlMain.purchaseCart(g1, "132456", "rehovot"));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}*/
		//purchase more then amount
		Guest g2 = new Guest();
		try{
			BlMain.addImmediatelyProduct(g2, immediateNoDiscountProduct, 11);
			try{
				BlMain.purchaseCart(g2, "13245678", "rehovot");
			}
			catch (Exception e) {
				assertEquals(e.getMessage(), "ilegal credit card");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//test success purchase no discount
		Guest g3 = new Guest();
		try{
			BlMain.addImmediatelyProduct(g3, immediateNoDiscountProduct, 4);
			try{
				assertTrue(BlMain.purchaseCart(g3, "13245678", "rehovot"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//test success purchase no discount
		Guest g4 = new Guest();
		try{
			BlMain.addImmediatelyProduct(g4, immediateOvertProduct, 4);
			try{
				assertTrue(BlMain.purchaseCart(g4, "13245678", "rehovot"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}





}
