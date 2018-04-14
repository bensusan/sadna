package TS_BL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TS_SharedClasses.Cart;
import TS_SharedClasses.DiscountPolicy;
import TS_SharedClasses.Guest;
import TS_SharedClasses.HiddenDiscount;
import TS_SharedClasses.ImmediatelyPurchase;
import TS_SharedClasses.LotteryPurchase;
import TS_SharedClasses.Product;
import TS_SharedClasses.PurchaseType;

public class BlOfir {

	//do i need this? need to delete
	//	public static boolean purchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
	//		Guest g = new Guest(c);
	//		return puchaseCart(g, creditCardNumber, buyerAddress);
	//	}

	public static boolean addProduct(Cart c, Product p, int amount) {
		Map<Product, Integer> toRet = c.getProducts();
		toRet.put(p, amount);
		c.setProducts(toRet);
		return true;

	}

	public static boolean removeProduct(Cart c, Product p) {
		if (c.getProducts().containsKey(p)) {
			Map<Product, Integer> toRet = c.getProducts();
			toRet.remove(p);
			c.setProducts(toRet);
			return true;
		}
		return false;

	}

	public static boolean editProduct(Cart c, Product p, int amount) {
		if(c.getProducts().containsKey(p)){
			Map<Product, Integer> toRet = c.getProducts();
			toRet.put(p, amount);
			c.setProducts(toRet);
			return true;
		}
		return false;
	}

	// replacing the amount of exisiting products in the cart c
	// if there is a product in newcart which doesn't exist in c it will return
	// false
	public static boolean editCart(Cart c, Map<Product, Integer> newCart) {
		Map<Product, Integer> toRet = c.getProducts();
		for (Product p : newCart.keySet()) {
			if (!c.getProducts().containsKey(p)) {
				return false; // the p product doesn't exist in the cart,
				// therefore you need to add it
			}
			toRet.put(p, newCart.get(p));
		}
		c.setProducts(toRet);
		return true;
	}

	public static int updatePrice(DiscountPolicy dp, int price) {
		return BlDiscountPolicy.updatePrice(dp, price);
	}

	public static boolean addProductToCart(Guest g, Product p, int amount) {
		Cart c = g.getCart();
		Map<Product, Integer> toRet = c.getProducts();
		toRet.put(p, amount);
		c.setProducts(toRet);
		g.setCart(c);
		return true;
	}

	public static boolean removeProductFromCart(Guest g, Product p) {
		if (g.getCart().getProducts().containsKey(p)) {
			Cart c = g.getCart();
			Map<Product, Integer> toRet = c.getProducts();
			toRet.remove(p);
			c.setProducts(toRet);
			g.setCart(c);
			return true;
		}
		return false;
	}

	public static boolean editProductInCart(Guest g, Product p, int amount) {
		Cart c = g.getCart();
		Map<Product, Integer> toRet = c.getProducts();
		if (!c.getProducts().containsKey(p)) {
			return false; // the p product doesn't exist in the cart, therefore
			// you need to add it
		}
		toRet.put(p, amount);
		c.setProducts(toRet);
		g.setCart(c);
		return true;
	}

	public static boolean editCart(Guest g, Map<Product, Integer> newCart) {
		Cart c = g.getCart();
		c.setProducts(newCart);
		g.setCart(c);
		return true;
	}

	public static boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress) {
		for (Product p : g.getCart().getProducts().keySet()) {
			pruchaseProduct(g, p, g.getCart().getProducts().get(p), creditCardNumber, buyerAddress);
		}
		return true;
	}

	public static boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress) {
		Map <Product, Integer> prods = new HashMap<Product, Integer>();
		prods.put(product, amount);
		Cart c = new Cart(prods);
		return BlMain.buyProduct(product.getStore(), product, amount) && BlMain.payToStore(product.getStore(), product.getPrice())
				&& BlMain.addPurchaseToHistory(product.getStore(), c) 
				&& BlPurchasePolicy.purchase(product.getPurchasePolicy(), g, product.getPrice(), amount); // true = call for paying system
	}

	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		return hd.updatePrice(price, code);
	}

	public static boolean purchase(PurchaseType ip, Guest g, int price, int amount) {
		return ip.purchase(g, price, amount);
	}

	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price) {
		return BlImmediatelyPurchase.getDiscountedPrice(ip, price);
	}

	//to get price in args
	public static boolean isLotteryDone(LotteryPurchase lp, int price) {
		//	return BlLotteryPurchase.isLotteryDone(lp);
		return BlLotteryPurchase.isLotteryDone(lp, price);
		
	}

	public static void closeCurrentLottery(LotteryPurchase lp) {
		//	BlLotteryPurchase.closeCurrentLottery(lp);
		BlLotteryPurchase.closeCurrentLottery(lp);
	}

		public static void openNewLottery(LotteryPurchase lp, Date newDate) {
			BlLotteryPurchase.openNewLottery(lp, newDate);
		}

}
