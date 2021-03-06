package TS_BL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import TS_ServiceLayer.GreetingController;
import TS_SharedClasses.*;

public class BlLotteryPurchase {

	public static boolean purchase(LotteryPurchase lp, Guest g, ProductInCart pic,String buyerAddress) throws Exception {
		int price = pic.getDiscountOrPrice();
		int productPrice = pic.getMyProduct().getPrice();
		Date date = new Date(); 
		if(date.after(lp.getActualEndDate()) || lp.gethasEnded()){
			throw new Exception("lottery has ended");
		}
		if(lp.getParticipants().keySet().contains(g))
			throw new Exception("you are already in!");
		if(productPrice <= 0)
			throw new Exception("price must be greater than 0");
		if(getSumOfMoney(lp) + price > productPrice)
			throw new Exception("you can't pay more than the original price");
		String creditCard;
		if((creditCard = BlGuest.currGuest.get(g)) == null)
			throw new Exception("wrong creditCart");
		
		if(g instanceof Subscriber)
			lp.addParticipant(((Subscriber) g).getUsername(), new GuestInLottery(creditCard, buyerAddress, pic.getDiscountOrPrice()));
		else
			lp.addParticipant(GreetingController.holdMyData.guests.get(g) + "", new GuestInLottery(creditCard, buyerAddress, pic.getDiscountOrPrice()));
		
		BlStore.payToStore(pic.getMyProduct().getStore(), productPrice, creditCard);
		
		if(getSumOfMoney(lp) == productPrice){
			lp.setLotteryEndDate(date);
			makeLottery(lp,pic);		
		}

		return true;
	}

	private static int getSumOfMoney(LotteryPurchase lp){
		int sumOfMoney = 0;
		Collection <GuestInLottery> gils = lp.getParticipants().values();
		for (GuestInLottery gil : gils) {
			sumOfMoney += gil.getMoney();
		}
		return sumOfMoney;
	}

	static void closeCurrentLottery(LotteryPurchase lp,Product p) throws Exception {
		Date date = new Date();
		lp.setActualEndDate((java.util.Date) date);
		for(String g : lp.getParticipants().keySet()){
			GuestInLottery gil = lp.getParticipants().get(g);
			BlStore.retMoney(gil.getCreditCard() , gil.getMoney());
			gil.resetAll();
		}
		
		lp.removeAllParticipants();
		lp.endLottery(true);
		deleteFromAllParticipantsCarts(lp,p,"The lottery over " + p.getName() 
				+ " , has ended and no one won<br>you got your money back");
	}

	//get new arg of new end date
	static void openNewLottery(LotteryPurchase lp, Date endDate) {
		lp.setLotteryEndDate((java.sql.Date) endDate);
	}
	
	static void makeLottery(LotteryPurchase lp,ProductInCart pic) throws Exception{
		Map<String,GuestInLottery> parti = lp.getParticipants();
		List<String> lotto = new ArrayList<String>();
		for (String u : parti.keySet()) {
			int amount = parti.get(u).getMoney();
			for(int i = 0; i < amount; i++)
				lotto.add(u);
		}
		Collections.shuffle(lotto);
		Random rand = new Random();
		
		//the chosen one as String(username for sub or init number for guest)
		String s = lotto.get(rand.nextInt(lotto.size()));
		
		Subscriber winner = BlMain.getSubscriberFromUsername(s);
		Guest g;
		if(winner != null){
			GreetingController.holdMyData.sendMsgToSub(s, "Congratulation! you are the lucky winner of the lottery!"
					+ " We sent you " + pic.getMyProduct().getName() + "enjoy!<br>");
			g = (Guest)winner;
			lp.setWinner(g);
		}
		else{
			g = GreetingController.holdMyData.guests.get(Integer.parseInt(s));
			GreetingController.holdMyData.sendMsgToGuest(g,"Congratulation! you are the lucky winner of the lottery!"
					+ " We sent you " + pic.getMyProduct().getName() + "enjoy!<br>");
		}
		
		for (String string : lp.getParticipants().keySet()) {
			if(string == s)
				continue;
			
			Subscriber ssub = BlMain.getSubscriberFromUsername(string);
			if(ssub != null)
				GreetingController.holdMyData.sendMsgToSub(string, "Lottery over " + pic.getMyProduct().getName() + " has ended. You didn't win. goodluck next time<br>");
			else{
				Guest gg = GreetingController.holdMyData.guests.get(Integer.parseInt(string));
				GreetingController.holdMyData.sendMsgToGuest(gg, "Lottery over " + pic.getMyProduct().getName() + " has ended. You didn't win. goodluck next time<br>");
			}
		}
		
		lp.setWinner(winner); 
		BlStore.sendTheProducts(winner, lp.getParticipants().get(s).getBuyerAddress());
		
		Purchase pur = BlStore.addProductToHistory(pic);
		
		if(winner instanceof Subscriber){
			BlSubscriber.addPurchaseToHistory(winner, pur);
		}
		
		Store store = pic.getMyProduct().getStore();
		int toUpdate = store.getProducts().get(pic.getMyProduct()) - 1;
		boolean ans = BlStore.stockUpdate(pic.getMyProduct(),toUpdate);
		GreetingController.holdMyData.sendMsgToStore(store,"Lottery has ended! there is a winner");
		
		lp.endLottery(true);
		deleteFromAllParticipantsCarts(lp,pic.getMyProduct(),"");
		lp.removeAllParticipants();
	}
	
	private static void deleteFromAllParticipantsCarts(LotteryPurchase lp, Product pic,String msg) throws Exception{
		for (String s : lp.getParticipants().keySet()) {
			Subscriber sub = BlMain.getSubscriberFromUsername(s);
			Guest g;
			if(sub != null){
				if(msg != "")
					GreetingController.holdMyData.sendMsgToSub(s, msg);
				g = (Guest)sub;
			}
			else{
				g = GreetingController.holdMyData.guests.get(Integer.parseInt(s));
				if(msg != "")
					GreetingController.holdMyData.sendMsgToGuest(g, msg);
			}
			
			BlGuest.removeProductFromCart(g, pic);
		}	
	}

	public static void undoPurchase(LotteryPurchase lotteryPurchase, Guest g) {
		lotteryPurchase.setActualEndDate(lotteryPurchase.getLotteryEndDate());
		lotteryPurchase.removeParticipant(g);
	}
}
