package TS_SharedClasses;import java.security.acl.Permission;

import TS_BL.BlMain;

public class StoreManager{
	private boolean[] premisions;
	private Store store;
	
	public StoreManager(boolean[] premisions, Store store) {
		super();
		this.premisions = premisions;
		if(this.premisions == null)
			this.premisions = new boolean[BlMain.NUM_OF_PERMISSIONS];
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
