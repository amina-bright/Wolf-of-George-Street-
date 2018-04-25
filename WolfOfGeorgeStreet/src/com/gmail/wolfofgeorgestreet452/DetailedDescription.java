package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/detailed-description")
public class DetailedDescription extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    
	
	public DetailedDescription() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//If no one is logged in redirect to the login page
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String username=(String) request.getSession().getAttribute("username");
		
		//Symbol of the stock requested
		String symbol=request.getParameter("symbol");
		
		String market=request.getParameter("market");
		
		//If no symbol specified, error. Return to the login page
		if(symbol==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		double[] dataParsed=null;
		double[] dataParsedBefore=null;
		//Allocate variable to hold stock that will be sent to the jsp
		Stock requestedStock=null;
		
		if(market.equals("CRYPTO")) {
			String data=StockInfoInteractor.fetchCryptoData(symbol, 1);
			
			dataParsed=StockInfoInteractor.getTimeSeriesDataCrypto(data, 1, 0);
			
			int count=1;
			while(dataParsedBefore==null) {
				dataParsedBefore=StockInfoInteractor.getTimeSeriesDataCrypto(data, 1, count);
			}
			
			ArrayList<Double> weekData=new ArrayList<Double>();
			int nullCount=0;
			int numNullBefore=0;
			for(int i=0;i<100;i++) {
				double[] temp=StockInfoInteractor.getTimeSeriesDataCrypto(data, 1, i);
				
				if(temp==null) {
					nullCount++;
					numNullBefore++;
					continue;
				}
				
				double close = temp[3];
				int j=0;
				while(j<numNullBefore+1) {
					weekData.add(close);
					j++;
				}
				if(weekData.size()>=31) {
					break;
				}
				numNullBefore=0;
			}
			request.setAttribute("weekBefore", weekData.toArray());
		} 
		
		else {
			//Get the daily data
			String data=StockInfoInteractor.fetchStockData(symbol, 1, false);
			
			//Parse out the prices for the most recent time
			dataParsed=StockInfoInteractor.getTimeSeriesData(data, 1, 0);
		;
			
			//Grab the data from the previous trading day to use to compare with
			int count=1;
			while(dataParsedBefore==null) {
				dataParsedBefore=StockInfoInteractor.getTimeSeriesData(data, 1, count);
				count++;
			}
			
			//
			ArrayList<Double> weekData=new ArrayList<Double>();
			int nullCount=0;
			int numNullBefore=0;
			for(int i=0;i<100;i++) {
				double[] temp=StockInfoInteractor.getTimeSeriesData(data, 1, i);
				
				if(temp==null) {
					nullCount++;
					numNullBefore++;
					continue;
				}
				
				double close = temp[3];
				int j=0;
				while(j<numNullBefore+1) {
					weekData.add(close);
					j++;
				}
				if(weekData.size()>=31) {
					break;
				}
				numNullBefore=0;
			}
			request.setAttribute("weekBefore", weekData.toArray());
		}
		
		ArrayList<String> leagueNames=new ArrayList<String>();
		ArrayList<String> leagueIds=new ArrayList<String>();
		ArrayList<Double> liquidMoneys=new ArrayList<Double>();
		
		//JDBC boilerplate
		Connection conn = null;
		Statement stmt = null;
		 
		try{
		      //register jdbc driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      
		      stmt = conn.createStatement();
		      String sql;
		      
		      //Get the stock with the requested symbol
		      sql = "SELECT * FROM StockLookup WHERE symbol= '" + symbol + "'";
		      
		      
		      ResultSet rs = stmt.executeQuery(sql);

		      //Extract the title and market of the stock
		      while(rs.next()){
		         String title = rs.getString("title");
		        // String market=rs.getString("market");
		         
		         //Create a stock out of the data
		         requestedStock=new Stock(symbol,title,market);
		      }
		      
		      //Grab all the leagues the user participates in
		      sql="SELECT * From LeagueUserList s, League w WHERE s.username='" + username + "' AND s.leagueID=w.leagueID";
		      rs=stmt.executeQuery(sql);
		      
		      //Add them to the lists
		      while(rs.next()) {
		    	  leagueIds.add(rs.getString("leagueID"));
		    	  leagueNames.add(rs.getString("leagueName"));
		    	  liquidMoneys.add(rs.getDouble("liquidMoney"));
		      }
		      
		      //close connections
		      rs.close();
		      stmt.close();
		      conn.close();
		      
		      
		      //pass stock and its data to the jsp
		      request.setAttribute("stock",requestedStock);
		      request.setAttribute("currentPrice", dataParsed[3]);
		      request.setAttribute("previousClose", dataParsedBefore[3]);
		      
		      //Calculate changes in price from previous trading day
		      double amountChanged=dataParsed[3]-dataParsedBefore[3];
		      double percentChanged=amountChanged/dataParsedBefore[3];
		      
		      
		      //Send this information plus the leagues lists to the jsp
		      request.setAttribute("amountChanged", amountChanged);
		      request.setAttribute("percentChanged", percentChanged);
		      request.setAttribute("leagueIds", leagueIds.toArray());
		      request.setAttribute("leagueNames", leagueNames.toArray());
		      request.setAttribute("liquidMoneys", Arrays.toString(liquidMoneys.toArray()));
		      
		      //Display the jsp
		      request.getRequestDispatcher("/jsps/detailed-description.jsp").forward(request, response);
		      
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
		
		//If no one logged in redirect to the login page
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		
		//Grab all the paramters from the jsp
		
		String username=(String) request.getSession().getAttribute("username");
		
		String button=request.getParameter("button");
		
		String amount=request.getParameter("amount");
		
		String transcationType=request.getParameter("transcationType");
		
		String league=request.getParameter("league");
		
		String symbol=request.getParameter("symbol");
		
		Double currentPrice=Double.parseDouble(request.getParameter("price"));
		
		Double amountDouble=Double.parseDouble(amount);
		
		//total cost or profit from a transaction
		double totalCost=currentPrice.doubleValue()*amountDouble.doubleValue();
	
		//Buy transaction
		if("button1".equals(button) && transcationType.equals("buy")) {
			
		
			Connection conn = null;
			Statement stmt = null;
		 
			try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");
	
			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
			      
			      stmt = conn.createStatement();
			      String sql;
			      
			      //Find the row corresponding to the user and the selected league
			      sql = "SELECT * FROM LeagueUserList WHERE username='" + username + "' AND leagueID=" + league + "";
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      double liquidMoney=0;
			      
			      //Grab the amount of liquid money the user has
			      while(rs.next()){
			    	  liquidMoney=rs.getDouble("liquidMoney");
			      }
			      
			      //If the user doesnt have enough money then failure
			      if(totalCost>liquidMoney) {
			    	  request.setAttribute("failure", "true");
			    	  request.setAttribute("reason", "Transaction Failed: Not Enough Liquid Money");
			    	  doGet(request,response);
			    	  return;
			      } 
			      	
			      	//Subtract cost from user's money and update the database
			      	liquidMoney-=totalCost;
			    	sql="UPDATE LeagueUserList SET liquidMoney=" + liquidMoney + "WHERE username='" + username + "' AND leagueID=" + league + "";
			    	conn.prepareStatement(sql).executeUpdate();
			    	
			    	//Find the asset user league triplet in the asset table 
			    	sql="SELECT * FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'" ;
			    	rs = stmt.executeQuery(sql);
			    	
			    	//If it exists it needs to be update
			    	if(rs.next()) {
			    		double amountAlready=rs.getDouble("amount");
			    		sql="UPDATE Asset SET amount=" + (amountAlready+amountDouble.doubleValue()) + " WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'" ;
			    		conn.prepareStatement(sql).executeUpdate();
			    	}
			    	
			    	//Else it has to be created
			    	else {
			    		sql="INSERT INTO Asset (username, amount, asset, leagueID) VALUES ('" + username + "', " + amountDouble.doubleValue() + ", '" 
			    				+ symbol + "', " + league + ")";
			    		conn.prepareStatement(sql).executeUpdate();
			    	}
			    	 
			    	//Create a new entry in the transaction table
			    	Timestamp ts=new Timestamp(System.currentTimeMillis());
			    	sql="INSERT INTO Transaction (username, amount, price, asset, transactionType, transactionTime, leagueID) VALUES ('"  +
			    			username + "', " + amountDouble.doubleValue() + ", " + currentPrice + ",  '" + symbol + "', 'buy', '" +  ts + "', " + league + ")";
			    	conn.prepareStatement(sql).executeUpdate();
			    	  
			    	//return success
			    	request.setAttribute("success", true);
			      
			      
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
		
		//Sell Transaction
		else if ("button1".equals(button) && transcationType.equals("sell")) {
			
			Connection conn = null;
			Statement stmt = null;
		 
			try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");
	
			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
			      //Find the user asset league triple in the asset table if it exists
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT * FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'";
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      double amountInPossession=0;
			      
			      //If it exists get the amount of the asset that the user has. If it doesn't exist the amount is set to 0
			      while(rs.next()) {
			    	  amountInPossession=rs.getDouble("amount");
			      }
			      
			      //If the user doesn't have enough of the asset to complete the transaction then return failure
			      if(amountInPossession<amountDouble.doubleValue()) {
			    	  request.setAttribute("failure", "true");
			    	  request.setAttribute("reason", "Transaction Failed: Not Enough Of Asset In Possession To Sell");
			    	  doGet(request,response);
			    	  return; 
			    	  
			      }
			      
			      //Delete from asset table if no more of asset left
			      if(amountInPossession-amountDouble.doubleValue()==0.0) {
			    	  sql = "DELETE FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'";
			    	  conn.prepareStatement(sql).executeUpdate();
			      }
			      
			      //Else update the table
			      else {
			    	  sql = "UPDATE Asset SET amount=" + (amountInPossession-amountDouble.doubleValue()) + " WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'" ;
			    	  conn.prepareStatement(sql).executeUpdate();
			      }
			      
			      //Find the user's liquid money
			      sql = "SELECT * FROM LeagueUserList WHERE username='" + username + "' AND leagueID=" + league + "";
			      rs = stmt.executeQuery(sql);
			      
			      double liquidMoney=0;
			      
			   
			      while(rs.next()){
			    	  liquidMoney=rs.getDouble("liquidMoney");
			      }
			      
			      //Update the liquid money
			      liquidMoney+=totalCost;
			      
			      sql="UPDATE LeagueUserList SET liquidMoney=" + liquidMoney + "WHERE username='" + username + "' AND leagueID=" + league + "";
			      conn.prepareStatement(sql).executeUpdate();
			      
			      //Create a new transaction entry
			      Timestamp ts=new Timestamp(System.currentTimeMillis());
			      sql="INSERT INTO Transaction (username, amount, price, asset, transactionType, transactionTime, leagueID) VALUES ('"  +
			    			username + "', " + amountDouble.doubleValue() + ", " + currentPrice + ",  '" + symbol + "', 'sell', '" +  ts + "', " + league + ")";
			      conn.prepareStatement(sql).executeUpdate();
			    	  
			      request.setAttribute("success", true);
			      
			      
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
		
		doGet(request,response);
	}
	
	public boolean transactionVerificationTest(String username, String type, String league, String symbol, double amount) {
		String data = StockInfoInteractor.fetchStockData(symbol, 1, false);
		double[] prices=StockInfoInteractor.getTimeSeriesData(data, 1, 0);
		
		double price=prices[3];
		double totalCost=price*amount;
		
		if(type=="buy") {
			Connection conn = null;
			Statement stmt = null;
		 
			try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");
	
			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
			      
			      stmt = conn.createStatement();
			      String sql;
			      
			      //Find the row corresponding to the user and the selected league
			      sql = "SELECT * FROM LeagueUserList WHERE username='" + username + "' AND leagueID=" + league + "";
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      double liquidMoney=0;
			      
			      //Grab the amount of liquid money the user has
			      while(rs.next()){
			    	  liquidMoney=rs.getDouble("liquidMoney");
			      }
			      
			      //If the user doesnt have enough money then failure
			      if(totalCost>liquidMoney) {
			    	  return false;
			      } 
			      
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
		
		else {
			Connection conn = null;
			Statement stmt = null;
		 
			try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");
	
			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
			      //Find the user asset league triple in the asset table if it exists
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT * FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'";
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      double amountInPossession=0;
			      
			      //If it exists get the amount of the asset that the user has. If it doesn't exist the amount is set to 0
			      while(rs.next()) {
			    	  amountInPossession=rs.getDouble("amount");
			      }
			      
			      //If the user doesn't have enough of the asset to complete the transaction then return failure
			      if(amountInPossession<amount) {
			    	  return false;
			    	  
			      }
			      
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
		
		return true;
	}

}
