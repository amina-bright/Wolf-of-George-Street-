package com.gmail.wolfofgeorgestreet452;

public class UserPortfolio{
	private String username;
	private double portfolioValue;
	
	public UserPortfolio() {
		username = " ";
		portfolioValue = 0;
	}
	
	public UserPortfolio(UserPortfolio other) {
		this.username = other.getUsername();
		this.portfolioValue = other.getPortfolioValue();
	}
	
	public UserPortfolio(String username, double value) {
		this.username = username;
		this.portfolioValue = value;
	}
	
	public String getUsername() {
		return username;
	}
	
	public double getPortfolioValue() {
		return portfolioValue;
	}
	
	public void setUsername(String user) {
		username = user;
	}
	
	public void setPortfolioValue(double value) {
		portfolioValue = value;
	}
}