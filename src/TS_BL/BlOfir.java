package TS_BL;

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

	public static boolean purchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
		return BlMain.puchaseCart(c, creditCardNumber, buyerAddress);
	}

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
		return BlMain.updatePrice(dp, price);
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
		return BlMain.puchaseCart(g, creditCardNumber, buyerAddress);
	}

	public static boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress) {
		return BlMain.pruchaseProduct(g, product, amount, creditCardNumber, buyerAddress);
	}

	public static int updatePrice(HiddenDiscount hd, int price, int code) {
		return BlMain.updatePrice(hd, price, code);
	}

	public static boolean purchase(PurchaseType ip, Guest g, int price, int amount) {
		if (ip instanceof ImmediatelyPurchase) {
			int priceAfterDiscount = getDiscountedPrice((ImmediatelyPurchase) ip, price);
			return BlMain.purchase(ip, g, priceAfterDiscount, amount);
		}
		return BlMain.purchase(ip, g, price, amount);
	}

	public static int getDiscountedPrice(ImmediatelyPurchase ip, int price) {
		return BlMain.getDiscountedPrice(ip, price);
	}

	public static boolean isLotteryDone(LotteryPurchase lp) {
		return BlMain.isLotteryDone(lp);
	}

	public static void closeCurrentLottery(LotteryPurchase lp) {
		BlMain.closeCurrentLottery(lp);
	}

	public static void openNewLottery(LotteryPurchase lp) {
		BlMain.openNewLottery(lp);
	}

}
