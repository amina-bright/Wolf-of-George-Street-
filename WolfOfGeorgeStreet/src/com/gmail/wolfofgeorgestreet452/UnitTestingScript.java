package com.gmail.wolfofgeorgestreet452;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class UnitTestingScript {
	
	public static void main(String [] args) {
		//LoginTest();
		PortfolioTest();
		TranscationVerification();
	}
	
	//This method tests whether a given user exists in the database
	public static void LoginTest() {
		Scanner scanner = new Scanner(System.in);
		 
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();
		 
		System.out.print("Enter your password: ");
		String password = scanner.nextLine();
		 
		WolfOfGeorgeStreet wg=new WolfOfGeorgeStreet();
		
		boolean result=wg.loginTest(username, password);
		
		if(result==true) {
			System.out.println("login successful");
		} else {
			System.out.println("login failed");
		}
	}
	
	//This method tests whether a transaction with the inputted details would be possible
	public static void TranscationVerification() {
		Scanner scanner = new Scanner(System.in);
		 
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();
		
		System.out.println("Enter 'buy' or 'sell'");
		String type = scanner.nextLine();
		
		if(!type.equals("buy")&&!type.equals("sell")) {
			System.out.println("Invalid input. Returning...");
		}
		
		System.out.println("Enter the leagueID: ");
		String leagueId = scanner.nextLine();
		
		System.out.println("Enter the asset symbol: ");
		String symbol = scanner.nextLine();
		
		System.out.println("Enter the amount: ");
		Double amount=null;
		try {
			amount = Double.parseDouble(scanner.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid Input. Returning...");
			return;
		}
		
		DetailedDescription dd=new DetailedDescription();
		boolean result=dd.transactionVerificationTest(username, type, leagueId, symbol,amount.doubleValue());
		
		if(result) {
			System.out.println("Transaction would be possible");
		}
		
		else {
			System.out.println("Transaction would not be possible");
		}
		
	}
	
	//This method will output the leagueID and asset information for each asset associated with the inputted user
	public static void PortfolioTest() {
		Scanner scanner = new Scanner(System.in);
		 
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();
		
		Portfolio p=new Portfolio();
		
		HashMap<String,ArrayList<Stock>> output=p.portfolioTest(username);
		
		Iterator it=output.entrySet().iterator();
		
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        String leagueId=(String) pair.getKey();
	        ArrayList<Stock> assets=(ArrayList<Stock>)pair.getValue();
	        for(int i=0;i<assets.size();i++) {
	        	System.out.println("LeagueID:" + leagueId+" Asset: " + assets.get(i).getSymbol() + " Amount: " + assets.get(i).getAmount());
	        }
	        it.remove();
	    }
		
	}
}
