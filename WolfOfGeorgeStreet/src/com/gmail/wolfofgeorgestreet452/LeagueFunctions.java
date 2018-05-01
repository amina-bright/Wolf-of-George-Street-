package com.gmail.wolfofgeorgestreet452;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LeagueFunctions{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
	
	public static double getUserLeagueAssetValue(String user, String leagueID) {
		double portfolioValue = 0;
		ArrayList<Stock> listOfNonCrypto = new ArrayList<Stock>();
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
		      
		      
		      //Grab all the assets the user has for that league and calculate value	    	  
	    	  
	    	  
	    	  sql="SELECT * FROM Asset, StockLookup WHERE username='" + user + "' AND leagueID=" + leagueID + " AND asset=symbol";
	    	  ResultSet rs=stmt.executeQuery(sql);
	    	  double dataParsed[] = null;
	    	  
	    	  while(rs.next()) {
	    		  String symbol=rs.getString("symbol");
	    		  String market=rs.getString("market");
	    		  String title=rs.getString("title");
	    		  double amount=rs.getDouble("amount");
	    		  
	    		  Stock newStock=new Stock(symbol, title, market);
	    		  newStock.setAmount(amount);
	    		  
	    		  if(market.equals("CRYPTO")) {
	    			  String data=StockInfoInteractor.fetchCryptoData(symbol, 0);
		    		  dataParsed=StockInfoInteractor.getTimeSeriesDataCrypto(data, 0, 0);
		    		  if(dataParsed==null) {
			    			 //System.out.println("TEST: " + System.currentTimeMillis());
			    			  TimeUnit.SECONDS.sleep((long)0.1);
			    	  }
		    		  double currentPrice = dataParsed[0];
		    		  portfolioValue += currentPrice*amount;
	    		  }else {
	    			  listOfNonCrypto.add(newStock);
	    		  }
	    	  }
	    	  //System.out.println("Cyrpto assets calculated " + portfolioValue);
	    	  
	    	    //list of non cryptocurrency assets is ready
	    	  String batchString = StockInfoInteractor.fetchStockDataBatch(listOfNonCrypto);
	    	  double[] stockPrices = StockInfoInteractor.getPriceFromBatch(batchString);
	    	  
	    	  for(int i = 0; i < listOfNonCrypto.size();i++){
	    		  portfolioValue += stockPrices[i] * listOfNonCrypto.get(i).getAmount();
	    	  }
	    	  //System.out.println("Stock assets added " + portfolioValue);
	    	   
	    		  /*  
	    		//for each asset, obtain the current price
	    		  double dataParsed[] = null;
	    		  
	    		  if(market.equals("CRYPTO")) {
	    			  String data=StockInfoInteractor.fetchCryptoData(symbol, 0);
		    		  dataParsed=StockInfoInteractor.getTimeSeriesDataCrypto(data, 0, 0);
	    		  }else {
	    			  String data=StockInfoInteractor.fetchStockData(symbol, 0, false);
	    			  dataParsed=StockInfoInteractor.getTimeSeriesData(data, 0, 0);
	    		  }
	    		  if(dataParsed==null) {
		    			 //System.out.println("TEST: " + System.currentTimeMillis());
		    			  TimeUnit.SECONDS.sleep((long)0.1);
		    	  }
		    		  //add to the running sum of the portfolio value
		    		  //System.out.println("getting current price of " + symbol);
		    		  double currentPrice = dataParsed[0];
		    		  portfolioValue += currentPrice*amount;
		    		  */
	    	  	
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
		
		return portfolioValue;
	}
	
}