package unitTests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import TS_BL.BlMain;
import TS_SharedClasses.Guest;
import TS_SharedClasses.Purchase;
import TS_SharedClasses.StoreManager;
import TS_SharedClasses.StoreOwner;
import TS_SharedClasses.Subscriber;
import TS_SharedClasses.SystemAdministrator;

public class systemAdministratorTests {

	@Test
	public void testRemoveSubscriber() {
		SystemAdministrator amit=new SystemAdministrator("amit123", "amit123", "amit kaplan", "hatamar 3 Modiin", "0545818680", "1111111111111111",new LinkedList<Purchase>(),new LinkedList<StoreManager>(),new LinkedList<StoreOwner>());
		Subscriber ofir=null;
		assertFalse(BlMain.removeSubscriber(amit, ofir));
		ofir=BlMain.signUp(new Guest(), "ofir123", "ofir123", "ofir imas", "pach zevel 1 Ashdod", "0584792829", "2222222222222222");
		assertFalse(BlMain.removeSubscriber(null, ofir));
		assertTrue(BlMain.removeSubscriber(amit, ofir));
	}

}
