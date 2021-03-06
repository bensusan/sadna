package TS_BL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import TS_SharedClasses.*;

public class BlGuest {
	private final static String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
	public static HashMap<Guest,String> currGuest;
	
	/**
	 * add product to cart amount times
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addImmediatelyProduct(Guest g, Product p, int amount, int discountCode) throws Exception {
		return g != null && BlCart.addImmediatelyProduct(g.getCart(), p, amount, discountCode);
	}
	
	static boolean addLotteryProduct(Guest g, Product p, int money) throws Exception {
		return g != null && BlCart.addLotteryProduct(g.getCart(), p, money);
	}

	/**
	 * remove product p from guest cart
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean removeProductFromCart(Guest g, Product p) throws Exception {
		return g != null && BlCart.removeProduct(g.getCart(), p);
	}

	/**
	 * replace the amount of product p by the new amount in guest cart
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean editProductAmount(Guest g, Product p, int amount) throws Exception {
		return g != null && BlCart.editProductAmount(g.getCart(), p, amount);
	}
	
	static boolean editProductDiscount(Guest g, Product p, int discountCode) throws Exception {
		return g != null && BlCart.editProductDiscount(g.getCart(), p, discountCode,g);
	}
	
	static boolean editProductPrice(Guest g, Product p, int money) throws Exception {
		return g != null && BlCart.editProductPrice(g.getCart(), p, money);
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean editCart(Guest g, Cart newCart) throws Exception {
		return g != null && BlCart.editCart(g.getCart(), newCart);
	}

	/**
	 * guest try to puchase his cart with given details
	 * 
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	
	//TODO - CreditCard
	static boolean puchaseCart(Guest g, String creditCardNumber, String buyerAddress) throws Exception {
		if(g == null) 
			throw new Exception("something went wrong");
		if(g.getCart().getProducts().isEmpty())
			throw new Exception("the cart is empty");	
		if(!BlMain.legalCreditCard(creditCardNumber))
			throw new Exception("ilegal credit card");
		if(!BlMain.legalAddress(buyerAddress))
			throw new Exception("ilegal address");
//		boolean isExistLotteryPurchase = false;
		Cart notPurchased = new Cart();
		Cart oldCart = g.getCart();
		
		//check if all product comply the product policy
		for (ProductInCart pic : g.getCart().getProducts()) {
			if (!(pic.getMyProduct().getPurchasePolicy().isCorrectProduct(pic.getAmount(), buyerAddress))){
				throw new Exception("product "+pic.getMyProduct().getName()+" does not comply with policy");
			}
		}//check if cart comply the store policy
		for (ProductInCart pic : g.getCart().getProducts()) {
			List<ProductInCart>StoreProducts=new ArrayList<ProductInCart>();
			for (ProductInCart pic2 : g.getCart().getProducts()) {
				if(pic.getMyProduct().getStore().equals(pic2.getMyProduct().getStore()))
					StoreProducts.add(pic2);
			}
			if (!pic.getMyProduct().getStore().getStorePolicy().isCorrectProduct(StoreProducts.size(), buyerAddress)){
				throw new Exception("the cart does not comply with "+pic.getMyProduct().getStore().getStoreName()+" store policy");
			}
		}//check if cart comply the category policy
		for(ProductInCart pic:g.getCart().getProducts()){
			List<ProductInCart>CategoryProducts=new ArrayList<ProductInCart>();
			for (ProductInCart pic2 : g.getCart().getProducts()) {
				if(pic.getMyProduct().getCategory().equals(pic2.getMyProduct().getCategory())){
					CategoryProducts.add(pic2);
				}
			}
			if (!pic.getMyProduct().getCategory().getPurchasePolicy().isCorrectProduct(CategoryProducts.size(), buyerAddress)){
				throw new Exception("the cart does not comply with "+pic.getMyProduct().getStore().getStoreName()+" store policy");
			}
		}
		
		Map<ProductInCart,Integer>productToPrice=new HashMap<ProductInCart,Integer>();
		currGuest = new HashMap<Guest, String>();
		//try to buy the products
		for (ProductInCart pic : g.getCart().getProducts()) {
			
			currGuest.put(g, creditCardNumber);
			boolean purchase = BlPurchaseType.purchase(pic, g,buyerAddress);
			if(purchase){
				int productPrice=pic.getMyProduct().getPrice();
				if (pic.getMyProduct().getType()instanceof ImmediatelyPurchase)
				{
					productPrice=pic.getMyProduct().getPurchasePolicy().updatePriceProduct(pic.getMyProduct().getPrice(), pic.getAmount(), buyerAddress, pic.getDiscountOrPrice());
					PurchasePolicy categoryDiscountTree=pic.getMyProduct().getStore().getCategoryDiscounts().get(pic.getMyProduct().getCategory());
					int sameStoreAndCategoryCounter=0;
					for(ProductInCart pic2:g.getCart().getProducts())
					{
						if(pic.getMyProduct().getStore().equals(pic2.getMyProduct().getStore())){
							if(pic.getMyProduct().getCategory().equals(pic2.getMyProduct().getCategory()))
							{
								sameStoreAndCategoryCounter++;
							}
						}
					}
					
					if(categoryDiscountTree!=null)
						productPrice=categoryDiscountTree.updatePriceProduct(productPrice, sameStoreAndCategoryCounter, buyerAddress, pic.getDiscountOrPrice());
				
				productPrice*=pic.getAmount();
				productToPrice.put(pic, productPrice);
				boolean payMoney = BlStore.payToStore(pic.getMyProduct().getStore(), productPrice, creditCardNumber);
				if(!payMoney){
					BlPurchaseType.undoPurchase(pic, g);
					notPurchased.getProducts().add(pic);
				}	
			}}
			else 
				notPurchased.getProducts().add(pic);
			
			currGuest.remove(g);
		}
		

		if(notPurchased.getProducts().size() == g.getCart().getProducts().size())
			throw new Exception("there aren't any products for immediate purchase");
		
		if(oldCart.getProducts().size() != g.getCart().getProducts().size()){
			oldCart.getProducts().removeAll(g.getCart().getProducts());
			notPurchased.getProducts().addAll(oldCart.getProducts());
		}
		
		for (ProductInCart ppp : g.getCart().getProducts()) {
			if(ppp.getMyProduct().getType() instanceof LotteryPurchase)
				notPurchased.getProducts().add(ppp);
		}
	
		Cart tmp = g.getCart();
		tmp.getProducts().removeAll(notPurchased.getProducts());
		g.setCart(tmp);
		
		//g.getCart() now has all the products that purchased.
		if(!BlStore.sendTheProducts(g, buyerAddress)){
			for(ProductInCart pic : g.getCart().getProducts()){
				BlPurchaseType.undoPurchase(pic, g);
				BlStore.undoPayToStore(pic.getMyProduct().getStore(), productToPrice.get(pic), creditCardNumber);
			}
			throw new Exception("we had problem with the supply system");
		}
		
		for (ProductInCart pic : g.getCart().getProducts()) {
			Purchase pur = BlStore.addProductToHistory(pic);
			
			if(g instanceof Subscriber)
				BlSubscriber.addPurchaseToHistory((Subscriber)g, pur);
		}
		
		notPurchased.getProducts().removeAll(oldCart.getProducts());
		g.setCart(notPurchased);
		
		return true;
	}

	/**
	 * guest try to puchase one product with given details
	 * 
	 * @param product
	 * @param amount
	 * @param creditCardNumber
	 * @param buyerAddress
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	
	static Subscriber signUp(Guest g, String username, String password, String fullName, String address, String phone, String creditCardNumber) throws Exception{
		if(g == null)
			throw new Exception("something went wrong");
		if(!BlMain.correctSpelledLettersSpacesNumbers(username) || !BlMain.correctSpelledLettersSpaces(fullName) || !BlMain.correctSpelledLettersSpacesNumbers(address) || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.correctSpelledNumbers(phone))
			throw new Exception("problem with one of the fields,check spelling try again"); //exception spell in user name | full name | address
		if(BlMain.checkIfSubscriberExists(username) != null)
			throw new Exception("the username is already taken, try again"); //user name exists
		if(!BlMain.legalPassword(password))
			throw new Exception("illegal password, try again"); //password rules failed.
		
		Subscriber newSub = new Subscriber(g.getCart(), username, password, fullName, address, phone, creditCardNumber, new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>()); 
		BlMain.allSubscribers.add(newSub);
		return newSub;
		
	}
	
	static Subscriber signIn(Guest g, String username, String password) throws Exception{
		if(g == null)
			throw new Exception("something went wrong");
		if(!BlMain.correctSpelledLettersSpacesNumbers(username))
			throw new Exception("wrong username");
		if(!BlMain.legalPassword(password))
			throw new Exception("wrong password");
		Subscriber ans = BlMain.checkIfSubscriberExists(username);
		
		if(ans != null && ans.getPassword().toString().equals(password.toString()))
		{
			if(ans.getCart().getProducts().isEmpty())
			{
				ans.setCart(g.getCart());
			}
			return ans;
		}
		
		throw new Exception("incorrect password or username");
	}
    
    static String md5Hash(String chechSum) {
        String hashedPass = null;
        try {
          MessageDigest messageDigest = MessageDigest.getInstance("MD5");
          messageDigest.update(chechSum.getBytes(), 0, chechSum.length());
          hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
          if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
          }
        } catch (Exception e) {
          System.out.println("failed to create md5");
          System.out.println(e.getMessage());
        }
        return hashedPass;
      }
    
}
