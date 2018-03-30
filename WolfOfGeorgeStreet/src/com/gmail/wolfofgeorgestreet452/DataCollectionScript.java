package com.gmail.wolfofgeorgestreet452;

import java.util.Scanner;

public class DataCollectionScript {
	
	//Script to output asset data for a given symbol
	public static void main(String [] args) {
		Scanner scanner = new Scanner(System.in);
		 
		System.out.print("Enter the symbol of the stock you would like to look up: ");
		
		//Read in the symbol
		String symbol = scanner.nextLine();
		
		//Fetch the daily data for that symbol
		String data = StockInfoInteractor.fetchStockData(symbol, 1,false);
		
		//If the data was null then invalid symbol
		if(data==null) {
			System.out.println("Invalid symbol. Exiting...");
		}
		
		//Get the prices for the most recent and previous days
		double[] prices = StockInfoInteractor.getTimeSeriesData(data, 1, 0);
		double[] pricesDayBefore=StockInfoInteractor.getTimeSeriesData(data, 1, 1);
		
		System.out.println("Most Recent Day Open Price: " + prices[0]);
		System.out.println("Most Recent Day Interval High Price: " + prices[1]);
		System.out.println("Most Recent Day Interval Low Price: " + prices[2]);
		System.out.println("Most Recent Day Interval Close Price: " + prices[3]);
		
		System.out.println("Preivous Day Open Price: " + pricesDayBefore[0]);
		System.out.println("Preivous Day Interval High Price: " + pricesDayBefore[1]);
		System.out.println("Preivous Day Interval Low Price: " + pricesDayBefore[2]);
		System.out.println("Preivous Day Interval Close Price: " + pricesDayBefore[3]);
		
		//Calculate the change in price and percentage for the asset
		double priceChange=prices[3]-pricesDayBefore[3];
		double percentChange=priceChange/pricesDayBefore[3];
		
		System.out.println("Price Change: $" + priceChange);
		
		System.out.println("Percent Change: " + percentChange*100 + "%");
	}
}
