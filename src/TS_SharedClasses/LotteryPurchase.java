package TS_SharedClasses;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class LotteryPurchase implements PurchaseType {
	
	private Date lotteryEndDate;
	private Map<Guest, Integer> participants;
	
	public LotteryPurchase(Date lotteryEndDate) {
		this.lotteryEndDate = lotteryEndDate;
		this.participants = new HashMap<Guest, Integer>();
	}
	
	public LotteryPurchase(Date lotteryEndDate, Map<Guest,Integer> participants) {
		this.lotteryEndDate = lotteryEndDate;
		this.participants = participants;
	}

	
	public Date getLotteryEndDate() {
		return lotteryEndDate;
	}

	
	public void setLotteryEndDate(Date lotteryEndDate) {
		this.lotteryEndDate = lotteryEndDate;
	}

	
	public Map<Guest, Integer> getParticipants() {
		return participants;
	}

	
	public void setParticipants(Map<Guest, Integer> participants) {
		this.participants = participants;
	}

	@Override
	public boolean purchase(Guest g, int price, int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
