package com.gmail.wolfofgeorgestreet452;

public class Stock {
	
	private String symbol;
	private String title;
	private String market;
	
	public Stock(String symbol, String title, String market) {
		this.symbol=symbol;
		this.title=title;
		this.market=market;
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
	
	

}
