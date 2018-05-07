package TS_ServiceLayer;

import java.util.List;

public class HelloMessage {

	public static enum functionNames {addImmediatelyProduct, addLotteryProduct, removeProductFromCart, editProductAmount, editProductDiscount, editProductPrice, editCart, purchaseCart, addProductToStore, deleteProductFromStore, updateProductDetails, addPolicyToProduct, addDiscountToProduct, addNewStoreOwner, addNewManager, closeStore, openStore, getPurchaseHistory, removeSubscriber, viewSubscriberHistory, viewStoreHistory, signUp, signIn, expiredProducts, changeStorePurchasePolicy}
	
	//assume first argument is on of the enums.
	private List<Object> args;

    public HelloMessage(List<Object> args) {
    	this.args = args;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}
