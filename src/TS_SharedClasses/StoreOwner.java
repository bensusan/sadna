package TS_SharedClasses;

public class StoreOwner {
	
	private Store store;
	
	public StoreOwner(Store store) {
		super();
		this.store = store;
	}
	
	public Store getStore() {
		return store;
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
}
