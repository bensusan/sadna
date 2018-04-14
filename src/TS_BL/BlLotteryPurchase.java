package TS_BL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TS_SharedClasses.*;

public class BlLotteryPurchase {

	// Here we will get guest, and the price he wants to pay -> add it to
	// participants.
	// when the lottery will done, we will have the guests to continue the
	// process.
	public static boolean purchase(LotteryPurchase lp, Guest g, int price) {
		Map <Guest, Integer> guestPrice = lp.getParticipants();
		guestPrice.put(g, price);
		lp.setParticipants(guestPrice);
		return false;
	}

	public static boolean isLotteryDone(LotteryPurchase lp, int price) {
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
			lp.setLotteryEndDate((java.sql.Date) date);
			return true;
		}
		return false;

	}

	public static void closeCurrentLottery(LotteryPurchase lp) {
		Date date = new Date();
		lp.setLotteryEndDate((java.sql.Date) date);
		for(Guest g : lp.getParticipants().keySet()){
			BlMoneySystem.retMoney(g, lp.getParticipants().get(g));
		}
		
	}

	//get new arg of new end date
	public static void openNewLottery(LotteryPurchase lp, Date endDate) {
		lp.setLotteryEndDate((java.sql.Date) endDate);
	}
}
