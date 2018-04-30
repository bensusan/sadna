package TS_BL;

import java.util.Date;
import java.util.Map;

import TS_SharedClasses.*;

public class BlLotteryPurchase {

	// Here we will get guest, and the price he wants to pay -> add it to
	// participants.
	// when the lottery will done, we will have the guests to continue the
	// process.
	static boolean purchase(LotteryPurchase lp, Guest g, int price) {
		if (lp == null || g == null || price <= 0)
			return false;
		Map <Guest, Integer> guestPrice = lp.getParticipants();
		guestPrice.put(g, price);
		lp.setParticipants(guestPrice);
		return true;
	}

	static boolean isLotteryDone(LotteryPurchase lp, int price) {
		if(lp == null || price <= 0)
			return false;
		Date date =  new Date();
		if(date.after(lp.getLotteryEndDate())){
			closeCurrentLottery(lp);
			return true;
		}
		int sumOfMoney = 0;
		for (Integer num : lp.getParticipants().values()) {
			sumOfMoney += num;
		}
		if(sumOfMoney >= price){
			lp.setLotteryEndDate(date);
			return true;
		}
		return false;

	}

	static void closeCurrentLottery(LotteryPurchase lp) {
		Date date = new Date();
		lp.setLotteryEndDate((java.util.Date) date);
		for(Guest g : lp.getParticipants().keySet()){
			BlGuest.retMoney(g, lp.getParticipants().get(g));
		}
		
	}

	//get new arg of new end date
	static void openNewLottery(LotteryPurchase lp, Date endDate) {
		lp.setLotteryEndDate((java.sql.Date) endDate);
	}
}
