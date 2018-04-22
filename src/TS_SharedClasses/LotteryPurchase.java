package TS_SharedClasses;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TS_BL.BlImmediatelyPurchase;
import TS_BL.BlLotteryPurchase;

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

	
	public void setLotteryEndDate(Date date) {
		this.lotteryEndDate = date;
	}

	
	public Map<Guest, Integer> getParticipants() {
		return participants;
	}

	
	public void setParticipants(Map<Guest, Integer> participants) {
		this.participants = participants;
	}

	@Override
	public boolean purchase(Guest g, int price, int amount) {
		if(amount <= 0)
			return false;
		boolean bool = true;
		for (int i = 0; i < amount; i++){
			bool = BlLotteryPurchase.purchase(this, g, price);
			if(!bool)
				return bool;
		}
		return bool;
	}
}
