package com.gmail.wolfofgeorgestreet452;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.tomcat.util.net.URL;

public class IntegrationTestingScript {
	
	public static void main(String [] args) {
		System.out.println("Beginning Login Test...");
		CreateAccountTest();
		System.out.println("Beginning Portfolio Test...");
		PortfolioTesting();
		System.out.println("Beginning Transaction Verification Test...");
		TranscationVerification();
		System.out.println("Beginning Create League Test..");
		CreateLeagueTest();
		System.out.println("Beginning Join League Test..");
		JoinLeagueTest();
	}
	
	
	private static void TranscationVerification() {
		// TODO Auto-generated method stub
		
	}


	public static void oneTimeSetUp() {
	    // start my app server and make sure apps are installed
	}
	
	public static void oneTimeTearDown() {
	    // shut down app server
	}

	//This method tests whether a given user exists in the database
	public static void CreateAccountTest() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter your First Name: ");
		String fname = scanner.nextLine();
		System.out.print("Enter your Last Name: ");
		String lname = scanner.nextLine();
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();
		System.out.print("Enter your email: ");
		String email = scanner.nextLine();
		System.out.print("Enter your password: ");
		String password = scanner.nextLine();
		 
		WolfOfGeorgeStreet wg=new WolfOfGeorgeStreet();
		
		boolean result=wg.loginTest(username, password);
		
		if(result==true) {
			System.out.println("login successful, account created and added to database");
		} else {
			System.out.println("login failed, account already created");
		}
	}
	
	//This method tests whether a transaction with the inputted details would be possible
	public static void TranasationTesting() {
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
	public static void PortfolioTesting() {
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
	
	public static void CreateLeagueTest() { //This method test if create league can be performed with the user inputted information
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();
		 
		System.out.print("Enter leagueName: ");
		String leagueName = scanner.nextLine();
		
		System.out.print("Enter 'normal' or 'headtohead' for gamemode: ");
		String gamemode = scanner.nextLine();
		
		if(!gamemode.equals("normal")&&!gamemode.equals("headtohead")) {
			System.out.println("Invalid input. Returning...");
		}
		
		System.out.print("Enter start date in format mm/dd/yyyy: ");
		String startdate = scanner.nextLine();
		
		System.out.print("Enter end date in format mm/dd/yyyy: ");
		String enddate = scanner.nextLine();
		
		System.out.print("Enter '1' to include cryptocurrency transactions or '0' to not include them: ");
		int crypto = scanner.nextInt();
		
		if(crypto != 0 &&crypto!=1) {
			System.out.println("Invalid input. Returning...");
		}
		
		System.out.print("Enter max particiants: ");
		int participants = scanner.nextInt();
		
		System.out.print("Enter starting principle: ");
		double principle = scanner.nextDouble();
		
		CreateLeague cl=new CreateLeague();
		boolean result=cl.CreateLeagueTest(username,leagueName,gamemode,startdate,enddate,crypto,participants,principle);
		
		if(result==true) {
			System.out.println("create league successful");
		} else {
			System.out.println("create league failed");
		}
	}
	
	public static void JoinLeagueTest() { //This method test if join league can be performed with the user inputted information
		Scanner scanner = new Scanner(System.in);
		 
		System.out.print("Enter leagueID: ");
		String leagueId = scanner.nextLine();
		 
		JoinLeague jl=new JoinLeague();
		
		boolean result=jl.JoinLeagueTest(leagueId);
		
		if(result==true) {
			System.out.println("join league successful");
		} else {
			System.out.println("join league failed");
		}
	}
	
	
	
}