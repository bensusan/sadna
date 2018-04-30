package TS_BL;

import TS_SharedClasses.*;

public class BlCart {

	// /**
	// * customer try to puchase this cart with given details
	// *
	// * @param creditCardNumber
	// * @param buyerAddress
	// * @return true if succseed false otherwise
	// */
	// public static boolean puchaseCart(Cart c, int creditCardNumber, String
	// buyerAddress) {
	// // TODO missing implementation
	// return true;
	// }

	/**
	 * add <p,amount> to products
	 * 
	 * @param p
	 * @param amount
	 * @param discountCode 
	 * @return true if succseed false otherwise
	 */
	static boolean addImmediatelyProduct(Cart c, Product p, int amount, int discountCode) {
		if (c == null || p == null || amount < 1 || p.getStore() == null)
			return false;
		if(!isImmediatelyPurchase(p))
			return false;
		if(isProductExistInCart(c, p) != -1)
			return false;
		ImmediatelyPurchase myPurchaseType = ((ImmediatelyPurchase)p.getPurchasePolicy().getPurchaseType());
		int updatedPrice = myPurchaseType.getDiscountPolicy().updatePrice(p.getPrice(), discountCode);
		return c.getProducts().add(new ProductInCart(p, updatedPrice, amount));
	}
	
	private static boolean isImmediatelyPurchase(Product p){
		if(p.getPurchasePolicy() == null || !(p.getPurchasePolicy().getPurchaseType() instanceof ImmediatelyPurchase))
			return false;
		return true;
	}
	
	private static boolean isLotteryPurchase(Product p){
		if(p.getPurchasePolicy() == null || !(p.getPurchasePolicy().getPurchaseType() instanceof LotteryPurchase))
			return false;
		return true;
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
	 */
	static boolean removeProduct(Cart c, Product p) {
		if (c == null || p == null)
			return false;
		int index = isProductExistInCart(c, p); 
		if (index != -1) {
			return c.getProducts().remove(index) != null;
		}
		return false;
	}

	/**
	 * replace the amount of product p by the new amount
	 * 
	 * @param p
	 * @param amount
	 * @return true if succseed false otherwise
	 */
	static boolean editProductAmount(Cart c, Product p, int amount) {
		if (c == null || p == null || amount < 1 || !isImmediatelyPurchase(p))
			return false;
		int index = isProductExistInCart(c, p);
		if (index != -1) {
			ProductInCart old = c.getProducts().get(index);
			old.setAmount(amount);
			return true;
		}
		return false;
	}
	
	static boolean editProductDiscount(Cart c, Product p, int discountCode){
		if (c == null || p == null || !isImmediatelyPurchase(p))
			return false;
		int index = isProductExistInCart(c, p);
		if (index != -1) {
			ProductInCart old = c.getProducts().get(index);
			int updatedPrice = ((ImmediatelyPurchase)p.getPurchasePolicy().getPurchaseType()).getDiscountPolicy().updatePrice(p.getPrice(), discountCode); 
			old.setPrice(updatedPrice);
			return true;
		}
		return false;
	}
	
	static boolean editProductPrice(Cart c, Product p, int money){
		if (c == null || p == null || money < 0 || !isLotteryPurchase(p))
			return false;
		int index = isProductExistInCart(c, p);
		if (index != -1) {
			ProductInCart old = c.getProducts().get(index);
			old.setPrice(money);
			return true;
		}
		return false;
	}

	/**
	 * edit products by replaceing the amount of each product with the new
	 * amount if there is a product in newcart which doesn't exist in c it will
	 * return // false
	 * 
	 * @param newCart
	 * @return true if succseed false otherwise
	 */
	static boolean editCart(Cart c, Cart newCart) {
		if (c == null || newCart == null)
			return false;
		for (ProductInCart pic : newCart.getProducts()) {
			if(isProductExistInCart(c, pic.getMyProduct()) == -1)
				return false;
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

	public static boolean addLotteryProduct(Cart c, Product lotteryProduct, int money) {
		if (c == null || lotteryProduct == null || money < 0 || lotteryProduct.getStore() == null)
			return false;
		if(!isLotteryPurchase(lotteryProduct))
			return false;
		if(isProductExistInCart(c, lotteryProduct) != -1)
			return false;
		return c.getProducts().add(new ProductInCart(lotteryProduct, money, 1));
	}

}
