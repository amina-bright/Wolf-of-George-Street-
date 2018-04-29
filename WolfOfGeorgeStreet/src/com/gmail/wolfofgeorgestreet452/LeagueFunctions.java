package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LeagueFunctions{	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
	
	public LeagueFunctions() {
		super();
	}
	
	public static double getUserLeagueAsset(String leagueID, String username) {
		double asset = 0;
		Connection conn = null;
	    Statement stmt = null;
	    
		try{
		      //register jdbc driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;
		      ResultSet rs;
		      
		    	  sql="SELECT * FROM Asset, StockLookup WHERE username='" + username + "' AND leagueID=" + leagueID + " AND asset=symbol";
		    	  rs=stmt.executeQuery(sql);
		    	  
		    	  //this will store the sum of all asset owned by the user for this league
		    	  
		    	  while(rs.next()) {
		    		  String symbol=rs.getString("symbol");
		    		  String market=rs.getString("market");
		    		  double amount=rs.getDouble("amount");
		    		 
		    		  
		    		//for each asset, obtain the current price
		    		  double dataParsed[] = null;
		    		  //System.out.println("retrieving data for " + symbol);
		    		  if(market.equals("CRYPTO")) {
		    			  String data=StockInfoInteractor.fetchCryptoData(symbol, 1);
			    		  dataParsed=StockInfoInteractor.getTimeSeriesDataCrypto(data, 1, 0);
		    		  }else {
		    			  String data=StockInfoInteractor.fetchStockData(symbol, 1, false);
		    			  dataParsed=StockInfoInteractor.getTimeSeriesData(data, 1, 0);
		    		  }
			    		  //add to the running sum of the asset value
			    		  double currentPrice = dataParsed[3];
			    		  asset += currentPrice*amount;
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
		
		return asset;
	}
	
}