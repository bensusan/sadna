package TS_ServiceLayer;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.messaging.handler.annotation.MessageMapping;

import TS_SharedClasses.*;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
			case addProductToStore:
				if (args.length == 7) {
					boolean isOwner = gson.fromJson(args[6], Boolean.class);
					String subName = gson.fromJson(args[0], String.class);
					int storeId = gson.fromJson(args[1], Integer.class);
					Product pToAdd = new Product(gson.fromJson(args[2], String.class), gson.fromJson(args[3], Integer.class), 3, new EmptyPolicy(), new ImmediatelyPurchase());
					int amount = gson.fromJson(args[5], Integer.class);
					String category = gson.fromJson(args[4], String.class);
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerForStorePerUsername(storeId, subName);
						
						ret.setContentAsJson(gson.toJson(BlMain.addProductToStore(so, pToAdd, amount, category)));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(subName, storeId);
						ret.setContentAsJson(gson.toJson(BlMain.addProductToStore(sm, pToAdd, amount, category)));
					}
				}
				break;
			case deleteProductFromStore:
				if (args.length == 4) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					Product toDelete = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class).intValue());
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerForStorePerUsername(gson.fromJson(args[2], Integer.class), gson.fromJson(args[3], String.class));
						
						boolean ans = BlMain.deleteProductFromStore(so, toDelete);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[3], String.class),gson.fromJson(args[2], Integer.class));
						
						boolean ans = BlMain.deleteProductFromStore(sm, toDelete);
						ret.setContentAsJson(gson.toJson(ans));
					}
				}
				break;
			case updateProductDetails:
				if (args.length == 8) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
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
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerForStorePerUsername(gson.fromJson(args[2], Integer.class), gson.fromJson(args[3], String.class));
						boolean ans = BlMain.updateProductDetails(so, oldProduct, newProduct, newAmount, newCategory);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[3], String.class),gson.fromJson(args[2], Integer.class));
						boolean ans = BlMain.updateProductDetails(sm, oldProduct, newProduct, newAmount, newCategory);
						ret.setContentAsJson(gson.toJson(ans));
					}
					
				}
				break;
			case addPolicyToProduct:
				if (args.length == 7) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					Product prodToupdate = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class).intValue());
					String purchacePolicy = gson.fromJson(args[4], String.class);
					PurchasePolicy pp = null;
					if(purchacePolicy.equals("empty")){
						pp = new EmptyPolicy();
					}else if(purchacePolicy.equals("min")){
						int min = gson.fromJson(args[5], Integer.class);
						pp = new MinPolicy(null, min);
					}
					else if(purchacePolicy.equals("max")){
						int max = gson.fromJson(args[6], Integer.class);
						pp = new MaxPolicy(null, max);
					}else if(purchacePolicy.equals("or")){
						int min = gson.fromJson(args[5], Integer.class);
						int max = gson.fromJson(args[6], Integer.class);
						MinPolicy minp = new MinPolicy(null, min);
						MaxPolicy maxp = new MaxPolicy(null, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new OrPolicy(null, listPP);
					}else if(purchacePolicy.equals("and")){
						int min = gson.fromJson(args[5], Integer.class);
						int max = gson.fromJson(args[6], Integer.class);
						MinPolicy minp = new MinPolicy(null, min);
						MaxPolicy maxp = new MaxPolicy(null, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new AndPolicy(null, listPP);
					}
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[2], String.class),
								gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addPolicyToProduct(so, pp , prodToupdate);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class),
								gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addPolicyToProduct(sm, pp , prodToupdate);
						ret.setContentAsJson(gson.toJson(ans));
					}
					
				}
				break;
			case addDiscountToProduct:
				if (args.length == 11) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					Product prodToupdate = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class).intValue());
					String discountType = gson.fromJson(args[4], String.class);
					int precentage = gson.fromJson(args[5], Integer.class);
					Date endDate = Date.valueOf(gson.fromJson(args[6], String.class));
					DiscountPolicy dp = null;
					if(discountType.equals("overt")){
						dp = new OvertDiscount(endDate, precentage);
					}else if(discountType.equals("hidden")){
						int code = gson.fromJson(args[7], Integer.class);
						dp = new HiddenDiscount(code, endDate, precentage);
					}
					String purchacePolicy = gson.fromJson(args[8], String.class);
					PurchasePolicy pp = null;
					if(purchacePolicy.equals("empty")){
						pp = new EmptyPolicy(dp);
					}else if(purchacePolicy.equals("min")){
						int min = gson.fromJson(args[9], Integer.class);
						pp = new MinPolicy(dp, min);
					}
					else if(purchacePolicy.equals("max")){
						int max = gson.fromJson(args[10], Integer.class);
						pp = new MaxPolicy(dp, max);
					}else if(purchacePolicy.equals("or")){
						int min = gson.fromJson(args[9], Integer.class);
						int max = gson.fromJson(args[10], Integer.class);
						MinPolicy minp = new MinPolicy(dp, min);
						MaxPolicy maxp = new MaxPolicy(dp, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new OrPolicy(dp, listPP);
					}else if(purchacePolicy.equals("and")){
						int min = gson.fromJson(args[9], Integer.class);
						int max = gson.fromJson(args[10], Integer.class);
						MinPolicy minp = new MinPolicy(dp, min);
						MaxPolicy maxp = new MaxPolicy(dp, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new AndPolicy(dp, listPP);
					}
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[2], String.class),
								gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addDiscountToProduct(so, pp , prodToupdate);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class),
								gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addDiscountToProduct(sm, pp , prodToupdate);
						ret.setContentAsJson(gson.toJson(ans));
					}
					
				}
				break;
			case addNewStoreOwner:
				if (args.length == 4) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					Subscriber subsToAdd = BlMain.getSubscriberFromUsername(gson.fromJson(args[2], String.class));
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[1], String.class),
																		gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addNewStoreOwner(so,subsToAdd);
						ret.setContentAsJson(gson.toJson(ans));
					}
					else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[1], String.class),
						gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addNewStoreOwner(sm,subsToAdd);
						ret.setContentAsJson(gson.toJson(ans));
					}
				}
				break;
			case addNewManager:
				if (args.length == 5) {
					boolean[] permits = BlMain.getPermitsFromString(gson.fromJson(args[4], String.class));
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					Subscriber subsToAdd = BlMain.getSubscriberFromUsername(gson.fromJson(args[2], String.class));
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[1], String.class),
																		gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addNewManager(so,subsToAdd);
						if(ans){
							StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class), gson.fromJson(args[3], Integer.class));
							sm.setAllPermissions(permits);
						}
						ret.setContentAsJson(gson.toJson(ans));
					}
					else{
						StoreManager so = BlMain.getStoreManagerFromUsername(gson.fromJson(args[1], String.class),
																			gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addNewManager(so,subsToAdd);
						if(ans){
							StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class), gson.fromJson(args[3], Integer.class));
							sm.setAllPermissions(permits);
						}
						ret.setContentAsJson(gson.toJson(ans));
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
					SystemAdministrator sa = BlMain.getSystemAdminFromUsername(gson.fromJson(args[0], String.class));
					Subscriber subs = BlMain.getSubscriberFromUsername(gson.fromJson(args[1], String.class));
					ret.setContentAsJson(gson.toJson(BlMain.removeSubscriber(sa,subs)));
				}
				break;
			case viewSubscriberHistory:
				if (args.length == 2){
					SystemAdministrator sa = BlMain.getSystemAdminFromUsername(gson.fromJson(args[0], String.class));
					Subscriber subs = BlMain.getSubscriberFromUsername(gson.fromJson(args[1], String.class));
					ret.setContentAsJson(gson.toJson(BlMain.viewSubscriberHistory(sa,subs)));
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
				if (args.length == 6) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					String purchacePolicy = gson.fromJson(args[3], String.class);
					PurchasePolicy pp = null;
					if(purchacePolicy.equals("empty")){
						pp = new EmptyPolicy();
					}else if(purchacePolicy.equals("min")){
						int min = gson.fromJson(args[4], Integer.class);
						pp = new MinPolicy(null, min);
					}
					else if(purchacePolicy.equals("max")){
						int max = gson.fromJson(args[5], Integer.class);
						pp = new MaxPolicy(null, max);
					}else if(purchacePolicy.equals("or")){
						int min = gson.fromJson(args[4], Integer.class);
						int max = gson.fromJson(args[5], Integer.class);
						MinPolicy minp = new MinPolicy(null, min);
						MaxPolicy maxp = new MaxPolicy(null, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new OrPolicy(null, listPP);
					}else if(purchacePolicy.equals("and")){
						int min = gson.fromJson(args[4], Integer.class);
						int max = gson.fromJson(args[5], Integer.class);
						MinPolicy minp = new MinPolicy(null, min);
						MaxPolicy maxp = new MaxPolicy(null, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new AndPolicy(null, listPP);
					}
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[1], String.class),
								gson.fromJson(args[2], Integer.class));
						boolean ans = BlMain.changeStorePurchasePolicy(so, pp );
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[1], String.class),
								gson.fromJson(args[2], Integer.class));
						boolean ans = BlMain.changeStorePurchasePolicy(sm, pp );
						ret.setContentAsJson(gson.toJson(ans));
					}
					
				}
				break;
				
			case addToSubCart:
				if (args.length == 4) {
					Subscriber sub = BlMain.getSubscriberFromUsername(gson.fromJson(args[0], String.class));
					int pid = gson.fromJson(args[1], Integer.class);
					Product p = BlMain.getProductFromProdId(pid);
					int amount = gson.fromJson(args[2], Integer.class);
					int discountCode = gson.fromJson(args[3], Integer.class);
					BlMain.addImmediatelyProduct((Guest)sub, p, amount, discountCode);
					
					ret.setContentAsJson(gson.toJson(sub.getCart()));
				}
				break;
			case removeProductFromCart:
				if (args.length == 2){
					Subscriber s = BlMain.getSubscriberFromUsername(gson.fromJson(args[0], String.class));
					Product p = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class));
					BlMain.removeProductFromCart((Guest)s, p);
					ret.setContentAsJson(gson.toJson(s.getCart()));	
				}
				break;
			case purchaseCart:
				if (args.length == 3){
					Subscriber s = BlMain.getSubscriberFromUsername(gson.fromJson(args[0], String.class));
					String creditCard = gson.fromJson(args[1], String.class);
					String address = gson.fromJson(args[2], String.class);
					BlMain.purchaseCart((Guest)s,creditCard,address);
					ret.setContentAsJson(gson.toJson(s.getCart()));
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
			case getProduct:
				if(args.length == 1){
					int pid = gson.fromJson(args[0], Integer.class);
					
					List<Product> pList = BlMain.getAllProducts();
					for (Product p : pList) {
						if(p.getId() == pid){
							ret.setContentAsJson(gson.toJson(p));
							break;
					}
				}}
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
			case changeProductType:
				if(args.length == 6){
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					Product prodToChange = BlMain.getProductFromProdId(gson.fromJson(args[1], Integer.class));
					String purType = gson.fromJson(args[3], String.class);
					PurchaseType newType;
					int storeId = gson.fromJson(args[5], Integer.class);
					if(purType.equals("immedietly"))
						newType = new ImmediatelyPurchase();
					else{
						Date newDate = Date.valueOf(gson.fromJson(args[4], String.class));
						newType = new LotteryPurchase(newDate);
					}
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[2], String.class),
														gson.fromJson(args[5], Integer.class));
						boolean ans = BlMain.changeProductType(so, newType, prodToChange);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager so = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class),
																			gson.fromJson(args[5], Integer.class));
						boolean ans = BlMain.changeProductType(so, newType, prodToChange);
						ret.setContentAsJson(gson.toJson(ans));
					}
				}
				break;
			case addDiscountToCategoryStore:
				if (args.length == 11) {
					boolean isOwner = gson.fromJson(args[0], Boolean.class);
					String Category = gson.fromJson(args[1], String.class);
					String discountType = gson.fromJson(args[4], String.class);
					int precentage = gson.fromJson(args[5], Integer.class);
					Date endDate = Date.valueOf(gson.fromJson(args[6], String.class));
					DiscountPolicy dp = null;
					if(discountType.equals("overt")){
						dp = new OvertDiscount(endDate, precentage);
					}else if(discountType.equals("hidden")){
						int code = gson.fromJson(args[7], Integer.class);
						dp = new HiddenDiscount(code, endDate, precentage);
					}
					String purchacePolicy = gson.fromJson(args[8], String.class);
					PurchasePolicy pp = null;
					if(purchacePolicy.equals("empty")){
						pp = new EmptyPolicy(dp);
					}else if(purchacePolicy.equals("min")){
						int min = gson.fromJson(args[9], Integer.class);
						pp = new MinPolicy(dp, min);
					}
					else if(purchacePolicy.equals("max")){
						int max = gson.fromJson(args[10], Integer.class);
						pp = new MaxPolicy(dp, max);
					}else if(purchacePolicy.equals("or")){
						int min = gson.fromJson(args[9], Integer.class);
						int max = gson.fromJson(args[10], Integer.class);
						MinPolicy minp = new MinPolicy(dp, min);
						MaxPolicy maxp = new MaxPolicy(dp, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new OrPolicy(dp, listPP);
					}else if(purchacePolicy.equals("and")){
						int min = gson.fromJson(args[9], Integer.class);
						int max = gson.fromJson(args[10], Integer.class);
						MinPolicy minp = new MinPolicy(dp, min);
						MaxPolicy maxp = new MaxPolicy(dp, max);
						List<PurchasePolicy> listPP = new ArrayList<PurchasePolicy>();
						listPP.add(minp);
						listPP.add(maxp);
						pp = new AndPolicy(dp, listPP);
					}
					if(isOwner){
						StoreOwner so = BlMain.getStoreOwnerFromUsername(gson.fromJson(args[2], String.class),
								gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addDiscountToCategoryStore(so, pp , Category);
						ret.setContentAsJson(gson.toJson(ans));
					}else{
						StoreManager sm = BlMain.getStoreManagerFromUsername(gson.fromJson(args[2], String.class),
								gson.fromJson(args[3], Integer.class));
						boolean ans = BlMain.addDiscountToCategoryStore(sm, pp , Category);
						ret.setContentAsJson(gson.toJson(ans));
					}
					
				}
				break;
				
			default:
				throw new Exception("NO SUCH FUNCTION");
			}
		} catch (Exception e) {
			ret.setException();
			ret.setContentAsJson(gson.toJson(e.getMessage()));
		}
		
		SimpMessagingTemplate simpMessagingTemplate;
		
		return ret;
	}
}

