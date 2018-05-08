package TS_ServiceLayer;

public class HelloMessage {

	public static enum functionNames {addImmediatelyProduct, addLotteryProduct, removeProductFromCart, editProductAmount, editProductDiscount, editProductPrice, editCart, purchaseCart, addProductToStore, deleteProductFromStore, updateProductDetails, addPolicyToProduct, addDiscountToProduct, addNewStoreOwner, addNewManager, closeStore, openStore, getPurchaseHistory, removeSubscriber, viewSubscriberHistory, viewStoreHistory, signUp, signIn, expiredProducts, changeStorePurchasePolicy};
	
	private String functionName;
	private String[] paramsAsJSON;
    
    public HelloMessage() {
    }

    public HelloMessage(String functionName, String[] paramsAsJSON) {
    	this.functionName = functionName;
    	this.paramsAsJSON = paramsAsJSON;
    }

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String[] getParamsAsJSON() {
		return paramsAsJSON;
	}

	public void setParamsAsJSON(String[] paramsAsJSON) {
		this.paramsAsJSON = paramsAsJSON;
	}
    
    
}
