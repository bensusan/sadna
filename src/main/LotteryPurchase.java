package main;

import java.sql.Date;
import java.util.Map;

public class LotteryPurchase implements PurchaseType {
	
	private Date lotteryEndDate;
	private Map<Guest, Integer> participants;
	
	public LotteryPurchase(Date lotteryEndDate) {
		this.lotteryEndDate = lotteryEndDate;
		this.participants = null;
		// TODO Auto-generated constructor stub
	}
	
	//Here we will get guest, and the price he wants to pay -> add it to participants.
	//when the lottery will done, we will have the guests to continue the process.
	public boolean purchase(Guest g, int price){
		//TODO
		return false;
	}
	
	public boolean isLotteryDone(){
		//TODO
		return false;
	}
	
	private void closeCurrentLottery(){
		//TODO
	}
	
	private void openNewLottery(){
		//TODO
	}
}
