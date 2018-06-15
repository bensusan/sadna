package TS_BL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import TS_SharedClasses.*;
import static TS_BL.BlMain.dalRef;

public class BlLotteryPurchase {

	public static boolean purchase(LotteryPurchase lp, Guest g, ProductInCart pic, String buyerAddress) throws Exception {
		int price = pic.getDiscountOrPrice();
		int productPrice = pic.getMyProduct().getPrice();
		Date date = new Date(); 
		if(date.after(lp.getActualEndDate()) || lp.gethasEnded())
			throw new Exception("lottery has ended");
		if(lp.getParticipants().keySet().contains(g))
			throw new Exception("you are already in!");
		if(getSumOfMoney(lp) + price > productPrice)
			throw new Exception("you can't pay more than the original price");
		lp.addParticipant(g, price);

		if(getSumOfMoney(lp) == productPrice){
			lp.setLotteryEndDate(date);
			startLottery(lp);
			lp.endLottery(true);
			Guest winner = lp.getWinner();
			int currentAmount = pic.getMyProduct().getStore().getProducts().get(pic.getMyProduct());
//			int currentAmount = dalRef.getAmountProductsAmount(pic.getMyProduct());
			BlStore.stockUpdate(pic.getMyProduct(), currentAmount - 1);
			BlStore.addProductToHistory(pic);
			if(winner instanceof Subscriber)
				BlSubscriber.addPurchaseToHistory((Subscriber)(lp.getWinner()), new Purchase(date, pic));
			BlStore.sendTheProducts(lp.getWinner(), buyerAddress);
		}
//		dalRef.setLottery(pic.getMyProduct(), lp);
		return true;
	}

	private static int getSumOfMoney(LotteryPurchase lp){
		int sumOfMoney = 0;
		for (Integer num : lp.getParticipants().values()) {
			sumOfMoney += num;
		}
		return sumOfMoney;
	}

	static boolean tryMakeLotteryDone(LotteryPurchase lp, int productPrice) throws Exception {
		if(lp == null)
			throw new Exception("something went wrong");
		if(productPrice <= 0)
			throw new Exception("price must be greater than 0");
			
		Date date =  new Date();
		if(date.after(lp.getActualEndDate())){
			closeCurrentLottery(lp);
			return true;
		}
		throw new Exception("lottery has passed");
	}

	static void closeCurrentLottery(LotteryPurchase lp) {
		Date date = new Date();
		lp.setActualEndDate((java.util.Date) date);
		for(Guest g : lp.getParticipants().keySet()){
			BlStore.retMoney(BlMain.getCreditCard(g), lp.getParticipants().get(g));
		}
		
		lp.removeAllParticipants();
		
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
