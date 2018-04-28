package TS_SharedClasses;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TS_BL.BlMain;

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
		if(participants == null)
			this.participants = new HashMap<Guest, Integer>();
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
			bool = BlMain.purchase(this, g, price);
			if(!bool)
				return bool;
		}
		return bool;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LotteryPurchase other = (LotteryPurchase) obj;
		if (lotteryEndDate == null) {
			if (other.lotteryEndDate != null)
				return false;
		} else if (!lotteryEndDate.equals(other.lotteryEndDate))
			return false;
		if(!BlMain.equalsMaps(participants, other.participants))
			return false;
		return true;
	}
	
}
