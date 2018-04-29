package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LeagueInfo")
public class LeagueInfo extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
	
	public LeagueInfo() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		if(request.getSession().getAttribute("username")==null) {
 			response.sendRedirect(request.getContextPath());
 			return;
 		}  
 		String button=request.getParameter("Submit");
 		
 		if("submit".equals(button)) {			
 		System.out.print("button_pressed");	
 		}    	  
     	  
		String username=(String) request.getSession().getAttribute("username");

		String leagueID=(String) request.getParameter("leagueID");
		String leagueName = " ";
		
		ArrayList<String> leagueMemberNames=new ArrayList<String>();
		ArrayList<Double> leagueAssets=new ArrayList<Double>();
		
		//List of lists for all the assets the user has for each league
		
		Connection conn = null;
		Statement stmt = null;
		 
		try{
		      //register jdbc driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //Grab all the leagues the user is in
		      stmt = conn.createStatement();
		      String sql;
		      sql="SELECT * from LeagueUserList WHERE leagueID = '"+ leagueID + "' ORDER BY liquidMoney DESC";
		      ResultSet rs=stmt.executeQuery(sql);
		      
		      int i = 0;
		      while(rs.next()) {
		    	  i++;
		    	  if(username.equals(rs.getString("username").trim())) {
		    		  request.setAttribute("userAsset", rs.getDouble("liquidMoney"));
		    		  request.setAttribute("userRank", i);
		    	  }
		    	  leagueMemberNames.add(rs.getString("username"));
		    	  leagueAssets.add(rs.getDouble("liquidMoney"));
		      }
		      
		      sql="SELECT LeagueName from League WHERE leagueID = '"+ leagueID + "' ";
		      rs = stmt.executeQuery(sql);
		      
		      while(rs.next()) {
		    	  leagueName = rs.getString("LeagueName");
		      }		      
		      
		      ArrayList<Double> assetSums=new ArrayList<Double>();
		      
		      for(int h =0;h<leagueMemberNames.size();h++)
		      {
		      sql="SELECT * FROM Asset, StockLookup WHERE username='" + leagueMemberNames.get(h) + "' AND leagueID=" + leagueID + " AND asset=symbol";
	    	  rs=stmt.executeQuery(sql);
	    	  double assetSum = 0;
	    	  
	    	  while(rs.next()) {
	    		  String symbol=rs.getString("symbol");
	    		  String market=rs.getString("market");
	    		  String title=rs.getString("title");
	    		  double amount=rs.getDouble("amount");
	    		  
	    		  Stock newStock=new Stock(symbol, title, market);
	    		  newStock.setAmount(amount);
	    		  
	    		 
	    		  
	    		//for each asset, obtain the current price
	    		  double dataParsed[] = null;
	    		  assetSum = 0;
	    		  while(dataParsed==null) {
	    		  if(market.equals("CRYPTO")) {
	    				  String data=StockInfoInteractor.fetchCryptoData(symbol, 1);
	    				  dataParsed=StockInfoInteractor.getTimeSeriesDataCrypto(data, 1, 0);
	    		  }else {
	    				  String data=StockInfoInteractor.fetchStockData(symbol, 1, false);
	    				  dataParsed=StockInfoInteractor.getTimeSeriesData(data, 1, 0);
	    		  }
	    		  if(dataParsed==null) {
	    			 //System.out.println("TEST: " + System.currentTimeMillis());
	    			  TimeUnit.SECONDS.sleep(10);
	    		  }
	    		  }
		    		  //add to the running sum of the asset value
		    		  //System.out.println("getting current price of " + symbol);
		    		  double currentPrice = dataParsed[3];
		    		  assetSum += currentPrice*amount;
	    	  }
	    	  
	    	  assetSums.add(h, assetSum);
	      }
		      
		      ArrayList<String> leagueMemberNames_temp= new ArrayList<String>();
		      for(int x=0;x<leagueMemberNames.size();x++)
		      {
		    	  leagueMemberNames_temp.add(leagueMemberNames.get(x)); //Creates duplicate league member names arraylist 
		      }  	//for match-making
		      
		      
		      
		      ArrayList<String> User1=new ArrayList<String>();
		      ArrayList<String> User2=new ArrayList<String>();
		     
		      Collections.shuffle(leagueMemberNames_temp); //Randomizes order of names to determine matchups
		      
		      for(int x=0;x<leagueMemberNames_temp.size()/2;x++)
		      {
				User1.add(leagueMemberNames_temp.get(x)); 								//Adds users to arraylist for matchmaking		
				User2.add(leagueMemberNames_temp.get(leagueMemberNames.size()/2+x));
		      }
				
		      int initial; //Dummy variables used to enter values into the database
		      initial=0;
		      int hi=10;
		      int round;
		      round=0;
		      		      		
		      	
		      		/*
		      		for(int h=0;h<leagueMemberNames_temp.size()/2;h++)
				      {
				    	  sql = "INSERT INTO Head2HeadMatchUp (LeagueID, round,username1 ,username2,User1_StartVal, User2_StartVal)"//sql query to add to h2h mode
				    		  + "VALUES ('" + leagueID + "', '" + initial + "', '"+ User1.get(h)+ " ' , '"+ User2.get(h) + "', '"+initial+ "','"+ hi +"')";
			      
			    	  stmt=conn.prepareStatement(sql);
				      stmt.executeUpdate(sql);//executes sql query to add to H2Hmatchups if in H2H mode
				      }
		      		*/
		      		for(int g=0;g<leagueMemberNames_temp.size()/2;g++)
				      {
		      		sql = "SELECT * from Head2HeadMatchUp where username1='"+ User1.get(g) + "'  and leagueID = '"+ leagueID + "'and round= '"+ round + "'";
		      		rs = stmt.executeQuery(sql);
				      
				      while(rs.next()) {
				    	 
				    	 double U1_StartVal = rs.getDouble("User1_StartVal"); //Gets the portfolio value of users at start of round
				    	 double U2_StartVal = rs.getDouble("User2_StartVal");
				    	 
				    	 double U1_EndVal = rs.getDouble("User1_EndVal"); //Gets the portfolio value of users at end of round
				    	 double U2_EndVal = rs.getDouble("User2_EndVal");
				    	 
				    	 String U1 = rs.getString("username1");//Gets usernames to send to the jsp for leaderboard
				    	 String U2 = rs.getString("username2");
				    	 
				    	 double U1_profit = U1_StartVal - U1_EndVal; //Calculates who won the round
				    	 double U2_profit = U2_StartVal - U2_EndVal;
				    	 
				    	 if (U1_profit>U2_profit) { //If User1 won, give user1 a win and user2 a loss
				    		sql = "update WolfOfGeorgeStreetDB.Head2Head set win =win +1 where leagueID = '"+ leagueID +"' and username = '"+U1+"'" ; 
				    		stmt=conn.prepareStatement(sql);
						    stmt.executeUpdate(sql);
						    
						    sql = "update WolfOfGeorgeStreetDB.Head2Head set loss =loss +1 where leagueID = '"+ leagueID +"' and username = '"+U2+"'" ; 
				    		stmt=conn.prepareStatement(sql);
						    stmt.executeUpdate(sql);
				    	 } else {//If User2 won, give user2 a win and user1 a loss
				    		 sql = "update WolfOfGeorgeStreetDB.Head2Head set win =win +1 where leagueID = '"+ leagueID +"' and username = '"+U2+"'" ; 
					    		stmt=conn.prepareStatement(sql);
							    stmt.executeUpdate(sql);
							    
							    sql = "update WolfOfGeorgeStreetDB.Head2Head set loss =loss +1 where leagueID = '"+ leagueID +"' and username = '"+U1+"'" ; 
					    		stmt=conn.prepareStatement(sql);
							    stmt.executeUpdate(sql);
				    		 
				    		 
				    	 }
				    	 }
				    	 
				      }
		      		
		      		
		      		ArrayList<Integer> wins=new ArrayList<Integer>(); //arraylist with user wins
		      		ArrayList<Integer> losses=new ArrayList<Integer>(); //arraylist with user losses
		      		ArrayList<String> leagueMemberNamesH2H=new ArrayList<String>(); //arraylist with membernames in H2H mode
		      		sql="SELECT * from Head2Head WHERE leagueID = '"+ leagueID + "' ORDER BY win desc, loss asc";//Sql query to determine H2H standings based on Win/Loss
				      ResultSet rs2=stmt.executeQuery(sql);
				      
				      int ii = 0;
				      while(rs2.next()) {
				    	  leagueMemberNamesH2H.add(rs2.getString("username")); //Gets username from database
				    	  wins.add(rs2.getInt("win")); //Gets amount of wins from database
				    	  losses.add(rs2.getInt("loss"));//Gets amount of loss from database
				      }
				      
				    ArrayList<Double> percentage=new ArrayList<Double>();  
		      		for (int q=0;q<wins.size();q++) //Calculates user win-loss percentage
		      		{		      			
		      			if (wins.get(q)+losses.get(q)== 0) //Prevents division by zero
		      			{
		      				percentage.add((1.00));
		      			}else	
		      				percentage.add((double) wins.get(q)/(wins.get(q)+losses.get(q)));	
		      		}
		      		
				      stmt.close();
				      conn.close();
		      
		      
		      
		      //Send the lists to the jsp
		      request.setAttribute("leagueName", leagueName);
		      request.setAttribute("leagueMemberNames", leagueMemberNames);
		      request.setAttribute("leagueMemberNamesH2H", leagueMemberNamesH2H);
		      request.setAttribute("leagueAssets", leagueAssets);
		      request.setAttribute("User1", User1);
		      request.setAttribute("User2", User2);
		      request.setAttribute("wins", wins);
		      request.setAttribute("losses", losses);
		      request.setAttribute("percentage", percentage);
		      request.setAttribute("assetSums", assetSums);
		      
		     //Display the jsp
		     request.getRequestDispatcher("/jsps/LeagueInfo.jsp").forward(request, response);
		      
		   }catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request,response);
		System.out.print("button_pressed");
		String button=request.getParameter("Submit");
		if("submit".equals(button)) {	
			System.out.print("button_pressed");
		}
		
		
	}
	
	
	
}