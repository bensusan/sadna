package FutureClasses;

import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import TS_BL.BlMain;
import TS_SharedClasses.*;

@Controller
public class GreetingController {


    @MessageMapping("/TS_ServiceLayer")
    @SendTo("/topic/greetings")
    public Object greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        
        List<Object> args = message.getArgs();
        
        functionNames f = (functionNames)args.get(0);
//        functionNames f = functionNames.signIn; 
        Object ret = null;
        switch(f){
        	case addImmediatelyProduct:
        		if(args.size() == 4)	
        			ret = BlMain.addImmediatelyProduct((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(3));
        		else if(args.size() == 5)	
        			ret = BlMain.addImmediatelyProduct((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(3), (Integer)args.get(4));
        		break;
        	case addLotteryProduct:
        		if(args.size() == 4)
        			ret = BlMain.addLotteryProduct((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(3));
        		break;
        	case removeProductFromCart:
        		if(args.size() == 3)
        			ret = BlMain.removeProductFromCart((Guest)args.get(1), (Product)args.get(2));
        		break;
        	case editProductAmount:
        		if(args.size() == 3)
        			ret = BlMain.editProductAmount((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(2));
        		break;
        	case editProductDiscount:
        		if(args.size() == 3)
        			ret = BlMain.editProductDiscount((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(2));
        		break;
        	case editProductPrice:
        		if(args.size() == 3)
        			ret = BlMain.editProductPrice((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(3));
        		break;
        	case editCart:
        		if(args.size() == 3)
        			ret = BlMain.editCart((Guest)args.get(1), (Cart)args.get(2));
        		break;
        	case purchaseCart:
        		if(args.size() == 4)
        			ret = BlMain.purchaseCart((Guest)args.get(1), (String)args.get(2), (String)args.get(3));
        		break;
        	case addProductToStore:
        		if(args.size() == 4){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.addProductToStore((StoreManager)args.get(1), (Product)args.get(2), (Integer)args.get(3));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.addProductToStore((StoreOwner)args.get(1), (Product)args.get(2), (Integer)args.get(3));
        		}
        		break;
        	case deleteProductFromStore:
        		if(args.size() == 3){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.deleteProductFromStore((StoreManager)args.get(1), (Product)args.get(2));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.deleteProductFromStore((StoreOwner)args.get(1), (Product)args.get(2));
        		}
        		break;
        	case updateProductDetails:
        		if(args.size() == 5){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.updateProductDetails((StoreManager)args.get(1), (Product)args.get(2), (Product)args.get(3), (Integer)args.get(4));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.updateProductDetails((StoreOwner)args.get(1), (Product)args.get(2), (Product)args.get(3), (Integer)args.get(4));
        		}
        		break;
        	case addPolicyToProduct:
        		if(args.size() == 4){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.addPolicyToProduct((StoreManager)args.get(1), (PurchasePolicy)args.get(2), (Product)args.get(3));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.addPolicyToProduct((StoreOwner)args.get(1), (PurchasePolicy)args.get(2), (Product)args.get(3));
        		}
        		break;
        	case addDiscountToProduct:
        		if(args.size() == 4){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.addDiscountToProduct((StoreManager)args.get(1), (DiscountPolicy)args.get(2), (Product)args.get(3));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.addDiscountToProduct((StoreOwner)args.get(1), (DiscountPolicy)args.get(2), (Product)args.get(3));
        		}
        		break;
        	case addNewStoreOwner:
        		if(args.size() == 3){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.addNewStoreOwner((StoreManager)args.get(1), (Subscriber)args.get(2));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.addNewStoreOwner((StoreOwner)args.get(1), (Subscriber)args.get(2));
        		}
        		break;
        	case addNewManager:
        		if(args.size() == 3){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.addNewManager((StoreManager)args.get(1), (Subscriber)args.get(2));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.addNewManager((StoreOwner)args.get(1), (Subscriber)args.get(2));
        		}
        		break;
        	case closeStore:
        		if(args.size() == 2){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.closeStore((StoreManager)args.get(1));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.closeStore((StoreOwner)args.get(1));
        		}
        		break;
        	case openStore:
        		if(args.size() == 2){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.openStore((StoreManager)args.get(1));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.openStore((StoreOwner)args.get(1));
        		}
        		else if(args.size() == 7)
        			ret = BlMain.openStore((Subscriber)args.get(1), (String)args.get(2), (Integer)args.get(3), (Map<Product,Integer>)args.get(4), (List<Purchase>)args.get(5), (Boolean)args.get(6));
        		break;
        	case getPurchaseHistory:
        		if(args.size() == 2){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.getPurchaseHistory((StoreManager)args.get(1));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.getPurchaseHistory((StoreOwner)args.get(1));
        		}
        		break;
        	case removeSubscriber:
        		if(args.size() == 3)
        			ret = BlMain.removeSubscriber((SystemAdministrator)args.get(1), (Subscriber)args.get(2));
        		break;
        	case viewSubscriberHistory:
        		if(args.size() == 3)
        			ret = BlMain.viewSubscriberHistory((SystemAdministrator)args.get(1), (Subscriber)args.get(2));
        		break;
        	case viewStoreHistory:
        		if(args.size() == 3)
        			ret = BlMain.viewStoreHistory((SystemAdministrator)args.get(1), (Store)args.get(2));
        		break;
        	case signUp:
        		if(args.size() == 8)
        			ret = BlMain.signUp((Guest)args.get(1), (String)args.get(2), (String)args.get(3), (String)args.get(4), (String)args.get(5), (String)args.get(6), (String)args.get(7));
        		break;
        	case signIn:
        		if(args.size() == 4)
        			ret = BlMain.signIn((Guest)args.get(1), (String)args.get(2), (String)args.get(3));
        		break;
        	case expiredProducts:
        		if(args.size() == 2){
        			if(args.get(1) instanceof StoreManager)
        				BlMain.expiredProducts((StoreManager)args.get(1));
        			else if(args.get(1) instanceof StoreOwner)
        				BlMain.expiredProducts((StoreOwner)args.get(1));
        		}
        		break;
        	case changeStorePurchasePolicy:
        		if(args.size() == 3){
        			if(args.get(1) instanceof StoreManager)
        				ret = BlMain.changeStorePurchasePolicy((StoreManager)args.get(1), (PurchasePolicy)args.get(2));
        			else if(args.get(1) instanceof StoreOwner)
        				ret = BlMain.changeStorePurchasePolicy((StoreOwner)args.get(1), (PurchasePolicy)args.get(2));
        		}
        		break;
        	default:
        		break;
        }
        return ret;
    }

}


