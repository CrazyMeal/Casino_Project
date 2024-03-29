package model;

public class Player {
	private static final int CASH_TO_BUY = 1000;
	private String pseudo;
	private int cash,bid;
	
	public Player(){
		this.pseudo = "default_player";
		this.cash = 0;
		this.bid = 0;
	}
	public void getMoreCash(){
		this.cash += CASH_TO_BUY;
	}
	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
}
