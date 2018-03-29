package com.gmail.wolfofgeorgestreet452;

public class Stock {
	
	private String symbol;
	private String title;
	private String market;
	private double amount;
	
	public Stock(String symbol, String title, String market) {
		this.symbol=symbol;
		this.title=title;
		this.market=market;
		this.amount=0;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getMarket() {
		return market;
	}
	
	public void setAmount(double amount) {
		this.amount=amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	

}
