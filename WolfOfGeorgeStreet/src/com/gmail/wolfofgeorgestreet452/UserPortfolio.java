package com.gmail.wolfofgeorgestreet452;

public class UserPortfolio{
		private String username = " ";
		private double portfolioValue = 0;
		
		public UserPortfolio(String name, double value) {
			this.username = name;
			this.portfolioValue = value;
		}
		//copy constructor
		public UserPortfolio(UserPortfolio other) {
			this.username = other.username;
			this.portfolioValue = other.portfolioValue;
		}
		
		public String getUsername() {
			return username;
		}
		
		public double getPortfolioValue() {
			return portfolioValue;
		}
		
	}