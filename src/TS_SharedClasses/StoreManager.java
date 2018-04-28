package TS_SharedClasses;
import java.util.Arrays;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreManager other = (StoreManager) obj;
		if (!Arrays.equals(premisions, other.premisions))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		return true;
	}
	
	
}
