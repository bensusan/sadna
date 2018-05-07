package TS_BL;

import TS_SharedClasses.*;

public class BlCart {

	/**
	 * add <p,amount> to products
	 * 
	 * @param p
	 * @param amount
	 * @param discountCode 
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean addImmediatelyProduct(Cart c, Product p, int amount, int discountCode) throws Exception {
		if (c == null || p == null || p.getStore() == null)
			throw new Exception("something went wrong");
		if (amount < 1)
			throw new Exception("amount must be greater than 1");
		if(!BlMain.isImmediatelyPurchase(p))
			throw new Exception("this product isn't for immediate purchase");
		if(isProductExistInCart(c, p) != -1)
			throw new Exception("this product already in the cart");
		ImmediatelyPurchase myPurchaseType = ((ImmediatelyPurchase)p.getPurchasePolicy().getPurchaseType());
		int updatedPrice = myPurchaseType.getDiscountPolicy().updatePrice(p.getPrice(), discountCode);
		return c.getProducts().add(new ProductInCart(p, updatedPrice, amount));
	}
	
	
	
	//return the index of the product if exists else return -1;
	private static int isProductExistInCart(Cart c, Product p){
		for (int i = 0; i < c.getProducts().size(); i++)
			if(c.getProducts().get(i).getMyProduct().equals(p))
				return i;
		return -1;
	}

	/**
	 * remove product p from products
	 * 
	 * @param p
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean removeProduct(Cart c, Product p) throws Exception {
		if (c == null || p == null)
			throw new Exception("something went wrong");
		int index = isProductExistInCart(c, p); 
		if (index != -1) {
			return c.getProducts().remove(index) != null;
		}
		throw new Exception("the product isn't belongs to the cart");
	}

	/**
	 * replace the amount of product p by the new amount
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean editProductAmount(Cart c, Product p, int amount) throws Exception {
		if (c == null || p == null)
			throw new Exception("something went wrong");
		if(amount < 1)
			throw new Exception("amout must be greater than 1");
		if(!BlMain.isImmediatelyPurchase(p))
			throw new Exception("you can't edit product that isn't for immediate purchase");
		
		int index = isProductExistInCart(c, p);
		if (index != -1) {
			ProductInCart old = c.getProducts().get(index);
			old.setAmount(amount);
			return true;
		}

		throw new Exception("the product isn't belongs to the cart");
	}
	
	static boolean editProductDiscount(Cart c, Product p, int discountCode) throws Exception{
		if (c == null || p == null)
			throw new Exception("something went wrong");
		if(!BlMain.isImmediatelyPurchase(p))
			throw new Exception("something went wrong");
		int index = isProductExistInCart(c, p);
		if (index != -1) {
			ProductInCart old = c.getProducts().get(index);
			int updatedPrice = ((ImmediatelyPurchase)p.getPurchasePolicy().getPurchaseType()).getDiscountPolicy().updatePrice(p.getPrice(), discountCode); 
			old.setPrice(updatedPrice);
			return true;
		}
		throw new Exception("the product isn't belongs to the cart");
	}
	
	static boolean editProductPrice(Cart c, Product p, int money) throws Exception{
		if (c == null || p == null)
			throw new Exception("something went wrong");
		if(money < 0)
			throw new Exception("must be a positive number");
		if(!BlMain.isLotteryPurchase(p))
			return false;
		
		int index = isProductExistInCart(c, p);
		if (index != -1) {
			ProductInCart old = c.getProducts().get(index);
			old.setPrice(money);
			return true;
		}
		throw new Exception("the product isn't belongs to the cart");
	}

	/**
	 * edit products by replaceing the amount of each product with the new
	 * amount if there is a product in newcart which doesn't exist in c it will
	 * return // false
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 * @throws Exception 
	 */
	static boolean editCart(Cart c, Cart newCart) throws Exception {
		if (c == null || newCart == null)
			throw new Exception("something went wrong");
		
		for (ProductInCart pic : newCart.getProducts()) {
			if(isProductExistInCart(c, pic.getMyProduct()) == -1)
				throw new Exception("the product doesn't exists");
		}
		int index = -1;
		Cart result = new Cart();
		
		for (ProductInCart pic : c.getProducts()) {
			ProductInCart toAdd = pic;
			index = isProductExistInCart(newCart, pic.getMyProduct());
			if(index != -1)
				toAdd = newCart.getProducts().get(index);
			result.getProducts().add(toAdd);
		}
		c.setProducts(result.getProducts());
		return true;
	}

	public static boolean addLotteryProduct(Cart c, Product lotteryProduct, int money) throws Exception {
		if (c == null || lotteryProduct == null || lotteryProduct.getStore() == null) 
			throw new Exception("something went wrong");		
		if(money < 0)
			throw new Exception("must be a positive number");
		if(!BlMain.isLotteryPurchase(lotteryProduct))
			throw new Exception("the product isn't belongs to any lottery");
		if(isProductExistInCart(c, lotteryProduct) != -1)
			throw new Exception("the product alreadt exists in cart");
		
		return c.getProducts().add(new ProductInCart(lotteryProduct, money, 1));
	}

}
