package AcceptanceTests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import TS_BL.BlMain;
import TS_SharedClasses.*;

public class GuestAT {

	//1.1
	//could not test specificlly entry to system there is no entry to system yet
	
	//1.2
	@Test
	public void testGuestToSubscriber(){
		Guest g = new Guest();
		//good case - add new sub
		Subscriber s1 = BlMain.signUp(g, "sagivmap", "123123", "Sagiv Map", "Jerusalem", 88123132, "123456");
		assertNotNull(s1);
		
		//sad case - existing username
		Subscriber s2 = BlMain.signUp(g, "sagivmap", "45678999", "Sagiv Mag", "Be'er Sheva", 88456456, "654321");
		assertNull(s2);
	}

}
