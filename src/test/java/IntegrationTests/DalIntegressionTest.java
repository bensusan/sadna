package IntegrationTests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import TS_SharedClasses.*;
import TS_DAL.DALProxy;

public class DalIntegressionTest {

	@Test
	public void testAddSubscriber() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT1", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>());
		try {
			dal.addSubscriber(s);
			boolean isContain=false;
			for(Subscriber newS:dal.allSubscribers()){
				if(newS.getUsername().equals("newUserT1"))
				{
					isContain=true;
				}
			}
			assertTrue(isContain);
			
			
			dal.removeSubscriber(s.getUsername());
			fail("error remove Subscriber");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testRemoveSubscriber() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT2", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>());
		try{
			dal.addSubscriber(s);
			dal.removeSubscriber(s.getUsername());
			boolean isContain=false;
			for(Subscriber newS:dal.allSubscribers()){
				if(newS.getUsername().equals("newUserT2"))
				{
					isContain=true;
				}
			}
			assertFalse(isContain);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddPurchaseToHistory() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT3", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try {
			Product prod = new Product("nameT3", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
			Purchase p=new Purchase(new Date(2018,4,13), new ProductInCart(prod,5,1));
			Store store=new Store("newStoreT3", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			
			
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addProductToStore(store, prod, 30, "category");
			dal.addPurchaseToHistory(s, p);
			
			List<Purchase>listP=dal.getMyPurchase("newUserT3");
			boolean isContain=false;
			for(Purchase newP:listP){
				if(newP.equals(p))
				{
					isContain=true;
				}
			}
			assertTrue(isContain);
			
			dal.removePurchase(p.getPurchaseID());
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddNewStoreOwner() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT4", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT4", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			dal.addSubscriber(s);
			dal.addStore(store);
			s=dal.getSubscriber("newUserT4", "newPass");
			int ownerCount=s.getOwner().size();
			dal.addNewStoreOwner(store, s);
			
			s=dal.getSubscriber("newUserT4", "newPass");
			assertEquals(s.getOwner().size(),ownerCount+1);
			
			dal.removeStoreOwner(s.getUsername(), store.getStoreId());
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddNewStoreManager() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT5", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT5", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			StoreManager sm=new StoreManager(store);
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addNewStoreManager(store, s, -1);
			s=dal.getSubscriber("newUserT5", "newPass");
			boolean isContain=false;
			for(StoreManager newMan:s.getManager()){
				if(newMan.equals(sm)){
					isContain=true;
				}
			}
			assertTrue(isContain);
			
			dal.deleteStoreManager(s.getUsername(), store.getStoreId());
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRemoveStoreOwner() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT6", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT6", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addNewStoreOwner(store, s);
			s=dal.getSubscriber("newUserT6", "newPass");
			int storeOwnerCount=s.getOwner().size();
			dal.removeStoreOwner(s.getUsername(), store.getStoreId());
			
			s=dal.getSubscriber("newUserT6", "newPass");
			assertEquals(s.getOwner().size()+1, storeOwnerCount);
			
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testDeleteStoreManager() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT7", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT7", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addNewStoreManager(store, s,-1);
			s=dal.getSubscriber("newUserT7", "newPass");
			int storeManagerCount=s.getManager().size();
			dal.deleteStoreManager(s.getUsername(), store.getStoreId());
			
			s=dal.getSubscriber("newUserT7", "newPass");
			assertEquals(s.getManager().size()+1, storeManagerCount);
			
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	
	//product
	@Test
	public void testAddProductToStore() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT8", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT8", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			Product prod = new Product("nameT8", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addProductToStore(store, prod, 30, "newCategory");
			
			boolean isContain=false;
			for(Product p:dal.getProductAmount(store.getStoreId()).keySet()){
				if(p.equals(prod))
				{
					isContain=true;
				}
			}
			assertTrue(isContain);
			
			dal.deleteProductFromStore(store.getStoreId(), prod.getId());
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testUpdateProductDetails() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT9", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT9", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			Product prod = new Product("nameT9", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addProductToStore(store, prod, 30, "newCategory");
			Product newProduct=new Product(prod);
			newProduct.setPrice(7);
			newProduct.setName("newProductName");
			dal.updateProductDetails(store, prod, newProduct, 30, "newCategory");
			
			boolean isContain=false;
			for(Product p:dal.getProductAmount(store.getStoreId()).keySet()){
				if(p.equals(newProduct))
				{
					isContain=true;
				}
				if(p.equals(prod)){
					fail();//product need to be updated
				}
			}
			assertTrue(isContain);
			
			dal.deleteProductFromStore(store.getStoreId(), prod.getId());
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testDeleteProductFromStore() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT10", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT10", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
			Product prod = new Product("nameT10", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addProductToStore(store, prod, 30, "newCategory");
			dal.deleteProductFromStore(store.getStoreId(), prod.getId());
			
			boolean isContain=false;
			for(Product p:dal.getProductAmount(store.getStoreId()).keySet()){
				if(p.equals(prod))
				{
					isContain=true;
				}
			}
			assertFalse(isContain);
			
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//store
	@Test
	public void testUpdateCloseStore() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT11", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT11", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		
			dal.addSubscriber(s);
			
			dal.addStore(store);
			
			dal.closeStore(store.getStoreId());
			store=dal.getStoreByStoreId(store.getStoreId());
			assertFalse(store.getIsOpen());
			
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testUpadteOpenStore() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT12", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT12", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), false);
		
			dal.addSubscriber(s);
			dal.addStore(store);
			
			dal.closeStore(store.getStoreId());
			store=dal.getStoreByStoreId(store.getStoreId());
			assertTrue(store.getIsOpen());
			
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testUpdateMoneyEarned() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT13", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT13", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		
			dal.addSubscriber(s);
	
			dal.addStore(store);
			
			dal.updateMoneyEarned(store, 55);
			store=dal.getStoreByStoreId(store.getStoreId());
			assertEquals(store.getMoneyEarned(),55);
			
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testStockUpdate() {
		DALProxy dal=new DALProxy();
		Subscriber s=new Subscriber(new Cart(), "newUserT14", "newPass", "newFullName", "newAddress", "newPhone", "newCreditCard", new LinkedList<Purchase>(), null, null);
		try{
			Store store=new Store("newStoreT14", "newAddress", "newPhone", 3, new HashMap<Product,Integer>(), new LinkedList<Purchase>(), true);
		
			Product prod = new Product("nameT14", 5, 4, new EmptyPolicy(),new ImmediatelyPurchase());
			dal.addSubscriber(s);
			dal.addStore(store);
			dal.addProductToStore(store, prod, 40, "newCategory");
			
			dal.stockUpdate(prod, 35, store);
			Map<Product,Integer>map=dal.getProductAmount(store.getStoreId());
			boolean found=false;
			for(Product newP:map.keySet())
			{
				if(newP.getId()==prod.getId()){
					found=true;
					assertEquals(map.get(newP).intValue(),35);
				}
			}
			assertTrue(found);
			
			dal.deleteProductFromStore(store.getStoreId(), prod.getId());
			dal.deleteStore(store.getStoreId());
			dal.removeSubscriber(s.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	

}
