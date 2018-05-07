package TS_BL;

import TS_SharedClasses.*;

public class BlDiscountPolicy {


	static int updatePrice(DiscountPolicy dp, int price, int code) throws Exception{
		return dp != null ? dp.updatePrice(price, code) : -1;
	}
}
