package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/portfolio")
public class Portfolio extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
	
	public Portfolio() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String username=(String) request.getSession().getAttribute("username");
		
		//List for all the lagues the user is in
		ArrayList<String> leagueNames=new ArrayList<String>();
		ArrayList<String> leagueIds=new ArrayList<String>();
		
		//List of lists for all the assets the user has for each league
		ArrayList<ArrayList<Stock>> assets=new ArrayList<ArrayList<Stock>>();
		
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
		      sql="SELECT * From LeagueUserList s, League w WHERE s.username='" + username + "' AND s.leagueID=w.leagueID";
		      ResultSet rs=stmt.executeQuery(sql);
		      
		      while(rs.next()) {
		    	  leagueIds.add(rs.getString("leagueID"));
		    	  leagueNames.add(rs.getString("leagueName"));
		      }
		      
		      //For each league grab all the assets the user has for that league and put them in the entry in the assets list
		      //corresponding to that league
		      for(int i=0;i<leagueIds.size();i++) {
		    	  String currentLeague=leagueIds.get(i);
		    	  
		    	  ArrayList<Stock> currentLeagueAssets=new ArrayList<Stock>();
		    	  
		    	  sql="SELECT * FROM Asset, StockLookup WHERE username='" + username + "' AND leagueID=" + currentLeague + " AND asset=symbol";
		    	  rs=stmt.executeQuery(sql);
		    	  
		    	  while(rs.next()) {
		    		  String symbol=rs.getString("symbol");
		    		  String market=rs.getString("market");
		    		  String title=rs.getString("title");
		    		  double amount=rs.getDouble("amount");
		    		  
		    		  Stock newStock=new Stock(symbol, title, market);
		    		  newStock.setAmount(amount);
		    		  
		    		  currentLeagueAssets.add(newStock);
		    	  }
		    	  
		    	  assets.add(i, currentLeagueAssets);
		    	  
		      }
		      
		      //Send the lists to the jsp
		      request.setAttribute("leagueNames", leagueNames);
		      request.setAttribute("leagueIds", leagueIds);
		      request.setAttribute("assets", assets);
		     
		      
		     //Display the jsp
		     request.getRequestDispatcher("/jsps/portfolio.jsp").forward(request, response);
		      
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
		doGet(request,response);
	}
	
	//Function used to test portfolio in unit testing script
	public HashMap<String,ArrayList<Stock>> portfolioTest(String username) {
		HashMap<String,ArrayList<Stock>> output=new HashMap<String,ArrayList<Stock>>();
		
		//List for all the lagues the user is in
		ArrayList<String> leagueIds=new ArrayList<String>();
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
		      sql="SELECT * From LeagueUserList s, League w WHERE s.username='" + username + "' AND s.leagueID=w.leagueID";
		      ResultSet rs=stmt.executeQuery(sql);
		      
		      while(rs.next()) {
		    	  leagueIds.add(rs.getString("leagueID"));
		      }
		      
		      for(int i=0;i<leagueIds.size();i++) {
		    	  String currentLeague=leagueIds.get(i);
		    	  
		    	  ArrayList<Stock> currentLeagueAssets=new ArrayList<Stock>();
		    	  
		    	  sql="SELECT * FROM Asset, StockLookup WHERE username='" + username + "' AND leagueID=" + currentLeague + " AND asset=symbol";
		    	  rs=stmt.executeQuery(sql);
		    	  
		    	  while(rs.next()) {
		    		  String symbol=rs.getString("symbol");
		    		  String market=rs.getString("market");
		    		  String title=rs.getString("title");
		    		  double amount=rs.getDouble("amount");
		    		  
		    		  Stock newStock=new Stock(symbol, title, market);
		    		  newStock.setAmount(amount);
		    		  
		    		  currentLeagueAssets.add(newStock);
		    	  }
		    	  
		    	  output.put(leagueIds.get(i),currentLeagueAssets);
		    	  
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
		return output;
	}
}