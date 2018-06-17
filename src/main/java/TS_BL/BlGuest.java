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
import static TS_BL.BlMain.dalRef;

public class BlGuest {
	private final static String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
	
	/**
	 * add product to cart amount times
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addImmediatelyProduct(Guest g, Product p, int amount, int discountCode) throws Exception {
		if(g == null)
			throw new Exception("addImmediatelyProduct Failed");
		if(BlCart.addImmediatelyProduct(g.getCart(), p, amount, discountCode)){
			if(g instanceof Subscriber)
				dalRef.addImeddiatleyProductToCart(((Subscriber)g).getUsername(), p, amount, discountCode);
			return true;
		}
		return false;
	}
	
	static boolean addLotteryProduct(Guest g, Product p, int money) throws Exception {
		if(g == null)
			throw new Exception("addLotteryProduct Failed");
		if(BlCart.addLotteryProduct(g.getCart(), p, money)){
			if(g instanceof Subscriber)
				dalRef.addLotteryProductToCart(((Subscriber)g).getUsername(), p, money);
			return true;
		}
		return false;
	}

	/**
	 * remove product p from guest cart
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean removeProductFromCart(Guest g, Product p) throws Exception {
		if(g == null)
			throw new Exception("removeProductFromCart Failed");
		if(BlCart.removeProduct(g.getCart(), p)){
			if(g instanceof Subscriber)
				dalRef.removeProductFromCart(((Subscriber)g).getUsername(), p.getId());
			return true;
		}
		return false;
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
		if(g == null)
			throw new Exception("editProductAmount Failed");
		if(BlCart.editProductAmount(g.getCart(), p, amount)){
			if(g instanceof Subscriber)
				dalRef.editProductAmount(((Subscriber)g).getUsername(), p, amount);
			return true;
		}
		return false;
	}
	
	static boolean editProductDiscount(Guest g, Product p, int discountCode) throws Exception {
		if(g == null)
			throw new Exception("editProductDiscount Failed");
		if(BlCart.editProductDiscount(g.getCart(), p, discountCode,g)){
			if(g instanceof Subscriber)
				dalRef.editProductCode(((Subscriber)g).getUsername(), p, discountCode);
			return true;
		}
		return false;
	}
	
	static boolean editProductPrice(Guest g, Product p, int money) throws Exception {
		if(g == null)
			throw new Exception("editProductPrice Failed");
		if(BlCart.editProductPrice(g.getCart(), p, money)){
			if(g instanceof Subscriber)
				dalRef.editProductAmount(((Subscriber)g).getUsername(), p, money);
			return true;
		}
		return false;
	}

	/**
	 * edit products in cart by a new cart
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	
	//UNUSE SO I DONT SUPPORT IT IN DAL
	static boolean editCart(Guest g, Cart newCart) throws Exception {
		if(g == null)
			throw new Exception("editProductPrice Failed");
		if(BlCart.editCart(g.getCart(), newCart)){
			if(g instanceof Subscriber)
//				dalRef.setNewCartForSubscriber(((Subscriber)g).getUsername(), g.getCart()); //guest cart changed thats why g.getCart()
			return true;
		}
		return false;
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
			List<ProductInCart>CategoryStoreProducts=new ArrayList<ProductInCart>();
			for (ProductInCart pic2 : g.getCart().getProducts()) {
				if(pic.getMyProduct().getCategory().equals(pic2.getMyProduct().getCategory())&&
						pic.getMyProduct().getStore().equals(pic2.getMyProduct().getStore())){
					CategoryStoreProducts.add(pic2);
				}
			}
			if (!pic.getMyProduct().getStore().getCategoryPolicy().get(pic.getMyProduct().getCategory()).isCorrectProduct(CategoryStoreProducts.size(), buyerAddress)){
				throw new Exception("the cart does not comply with "+pic.getMyProduct().getStore().getStoreName()+" store policy");
			}
		}
		
		Map<ProductInCart,Integer> productToPrice = new HashMap<ProductInCart,Integer>();
		//try to buy the products
		for (ProductInCart pic : g.getCart().getProducts()) {

			boolean purchase = BlPurchaseType.purchase(pic, g,buyerAddress);
			if(purchase){
				int productPrice = pic.getMyProduct().getPrice();
				if (pic.getMyProduct().getType() instanceof ImmediatelyPurchase)
				{
					productPrice = pic.getMyProduct().getPurchasePolicy().updatePriceProduct(pic.getMyProduct().getPrice(), pic.getAmount(), buyerAddress, pic.getDiscountOrPrice());
					PurchasePolicy categoryDiscountTree = pic.getMyProduct().getStore().getCategoryDiscounts().get(pic.getMyProduct().getCategory());
					int sameStoreAndCategoryCounter = 0;
					for(ProductInCart pic2 : g.getCart().getProducts())
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
				}
				else{
					productPrice = pic.getDiscountOrPrice();
				}
				productPrice *= pic.getAmount();
				productToPrice.put(pic, productPrice);
				boolean payMoney = BlStore.payToStore(pic.getMyProduct().getStore(), productPrice, creditCardNumber);
				if(!payMoney){
					BlPurchaseType.undoPurchase(pic, g);
					notPurchased.getProducts().add(pic);
				}	
			}
			else 
				notPurchased.getProducts().add(pic);
		}
		
		if(notPurchased.getProducts().size() == g.getCart().getProducts().size())
			throw new Exception("there aren't any products for immediate purchase");
		
		g.getCart().getProducts().removeAll(notPurchased.getProducts());
		//g.getCart() now has all the products that purchased.
		if(!BlStore.sendTheProducts(g, buyerAddress)){
			for(ProductInCart pic : g.getCart().getProducts()){
				BlPurchaseType.undoPurchase(pic, g);
				BlStore.undoPayToStore(pic.getMyProduct().getStore(), productToPrice.get(pic), creditCardNumber);
			}
			throw new Exception("we had problem with the supply system");
		}
		
		for (ProductInCart pic : g.getCart().getProducts()) {
			PurchaseType pt = pic.getMyProduct().getType(); 
			if(pt instanceof LotteryPurchase)
				BlLotteryPurchase.startLottery(((LotteryPurchase)pt));
			
			Purchase pur = BlStore.addProductToHistory(pic);
			
			if(g instanceof Subscriber)
				BlSubscriber.addPurchaseToHistory((Subscriber)g, pur);
		}
		g.setCart(notPurchased);
		//TODO
//		if(isExistLotteryPurchase)
//			BlMain.addCreditCardToMap(creditCardNumber, g);
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
		if(BlMain.checkIfSubscriberExists(username) != null)
			throw new Exception("the username is already taken, try again"); //user name exists
		if(!BlMain.legalPassword(password))
			throw new Exception("illegal password, try again"); //password rules failed.
		if(!BlMain.correctSpelledLettersSpacesNumbers(username) || !BlMain.correctSpelledLettersSpaces(fullName) || !BlMain.correctSpelledLettersSpacesNumbers(address) || !BlMain.legalCreditCard(creditCardNumber) || !BlMain.correctSpelledNumbers(phone))
			throw new Exception("problem with one of the fields,check spelling try again"); //exception spell in user name | full name | address
		
		Subscriber newSub = new Subscriber(g.getCart(), username, password, fullName, address, phone, creditCardNumber, new LinkedList<Purchase>(), new LinkedList<StoreManager>(), new LinkedList<StoreOwner>()); 
		
		dalRef.addSubscriber(newSub);
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
//			if(ans.getCart().getProducts().isEmpty())
//			{
//				ans.setCart(g.getCart());
//				dalRef.setNewCartForSubscriber(((Subscriber)g).getUsername(), g.getCart());
//			}
			return ans;
		}
		
		throw new Exception("incorrect password or username");
	}
    
    static String md5Hash(String chechSum) throws Exception{
        String hashedPass = null;
        try {
          MessageDigest messageDigest = MessageDigest.getInstance("MD5");
          messageDigest.update(chechSum.getBytes(), 0, chechSum.length());
          hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
          if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
          }
        } catch (Exception e) {
        	throw new Exception("failed to encrypt with md5");
        }
        return hashedPass;
      }
    
}
