package TS_BL;

import java.util.List;
import java.util.Map;

import TS_DAL.DalInteface;
import TS_DAL.DalMain;
import TS_SharedClasses.*;

public class BlMain implements BlInterface {

	static DalInteface dal = new DalMain();
	
	@Override
	public boolean puchaseCart(Cart c, int creditCardNumber, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProduct(Cart c, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeProduct(Cart c, Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editProduct(Cart c, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editCart(Cart c, Map<Product, Integer> newCart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int updatePrice(DiscountPolicy dp, int price) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addProductToCart(Guest g, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeProductFromCart(Guest g, Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editProductInCart(Guest g, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editCart(Guest g, Map<Product, Integer> newCart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puchaseCart(Guest g, int creditCardNumber, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pruchaseProduct(Guest g, Product product, int amount, int creditCardNumber, String buyerAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int updatePrice(HiddenDiscount hd, int price, int code) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean purchase(PurchaseType ip, Guest g, int price) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDiscountedPrice(ImmediatelyPurchase ip, int price) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isLotteryDone(LotteryPurchase lp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeCurrentLottery(LotteryPurchase lp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openNewLottery(LotteryPurchase lp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean purchase(Product p, Guest g, int price) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean purchase(PurchasePolicy pp, Guest g, int price) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPurchase(PurchasePolicy pp, Guest g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkInStock(Store s, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPurchaseToHistory(Store s, Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buyProduct(Store s, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stockUpdate(Store s, Product p, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProductToStore(StoreManager sm, Product product, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProductFromStore(StoreManager sm, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProductDetails(StoreManager sm, Product newProduct, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPolicyToProduct(StoreManager sm, PurchasePolicy policy, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addDiscountToProduct(StoreManager sm, DiscountPolicy discount, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addNewStoreOwner(StoreManager sm, StoreOwner owner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addNewManager(StoreManager oldMan, StoreManager newMan) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeStore(StoreManager sm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean openStore(StoreManager sm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cart> getPurchaseHistory(StoreManager sm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addProductToStore(StoreOwner so, Product product, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProductFromStore(StoreOwner so, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProductDetails(StoreOwner so, Product newProduct, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPolicyToProduct(StoreOwner so, PurchasePolicy policy, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addDiscountToProduct(StoreOwner so, DiscountPolicy discount, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addNewStoreOwner(StoreOwner oldSo, StoreOwner newSo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addNewManager(StoreOwner so, StoreManager manager) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeStore(StoreOwner so) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean openStore(StoreOwner so) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cart> getPurchaseHistory(StoreOwner so) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store openStore(Subscriber sub, String storeName, String Description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPurchaseToHistory(Subscriber sub, Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addOwner(Subscriber sub, StoreOwner owner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addManager(Subscriber sub, StoreManager manager) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOwner(Subscriber sub, StoreOwner owner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteManager(Subscriber sub, StoreManager manager) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeSubscriber(SystemAdministrator sa, Subscriber s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cart> viewSubscriberHistory(SystemAdministrator sa, Subscriber s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> viewStoreHistory(SystemAdministrator sa, Store store) {
		// TODO Auto-generated method stub
		return null;
	}

}
