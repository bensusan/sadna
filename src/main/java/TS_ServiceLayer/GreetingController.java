package TS_ServiceLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import TS_SharedClasses.*;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import TS_BL.BlMain;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		Gson gson = new Gson();
		String[] args = message.getParamsAsJSON();
		HelloMessage.functionNames f = HelloMessage.functionNames.valueOf(message.getFunctionName());
		Object ret = null;
		try {
			switch (f) {
			case addImmediatelyProduct:
				if (args.length == 3)
					ret = BlMain.addImmediatelyProduct(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class));
				else if (args.length == 4)
					ret = BlMain.addImmediatelyProduct(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class),
							gson.fromJson(args[3], Integer.class));
				break;
			case addLotteryProduct:
				if (args.length == 3)
					ret = BlMain.addLotteryProduct(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class));
				break;
			case removeProductFromCart:
				if (args.length == 2)
					ret = BlMain.removeProductFromCart(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class));
				break;
			case editProductAmount:
				if (args.length == 2)
					ret = BlMain.editProductAmount(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[1], Integer.class));
				break;
			case editProductDiscount:
				if (args.length == 2)
					ret = BlMain.editProductDiscount(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[1], Integer.class));
				break;
			case editProductPrice:
				if (args.length == 2)
					ret = BlMain.editProductPrice(gson.fromJson(args[0], Guest.class),
							gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class));
				break;
			case editCart:
				if (args.length == 2)
					ret = BlMain.editCart(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], Cart.class));
				break;
			case purchaseCart:
				if (args.length == 3)
					ret = BlMain.purchaseCart(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], String.class),
							gson.fromJson(args[2], String.class));
				break;
			case addProductToStore:
				if (args.length == 3) {
					try {
						ret = BlMain.addProductToStore(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.addProductToStore(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Integer.class));
					}
				}
				break;
			case deleteProductFromStore:
				if (args.length == 2) {
					try {
						ret = BlMain.deleteProductFromStore(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], Product.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.deleteProductFromStore(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], Product.class));
					}
				}
				break;
			case updateProductDetails:
				if (args.length == 4) {
					try {
						ret = BlMain.updateProductDetails(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Product.class),
								gson.fromJson(args[3], Integer.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.updateProductDetails(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], Product.class), gson.fromJson(args[2], Product.class),
								gson.fromJson(args[3], Integer.class));
					}
				}
				break;
			case addPolicyToProduct:
				if (args.length == 3) {
					try {
						ret = BlMain.addPolicyToProduct(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], PurchasePolicy.class), gson.fromJson(args[2], Product.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.addPolicyToProduct(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], PurchasePolicy.class), gson.fromJson(args[2], Product.class));
					}
				}
				break;
			case addDiscountToProduct:
				if (args.length == 3) {
					try {
						ret = BlMain.addDiscountToProduct(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], DiscountPolicy.class), gson.fromJson(args[2], Product.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.addDiscountToProduct(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], DiscountPolicy.class), gson.fromJson(args[2], Product.class));
					}
				}
				break;
			case addNewStoreOwner:
				if (args.length == 2) {
					try {
						ret = BlMain.addNewStoreOwner(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], Subscriber.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.addNewStoreOwner(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], Subscriber.class));
					}
				}
				break;
			case addNewManager:
				if (args.length == 2) {
					try {
						ret = BlMain.addNewManager(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], Subscriber.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.addNewManager(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], Subscriber.class));
					}
				}
				break;
			case closeStore:
				if (args.length == 1) {
					try {
						ret = BlMain.closeStore(gson.fromJson(args[0], StoreManager.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.closeStore(gson.fromJson(args[0], StoreOwner.class));
					}
				}
				break;
			case openStore:
				if (args.length == 1) {
					try {
						ret = BlMain.openStore(gson.fromJson(args[0], StoreManager.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.openStore(gson.fromJson(args[0], StoreOwner.class));
					}
				} else if (args.length == 6) {
					Map<Product, Integer> map = new HashMap<Product, Integer>();
					List<Purchase> p = new LinkedList<Purchase>();
					ret = BlMain.openStore(gson.fromJson(args[0], Subscriber.class),
							gson.fromJson(args[1], String.class), gson.fromJson(args[2], Integer.class),
							(Map<Product, Integer>) gson.fromJson(args[3], map.getClass()),
							(List<Purchase>) gson.fromJson(args[4], p.getClass()),
							gson.fromJson(args[5], Boolean.class));
				}
				break;
			case getPurchaseHistory:
				if (args.length == 1) {
					try {
						ret = BlMain.getPurchaseHistory(gson.fromJson(args[0], StoreManager.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.getPurchaseHistory(gson.fromJson(args[0], StoreOwner.class));
					}
				}
				break;
			case removeSubscriber:
				if (args.length == 2)
					ret = BlMain.removeSubscriber(gson.fromJson(args[0], SystemAdministrator.class),
							gson.fromJson(args[1], Subscriber.class));
				break;
			case viewSubscriberHistory:
				if (args.length == 2)
					ret = BlMain.viewSubscriberHistory(gson.fromJson(args[0], SystemAdministrator.class),
							gson.fromJson(args[1], Subscriber.class));
				break;
			case viewStoreHistory:
				if (args.length == 2)
					ret = BlMain.viewStoreHistory(gson.fromJson(args[0], SystemAdministrator.class),
							gson.fromJson(args[1], Store.class));
				break;
			case signUp:
				if (args.length == 7)
					ret = BlMain.signUp(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], String.class),
							gson.fromJson(args[2], String.class), gson.fromJson(args[3], String.class),
							gson.fromJson(args[4], String.class), gson.fromJson(args[5], String.class),
							gson.fromJson(args[6], String.class));
				break;
			case signIn:
				if (args.length == 3) {
					ret = BlMain.signIn(gson.fromJson(args[0], Guest.class), gson.fromJson(args[1], String.class),
							gson.fromJson(args[2], String.class));
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
						ret = BlMain.changeStorePurchasePolicy(gson.fromJson(args[0], StoreManager.class),
								gson.fromJson(args[1], PurchasePolicy.class));
					} catch (JsonSyntaxException j) {
						ret = BlMain.changeStorePurchasePolicy(gson.fromJson(args[0], StoreOwner.class),
								gson.fromJson(args[1], PurchasePolicy.class));
					}
				}
				break;
			default:
				break;
			}
			return ret != null ? new Greeting(ret.toString()) : null;
		} catch (Exception e) {
			return new Greeting(e.getMessage());
		}
	}

}
