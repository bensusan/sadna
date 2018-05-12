package TS_ServiceLayer;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import TS_SharedClasses.Cart;
import TS_SharedClasses.Category;import TS_SharedClasses.DiscountPolicy;
import TS_SharedClasses.Guest;
import TS_SharedClasses.ImmediatelyPurchase;
import TS_SharedClasses.MaxPolicy;
import TS_SharedClasses.OrPolicy;
import TS_SharedClasses.OvertDiscount;
import TS_SharedClasses.Product;
import TS_SharedClasses.Store;

public class testMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		ObjectMapper mapper = new ObjectMapper();
//		HelloMessage msg = new HelloMessage(, new String[2], 2, "a", "b");
//		
//		try {
//			String jsonInString = mapper.writeValueAsString(msg);
//			System.out.println(jsonInString);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Gson g = new Gson();
//		Product p = new Product("p", 2, 2, new Category("s"), new MaxPolicy(new OvertDiscount(), 10), new ImmediatelyPurchase());
//		HashMap<Product, Integer> h = new HashMap<Product, Integer>();
//		h.put(p, 2);
		
//		HashMap<Store, Map<Product,Integer>> h2 = new HashMap<Store, Map<Product,Integer>>();
//		h2.put(new Store("a", "a", "a", 3, h, null, true), h);
//		System.out.println(g.toJson(h2));
//		System.out.println(g.toJson(h));


	}

}
