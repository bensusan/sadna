package ExternalSystemTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import TS_SharedClasses.EmptyPolicy;
import TS_SharedClasses.ImmediatelyPurchase;
import TS_SharedClasses.Product;

public class SupplySystemTest {

	@Test
	public void MoneyCollectionTestSuccess() {
		try{
			URL url = new URL("http://SupplySys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validAddress = "Jerusalem st. 1/9 Tel Aviv";
			Product p1 = new Product("p1", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
			Product p2 = new Product("p2", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
			Product p3 = new Product("p3", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
	        params.put("product1", p1);
	        params.put("product2", p2);
	        params.put("product3", p3);
	        params.put("address", validAddress);
	        
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        
	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	        
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;)
	            sb.append((char)c);
	        String response = sb.toString();
	        
	        Boolean success = response.toLowerCase().contains("success");
	        assertTrue(success);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void SupplyTestInvalidAddress() {
		try{
			URL url = new URL("http://SupplySys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validAddress = "Not Exist Address";
			Product p1 = new Product("p1", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
			Product p2 = new Product("p2", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
			Product p3 = new Product("p3", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
	        params.put("product1", p1);
	        params.put("product2", p2);
	        params.put("product3", p3);
	        params.put("address", validAddress);
	        
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        
	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	        
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;)
	            sb.append((char)c);
	        String response = sb.toString();
	        
	        Boolean success = response.toLowerCase().contains("success");
	        assertFalse(success);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void SupplyTestInvalidProducts() {
		try{
			URL url = new URL("http://SupplySys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validAddress = "Not Exist Address";
	        params.put("product1", null);
	        params.put("product2", null);
	        params.put("product3", null);
	        params.put("address", validAddress);
	        
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        
	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	        
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;)
	            sb.append((char)c);
	        String response = sb.toString();
	        
	        Boolean success = response.toLowerCase().contains("success");
	        assertFalse(success);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void SupplyTestCurrptConnection() {
		try{
			URL url = new URL("http://SupplySys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validAddress = "Jerusalem st. 1/9 Tel Aviv";
			Product p1 = new Product("p1", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
			Product p2 = new Product("p2", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
			Product p3 = new Product("p3", 10, 3, new EmptyPolicy(), new ImmediatelyPurchase());
	        params.put("product1", p1);
	        params.put("product2", p2);
	        params.put("product3", p3);
	        params.put("address", validAddress);
	        
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.disconnect();
	        conn.getOutputStream().write(postDataBytes);
	        
	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	        
	        StringBuilder sb = new StringBuilder();
	        for (int c; (c = in.read()) >= 0;)
	            sb.append((char)c);
	        String response = sb.toString();
	        
	        Boolean success = response.toLowerCase().contains("success");
	        assertFalse(success);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
}
