package TS_BL;

import java.util.Date;

import TS_SharedClasses.*;

public class BlLotteryPurchase {

	// Here we will get guest, and the price he wants to pay -> add it to
	// participants.
	// when the lottery will done, we will have the guests to continue the
	// process.
	public static boolean purchase(LotteryPurchase lp, Guest g, int price, int productPrice) {
		Date date = new Date(); 
		if(date.after(lp.getLotteryEndDate()) || lp.getParticipants().keySet().contains(g))
			return false;
		if(getSumOfMoney(lp) + price > productPrice)
			return false;
		lp.addParticipant(g, price);
		if(getSumOfMoney(lp) == productPrice){
			lp.setLotteryEndDate(date);
			startLottery(lp);
		}
		return true;
	}

	private static int getSumOfMoney(LotteryPurchase lp){
		int sumOfMoney = 0;
		for (Integer num : lp.getParticipants().values()) {
			sumOfMoney += num;
		}
		return sumOfMoney;
	}

	static boolean tryMakeLotteryDone(LotteryPurchase lp, int productPrice) {
		if(lp == null || productPrice <= 0)
			return false;
		Date date =  new Date();
		if(date.after(lp.getLotteryEndDate())){
			closeCurrentLottery(lp);
			return true;
		}
		return false;
	}

	static void closeCurrentLottery(LotteryPurchase lp) {
		Date date = new Date();
		lp.setLotteryEndDate((java.util.Date) date);
		for(Guest g : lp.getParticipants().keySet()){
			BlStore.retMoney(BlMain.getCreditCard(g), lp.getParticipants().get(g));
		}
		
	}

	//get new arg of new end date
	static void openNewLottery(LotteryPurchase lp, Date endDate) {
		lp.setLotteryEndDate((java.sql.Date) endDate);
	}
	
	static void startLottery(LotteryPurchase lp){
		//TODO - here will be the random method
	}
}
