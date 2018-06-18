package ExternalSystemTests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class MoneyCollectionTest {
	
	@Test
	public void MoneyCollectionTestSuccess() {
		try{
			URL url = new URL("http://moneyCollectionSys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validCreditCardNumber = "123456798132";
			int validPrice = 123456;
	        params.put("price", validPrice);
	        params.put("creditCardNumber", validCreditCardNumber);
	        
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
	public void MoneyCollectionTestInvalidCredictCard() {
		try{
			URL url = new URL("http://moneyCollectionSys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validCreditCardNumber = "123456aa8132";
			int validPrice = 123456;
	        params.put("price", validPrice);
	        params.put("creditCardNumber", validCreditCardNumber);
	        
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
	public void MoneyCollectionTestCurrptConnection() {
		try{
			URL url = new URL("http://moneyCollectionSys.net/");
			Map<String,Object> params = new LinkedHashMap();
			String validCreditCardNumber = "1234561238132";
			int validPrice = 123456;
	        params.put("price", validPrice);
	        params.put("creditCardNumber", validCreditCardNumber);
	        
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
