package TS_ServiceLayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.messaging.handler.annotation.MessageMapping;
import TS_SharedClasses.*;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import TS_BL.BlMain;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		Gson gson = new Gson();		
		
		String[] args = message.getParamsAsJSON();
		String fName = message.getFunctionName();
		HelloMessage.functionNames f = HelloMessage.functionNames.valueOf(fName);
		Greeting ret = new Greeting(message.getPageName(), fName);
		try {
			switch (f) {
			case addImmediatelyProduct:
				if (args.length == 3) {
					ret.setContentAsJson(gson.toJson(BlMain.addImmediatelyProduct(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class))));
				} else if (args.length == 4){
					ret.setContentAsJson(gson.toJson(BlMain.addImmediatelyProduct(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class),
							gson.fromJson(args[3], Integer.class))));
				}
				break;
			case addLotteryProduct:
				if (args.length == 3){
					ret.setContentAsJson(gson.toJson(BlMain.addLotteryProduct(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class))));
				}
				break;
			case removeProductFromCart:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.removeProductFromCart(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class))));
				}
				break;
			case editProductAmount:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.editProductAmount(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[1], Integer.class))));
				}
				break;
			case editProductDiscount:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.editProductDiscount(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[1], Integer.class))));
				}
				break;
			case editProductPrice:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.editProductPrice(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class))));
				}
				break;
			case editCart:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.editCart(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], Cart.class))));
				}
				break;
			case purchaseCart:
				if (args.length == 3){
					ret.setContentAsJson(gson.toJson(BlMain.purchaseCart(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], String.class),
							gson.fromJson(args[2], String.class))));
				}
				break;
			case addProductToStore:
				if (args.length == 6) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.addProductToStore(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class),
								gson.fromJson(args[3], String.class))));
					} catch (JsonSyntaxException j) {
						Subscriber sub = gson.fromJson(args[0], Subscriber.class);
						Store store = gson.fromJson(args[1], Store.class);
						StoreOwner so = BlMain.getStoreOwnerForStorePerUsername(store.getStoreId(), sub.getUsername());
						Product pToAdd = new Product(gson.fromJson(args[2], String.class), gson.fromJson(args[3], Integer.class), 3, new EmptyPolicy(), new ImmediatelyPurchase());
						int amount = gson.fromJson(args[5], Integer.class);
						String category = gson.fromJson(args[4], String.class);
						ret.setContentAsJson(gson.toJson(BlMain.addProductToStore(so, pToAdd, amount, category)));
					}
				}
				break;
			case deleteProductFromStore:
				if (args.length == 4) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerForStorePerUsername(gson.fromJson(args[2], Integer.class), gson.fromJson(args[3], String.class));
						Product toDelete = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class).intValue());
						boolean ans = BlMain.deleteProductFromStore(so, toDelete);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						
					}
				}
				break;
			case updateProductDetails:
				if (args.length == 8) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerForStorePerUsername(gson.fromJson(args[2], Integer.class), gson.fromJson(args[3], String.class));
						Product oldProduct = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class).intValue());
						int newAmount = BlMain.getAmountFromProdId(gson.fromJson(args[1], Integer.class).intValue());
						
						String newName = gson.fromJson(args[4], String.class);
						if(newName == null)
							newName = oldProduct.getName();
						
						int newPrice;
						String checkval = gson.fromJson(args[5], String.class);
						if(checkval == null)
							newPrice = oldProduct.getPrice();
						else
							newPrice = gson.fromJson(args[5], Integer.class);
						
						String newCategory = gson.fromJson(args[6], String.class);
						if(newCategory == null)
							newCategory = oldProduct.getCategory().getName();
						
						checkval = gson.fromJson(args[7], String.class);
						if(checkval != null)
							newAmount = gson.fromJson(args[7], Integer.class);
						
						
						
						Product newProduct = new Product(newName, newPrice, oldProduct.getGrading(), oldProduct.getPurchasePolicy(), oldProduct.getType());
						
						boolean ans = BlMain.updateProductDetails(so, oldProduct, newProduct, newAmount, newCategory);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						
					}
					
				}
				break;
			case addPolicyToProduct:
				if (args.length == 3) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.addPolicyToProduct(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], PurchasePolicy.class), gson.fromJson(args[2], Product.class))));
					} catch (JsonSyntaxException j) {
						ret.setContentAsJson(gson.toJson(BlMain.addPolicyToProduct(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], PurchasePolicy.class), gson.fromJson(args[2], Product.class))));
					}
				}
				break;
			case addDiscountToProduct:
				if (args.length == 4) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.addDiscountToProduct(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], PurchasePolicy.class), gson.fromJson(args[2], Product.class))));
					} catch (JsonSyntaxException j) {
						ret.setContentAsJson(gson.toJson(BlMain.addDiscountToProduct(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], PurchasePolicy.class), gson.fromJson(args[2], Product.class))));
					}
				}
				break;
			case addNewStoreOwner:
				if (args.length == 4) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[1], String.class),
																		gson.fromJson(args[3], Integer.class));
						Subscriber subsToAdd = BlMain.getSubscriberFromUsername(gson.fromJson(args[2], String.class));
						boolean ans = BlMain.addNewStoreOwner(so,subsToAdd);
						ret.setContentAsJson(gson.toJson(ans));
					}
					else{
						
					}
				}
				break;
			case addNewManager:
				if (args.length == 5) {
					boolean[] permits = BlMain.getPermitsFromString(gson.fromJson(args[4], String.class));
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[1], String.class),
																		gson.fromJson(args[3], Integer.class));
						Subscriber subsToAdd = BlMain.getSubscriberFromUsername(gson.fromJson(args[2], String.class));
						boolean ans = BlMain.addNewManager(so,subsToAdd);
						if(ans){
							StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class), gson.fromJson(args[3], Integer.class));
							sm.setAllPermissions(permits);
						}
						
						ret.setContentAsJson(gson.toJson(ans));
					}
					else{
						
					}
				}
				break;
			case closeStore:
				if (args.length == 1) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.closeStore(gson.fromJson(args[0], StoreManager.class))));
					} catch (JsonSyntaxException j) {
						ret.setContentAsJson(gson.toJson(BlMain.closeStore(gson.fromJson(args[0], StoreOwner.class))));
					}
				}
				break;
			case openStore:
				if (args.length == 1) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.openStore(gson.fromJson(args[0], StoreManager.class))));
					} catch (JsonSyntaxException j) {
						ret.setContentAsJson(gson.toJson(BlMain.openStore(gson.fromJson(args[0], StoreOwner.class))));
					}
				} else if (args.length == 2) {
					Subscriber sub = gson.fromJson(args[0], Subscriber.class);
					String str = gson.fromJson(args[1], String.class);
					Store s = BlMain.openStore(sub,str, 3, true);
					String ans = gson.toJson(s);
					ret.setContentAsJson(ans);
				}
				break;
			case getPurchaseHistory:
				if (args.length == 1) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.getPurchaseHistory(gson.fromJson(args[0], StoreManager.class))));
					} catch (JsonSyntaxException j) {
						ret.setContentAsJson(gson.toJson(BlMain.getPurchaseHistory(gson.fromJson(args[0], StoreOwner.class))));
					}
				}
				break;
			case removeSubscriber:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.removeSubscriber(gson.fromJson(args[0], SystemAdministrator.class),
							gson.fromJson(args[1], Subscriber.class))));
				}
				break;
			case viewSubscriberHistory:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.viewSubscriberHistory(gson.fromJson(args[0], SystemAdministrator.class),
							gson.fromJson(args[1], Subscriber.class))));
				}
				break;
			case viewStoreHistory:
				if (args.length == 2){
					ret.setContentAsJson(gson.toJson(BlMain.viewStoreHistory(gson.fromJson(args[0], SystemAdministrator.class),
							gson.fromJson(args[1], Store.class))));
				}
				break;
			case signUp:
				if (args.length == 7){
					Subscriber subs = BlMain.signUp(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], String.class),
							gson.fromJson(args[2], String.class), gson.fromJson(args[3], String.class),
							gson.fromJson(args[4], String.class), gson.fromJson(args[5], String.class),
							gson.fromJson(args[6], String.class));
					ret.setContentAsJson(gson.toJson(subs));
				}
				break;
			case signIn:
				if (args.length == 3) {
					ret.setContentAsJson(gson.toJson(BlMain.signIn(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], String.class),
							gson.fromJson(args[2], String.class))));
				}
				break;
			case expiredProducts:
				if (args.length == 1) {
					try {
						BlMain.expiredProducts(gson.fromJson(args[0], StoreManager.class));
					} catch (JsonSyntaxException j) {
						BlMain.expiredProducts(gson.fromJson(args[0], StoreOwner.class));
					}
				}
				break;
			case changeStorePurchasePolicy:
				if (args.length == 2) {
					try {
						ret.setContentAsJson(gson.toJson(BlMain.changeStorePurchasePolicy(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], PurchasePolicy.class))));
					} catch (JsonSyntaxException j) {
						ret.setContentAsJson(gson.toJson(BlMain.changeStorePurchasePolicy(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], PurchasePolicy.class))));
					}
				}
				break;
			case getAllStoresWithThierProductsAndAmounts:
				if(args.length == 0)
					ret.setContentAsJson(gson.toJson(BlMain.getAllStoresWithThierProductsAndAmounts()));
				break;
			case getAllStores:
				if(args.length == 0)
					ret.setContentAsJson(gson.toJson(BlMain.getAllStores()));
				break;
			case getAllSubscriberStores:
				if(args.length == 1)
					ret.setContentAsJson(gson.toJson(BlMain.getAllSubscriberStores(gson.fromJson(args[0], Subscriber.class))));
				break;
				
			case getSubscriberFromUsername:
				if(args.length == 1)
					ret.setContentAsJson(gson.toJson(BlMain.getSubscriberFromUsername(gson.fromJson(args[0], String.class))));
				break;
			case getProductAndAmountPerStoreId:
				if(args.length == 1){
					Map<Product, Integer> map = BlMain.getProductAndAmountPerStoreId(gson.fromJson(args[0], Integer.class));
					ret.setContentAsJson(gson.toJson(map));
				}
				break;
			case getAllSubscribersWithPotential:
				if(args.length == 0){
					List<Subscriber> a = BlMain.getAllSubscribersWithPotential();
					ret.setContentAsJson(gson.toJson(a));
				}
				break;
				
			default:
				throw new Exception("NO SUCH FUNCTION");
			}
		} catch (Exception e) {
			ret.setException();
			ret.setContentAsJson(e.getMessage());
		}
		return ret;
	}
}

