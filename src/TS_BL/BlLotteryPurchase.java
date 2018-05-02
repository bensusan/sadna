package TS_BL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import TS_SharedClasses.*;

public class BlLotteryPurchase {

	public static boolean purchase(LotteryPurchase lp, Guest g, ProductInCart pic,String buyerAddress) {
		int price = pic.getPrice();
		int productPrice = pic.getMyProduct().getPrice();
		Date date = new Date(); 
		if(date.after(lp.getActualEndDate()) || lp.getParticipants().keySet().contains(g))
			return false;
		if(lp.gethasEnded())
			return false;
		if(getSumOfMoney(lp) + price > productPrice)
			return false;
		lp.addParticipant(g, price);
<<<<<<< HEAD
		if(getSumOfMoney(lp) == productPrice)
			lp.setActualEndDate(date);;
=======
		if(getSumOfMoney(lp) == productPrice){
			lp.setLotteryEndDate(date);
			startLottery(lp);
			lp.endLottery(true);
			BlStore.sendTheProducts(lp.getWinner(), buyerAddress);
			
		}
>>>>>>> Alex
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
		if(date.after(lp.getActualEndDate())){
			closeCurrentLottery(lp);
			return true;
		}
		return false;
	}

	static void closeCurrentLottery(LotteryPurchase lp) {
		Date date = new Date();
		lp.setActualEndDate((java.util.Date) date);
		for(Guest g : lp.getParticipants().keySet()){
			BlStore.retMoney(BlMain.getCreditCard(g), lp.getParticipants().get(g));
		}
		
		
	}

	//get new arg of new end date
	static void openNewLottery(LotteryPurchase lp, Date endDate) {
		lp.setLotteryEndDate((java.sql.Date) endDate);
	}
	
	static void startLottery(LotteryPurchase lp){

		Map<Guest,Integer> parti = lp.getParticipants();
		List<Guest> lotto = new ArrayList<Guest>();
		for (Guest g : parti.keySet()) {
			int amount = parti.get(g);
			for(int i = 0; i < amount; i++)
				lotto.add(g);
		}
		
		Collections.shuffle(lotto);
		Random rand = new Random();
		
		 lp.setWinner(lotto.get(rand.nextInt(lotto.size())));
	}

	public static void undoPurchase(LotteryPurchase lotteryPurchase, Guest g) {
		lotteryPurchase.setActualEndDate(lotteryPurchase.getLotteryEndDate());
		lotteryPurchase.removeParticipant(g);
	}
}
