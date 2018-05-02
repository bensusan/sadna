package TS_SharedClasses;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import TS_BL.BlLotteryPurchase;
import TS_BL.BlMain;

public class LotteryPurchase implements PurchaseType {
	
	private Date actualEndDate;
	private Date lotteryEndDate;
	private Map<Guest, Integer> participants;
	private Guest winner = null;
	private boolean hasEnded = false;
	
	public LotteryPurchase(Date lotteryEndDate) {
		this.actualEndDate = lotteryEndDate;
		this.lotteryEndDate = lotteryEndDate;
		this.participants = new HashMap<Guest, Integer>();
	}
	
	public LotteryPurchase(Date lotteryEndDate, Map<Guest,Integer> participants) {
		this(lotteryEndDate);
		if(participants != null)
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

	public void removeAllParticipants(){
		this.participants = new HashMap<Guest, Integer>();
	}

	@Override
	public boolean purchase(Guest g, ProductInCart pic,String buyerAddress) {
		return BlLotteryPurchase.purchase(this, g, pic,buyerAddress);
	}
	
	@Override
	public void undoPurchase(ProductInCart pic, Guest g) {
		BlLotteryPurchase.undoPurchase(this, g);
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
<<<<<<< HEAD

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public void removeParticipant(Guest g) {
		this.participants.remove(g);
	}
	
	
	
=======
	
	public void setWinner(Guest winner){
		if(this.winner == null)
			this.winner = winner;
	}
	
	public void endLottery(boolean hasEnded){
		if(!this.hasEnded)
			this.hasEnded = hasEnded;
	}
	
	public boolean gethasEnded(){
		return this.hasEnded;
	}
	
	public Guest getWinner(){
		return this.winner;
	}
>>>>>>> Alex
}
