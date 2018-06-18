package TS_SharedClasses;

public class GuestInLottery {
	private String creditCard;
	private String buyerAddress;
	private int money;
	
	public GuestInLottery(String creditCard, String buyerAddress, int money) {
		super();
		this.creditCard = creditCard;
		this.buyerAddress = buyerAddress;
		this.money = money;
	}
	
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

	public void resetAll() {
		this.buyerAddress = "";
		this.creditCard = "";
		this.money = 0;
	}
	
	
	
}
