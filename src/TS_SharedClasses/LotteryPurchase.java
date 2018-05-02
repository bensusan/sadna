package TS_SharedClasses;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TS_BL.BlLotteryPurchase;
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

	public void removeAllParticipants(){
		this.participants = new HashMap<Guest, Integer>();
	}

	@Override
	public boolean purchase(Guest g, ProductInCart pic) {
		return BlLotteryPurchase.purchase(this, g, pic);
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
	
	
	public boolean addParticipant(Guest g, int price){
		return this.participants.put(g, price) == null;
	}
}
