package IntegrationTests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import TS_SharedClasses.*;
import TS_DAL.DAL;

public class DalIntegressionTest {

	
	//allSubscribers
	//List<StoreOwner> getSubscriberOwners(String username)
	//Store getStoreByStoreId(String storeId)
	//List<StoreManager> getSubscriberManagers(String username)
	//List<Purchase> getMyPurchase(String username)
	//List<Purchase> getStorePurchase(Store store)
	//Store getProductStore(Product p)
	//getStoreProducts
	//List<Category>getAllCategory
	//Category getCategory(String categoryName)
	//Subscriber getSubscriber(String username, String password)
	//isSubscriberExist(String username)
	//isAdmin(String username)
	//checkInStock(p, amount)
	


	

	
	@Test
	public void testAddSubscriber() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>());
		dal.addSubscriber(s);
		
		boolean isContain=false;
		for(Subscriber newS:dal.allSubscribers()){
			if(newS.getUsername().equals("newUser"))
			{
				isContain=true;
			}
		}
		assertTrue(isContain);
		
		dal.removeSubscriber(s);
		fail("error remove Subscriber");
	}
	
	@Test
	public void testRemoveSubscriber() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>());
		dal.addSubscriber(s);
		dal.removeSubscriber(s);
		boolean isContain=false;
		for(Subscriber newS:dal.allSubscribers()){
			if(newS.getUsername().equals("newUser"))
			{
				isContain=true;
			}
		}
		assertFalse(isContain);
		fail("error remove Subscriber");
	}

	@Test
	public void testAddPurchaseToHistory() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try {
			Product prod = new Product("name", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
			Purchase p=new Purchase(new Date(2018,4,13), new ProductInCart(prod,5,1));
			
			dal.addSubscriber(s);
			dal.addPurchaseToHistory(s, p);
			
			List<Purchase>listP=dal.getMyPurchase("newUser");
			boolean isContain=false;
			for(Purchase newP:listP){
				if(newP.equals(p))
				{
					isContain=true;
				}
			}
			assertTrue(isContain);
			
			dal.removePurchase(s,p);
			dal.removeSubscriber(s);
			fail("Not yet implemented");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddOwner() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		StoreOwner so=new StoreOwner(store);
		dal.addSubscriber(s);
		//open Store
		dal.addOwner(s, so);
		s=dal.getSubscriber("newUser", "newPass");
		boolean isContain=false;
		for(StoreOwner newOwn:s.getOwner()){
			if(newOwn.equals(so)){
				isContain=true;
			}
		}
		assertTrue(isContain);
		
		dal.deleteOwner(s, so);
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}

	@Test
	public void testAddManager() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		StoreManager sm=new StoreManager(store);
		dal.addSubscriber(s);
		//open store
		dal.addManager(s, sm);
		s=dal.getSubscriber("newUser", "newPass");
		boolean isContain=false;
		for(StoreManager newMan:s.getManager()){
			if(newMan.equals(sm)){
				isContain=true;
			}
		}
		assertTrue(isContain);
		
		dal.deleteManager(s, sm);
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteOwner() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		StoreOwner so=new StoreOwner(store);
		dal.addSubscriber(s);
		//open Store
		dal.addOwner(s, so);
		dal.deleteOwner(s, so);
		s=dal.getSubscriber("newUser", "newPass");
		boolean isContain=false;
		for(StoreOwner newOwn:s.getOwner()){
			if(newOwn.equals(so)){
				isContain=true;
			}
		}
		assertFalse(isContain);
		
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteManager() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		StoreManager sm=new StoreManager(store);
		dal.addSubscriber(s);
		//open store
		dal.addManager(s, sm);
		dal.deleteManager(s, sm);
		
		s=dal.getSubscriber("newUser", "newPass");
		boolean isContain=false;
		for(StoreManager newMan:s.getManager()){
			if(newMan.equals(sm)){
				isContain=true;
			}
		}
		assertFalse(isContain);
		
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}

	
	//product
	@Test
	public void testAddProductToStore() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		Product prod = new Product("name", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
		dal.addSubscriber(s);
		//open store
		dal.addProductToStore(store, prod, 30, "newCategory");
		
		boolean isContain=false;
		for(Product p:dal.getStoreProduct(store).keySet()){
			if(p.equals(prod))
			{
				isContain=true;
			}
		}
		assertTrue(isContain);
		
		dal.deleteProductFromStore(store, prod);
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateProductDetails() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		Product prod = new Product("name", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
		dal.addSubscriber(s);
		//open store
		dal.addProductToStore(store, prod, 30, "newCategory");
		Product newProduct=new Product(prod);
		newProduct.setPrice(7);
		newProduct.setName("newProductName");
		dal.updateProductDetails(store, prod, newProduct, 30, "newCategory");
		
		boolean isContain=false;
		for(Product p:dal.getStoreProduct(store).keySet()){
			if(p.equals(newProduct))
			{
				isContain=true;
			}
			if(p.equals(prod)){
				fail();//product need to be updated
			}
		}
		assertTrue(isContain);
		
		dal.deleteProductFromStore(store, prod);
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeleteProductFromStore() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		Product prod = new Product("name", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
		dal.addSubscriber(s);
		//open store
		dal.addProductToStore(store, prod, 30, "newCategory");
		dal.deleteProductFromStore(store, prod);
		
		boolean isContain=false;
		for(Product p:dal.getStoreProduct(store).keySet()){
			if(p.equals(prod))
			{
				isContain=true;
			}
		}
		assertFalse(isContain);
		
		//delete store
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}
	

	@Test
	public void testAddStoreOwner() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		StoreOwner so=new StoreOwner(store);
		dal.addSubscriber(s);
		//open store
		dal.addStoreOwner(s, so);
		
		List<StoreOwner>listSo=dal.getSubscriberOwners("newUser");
		boolean isContain=false;
		for(StoreOwner newso:listSo)
		{
			if(newso.equals(so))
			{
				isContain=true;
			}
		}
		assertTrue(isContain);
		
		
		dal.deleteOwner(s, so);//from Store
		fail();
		//deleteStore
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewStoreOwner() {
		DAL dal=DAL.getInstance();
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewManager() {
		DAL dal=DAL.getInstance();
		fail("Not yet implemented");
	}

	//store
	@Test
	public void testUpdateCloseStore() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		dal.addSubscriber(s);
		//open store
		
		dal.updateCloseStore(store);
		store=dal.getStoreByStoreId(store.getStoreId());
		assertFalse(store.getIsOpen());
		
		//deleteStore
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpadteOpenStore() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		dal.addSubscriber(s);
		//open store
		
		dal.updateCloseStore(store);
		store=dal.getStoreByStoreId(store.getStoreId());
		assertTrue(store.getIsOpen());
		
		//deleteStore
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdategetMoneyEarned() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		dal.addSubscriber(s);
		//open store
		
		dal.updategetMoneyEarned(store, 55);
		store=dal.getStoreByStoreId(store.getStoreId());
		assertEquals(store.getMoneyEarned(),55);
		
		//deleteStore
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}
	
	@Test
	public void testStockUpdate() {
		DAL dal=DAL.getInstance();
		Subscriber s=new Subscriber(new Cart(), "newUser", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		Store store=new Store("newStore", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		Product prod = new Product("name", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
		dal.addSubscriber(s);
		//open store
		dal.addProductToStore(store, prod, 40, "newCategory");
		
		dal.stockUpdate(prod, 35, store);
		Map<Product,Integer>map=dal.getStoreProduct(store);
		boolean found=false;
		for(Product newP:map.keySet())
		{
			if(newP.getId()==prod.getId()){
				found=true;
				assertEquals(map.get(newP).intValue(),35);
			}
		}
		assertTrue(found);
		
		dal.deleteProductFromStore(store, prod);
		//deleteStore
		dal.removeSubscriber(s);
		fail("Not yet implemented");
	}


	

}
