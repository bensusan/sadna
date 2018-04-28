package TS_BL;

import TS_SharedClasses.*;

public class BlDiscountPolicy {

	static int updatePrice(DiscountPolicy dp, int price){
		return dp != null ? dp.updatePrice(price) : -1;
	}
	
	static int updatePrice(DiscountPolicy dp, int price, int code){
		return dp != null ? dp.updatePrice(price, code) : -1;
	}
}
