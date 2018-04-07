package TS_SharedClasses;

public class StoreManager{
	private boolean[] premisions;
	private Store store;
	
	public StoreManager(boolean[] premisions, Store store) {
		super();
		this.premisions = premisions;
		this.store = store;
	}
	
	public boolean[] getPremisions() {
		return premisions;
	}
	
	public void setPremisions(boolean[] premisions) {
		this.premisions = premisions;
	}
	
	public Store getStore() {
		return store;
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
}
