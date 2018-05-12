package TS_ServiceLayer;

public class HelloMessage {

	public static enum functionNames {addImmediatelyProduct, addLotteryProduct, removeProductFromCart, editProductAmount, editProductDiscount, editProductPrice, editCart, purchaseCart, addProductToStore, deleteProductFromStore, updateProductDetails, addPolicyToProduct, addDiscountToProduct, addNewStoreOwner, addNewManager, closeStore, openStore, getPurchaseHistory, removeSubscriber, viewSubscriberHistory, viewStoreHistory, signUp, signIn, expiredProducts, changeStorePurchasePolicy, getAllStoresWithThierProductsAndAmounts, getAllStores, getAllSubscriberStores};
	
	private String pageName;
	private String functionName;
	private String[] paramsAsJSON;
    
    public HelloMessage() {
    }

    public HelloMessage(String pageName, String functionName, String[] paramsAsJSON) {
    	this.pageName = pageName;
    	this.functionName = functionName;
    	this.paramsAsJSON = paramsAsJSON;
    }

    public String getPageName() {
		return pageName;
	}
    
    public void setPageName(String pageName) {
		this.pageName = pageName;
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
