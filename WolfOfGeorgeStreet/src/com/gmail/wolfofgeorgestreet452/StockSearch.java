package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class WolfOfGeorgeStreet
 */
@WebServlet("/stocksearch")
public class StockSearch extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockSearch() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String username=(String) request.getSession().getAttribute("username");

				
		Connection conn = null;
		Statement stmt = null;
				 
		try{
			//register jdbc driver
			Class.forName("com.mysql.jdbc.Driver");

			//open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//Execute sql query searching fro similar strings in symbols, titles, or market
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM User WHERE username='" + username + "'";
	      
			ResultSet rs = stmt.executeQuery(sql);
	      
	      if(rs.next()) {
	    	  String strategy =  rs.getString("strategy");
	    	  request.setAttribute("strategy", strategy);
	    	  
	    	  ArrayList<Stock> reccomended= new ArrayList<Stock>();
	    	  
	    	  if(strategy.equals("Moderate")) {
	    		  reccomended.add(new Stock("TTWO", "Take-Two Interactive Software, Inc.", "NASDAQ")); 
	    		  reccomended.add(new Stock("FIZZ", "National Beverage Corp.", "NASDAQ")); 
	    		  reccomended.add(new Stock("PRAH", "PRA Health Sciences, Inc.", "NASDAQ")); 
	    		  reccomended.add(new Stock("STMP", "Stamps.com Inc.", "NASDAQ")); 
	    		  reccomended.add(new Stock("MTCH", "Match Group, Inc.", "NASDAQ")); 
	    	  }
	    	  
	    	  else if(strategy.equals("Conservative")) {
	    		  reccomended.add(new Stock("KO", "Coca-Cola Company (The)", "NYSE")); 
	    		  reccomended.add(new Stock("JPM", "J P Morgan Chase & Co", "NYSE")); 
	    		  reccomended.add(new Stock("JNJ", "Johnson & Johnson", "NYSE")); 
	    		  reccomended.add(new Stock("AMGN", "AMGN", "NASDAQ")); 
	    		  reccomended.add(new Stock("STOR", "STORE Capital Corporation", "NYSE")); 
	    		  
	    	  }
	    	  
	    	  else {
	    		  reccomended.add(new Stock("BTC", "Bitcoin", "CRYPTO"));
	    		  reccomended.add(new Stock("ETH", "Ethereum", "CRYPTO"));
	    		  reccomended.add(new Stock("XRP", "Ripple", "CRYPTO"));
	    		  reccomended.add(new Stock("CVEO", "Civeo Corporation", "NYSE"));
	    		  reccomended.add(new Stock("CBK", "Christopher & Banks Corporation", "NYSE"));
	    	  }
	    	  request.setAttribute("recommended", reccomended.toArray());
	    	  
	    	  request.getRequestDispatcher("/jsps/stocksearch.jsp").forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String button=request.getParameter("button");
		String username=(String) request.getSession().getAttribute("username");
		
		//what was typed into the search bar
		String searchContent=request.getParameter("searchContent");
		
		//List of all the stocks that will be sent to the jsp
		ArrayList<Stock> stocks=new ArrayList<Stock>();
		
		//if the submit button was clicked
		if("button1".equals(button)) {
			
			 Connection conn = null;
			 Statement stmt = null;
			 
			 try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			      //Execute sql query searching fro similar strings in symbols, titles, or market
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT * FROM StockLookup WHERE symbol LIKE '%" + searchContent + "%'"
			    		  + "OR title LIKE '%" + searchContent + "%'" + "OR market LIKE '%" + searchContent
			    		  + "'";
			      ResultSet rs = stmt.executeQuery(sql);

			      //Extract the return data
			      while(rs.next()){
			         String symbol = rs.getString("symbol");
			         String title = rs.getString("title");
			         String market=rs.getString("market");
			         
			         //Create a stock out of each set
			         Stock newStock=new Stock(symbol,title,market);
			         stocks.add(newStock);
			      }
			      	  
			      //close connections
			      rs.close();
			      stmt.close();
			      conn.close();
			      
			      //pass stocks to the jsp
			      request.setAttribute("stocks",stocks);
			      
			      //display jsp
			      request.getRequestDispatcher("/jsps/stocksearch.jsp").forward(request, response);	
			      
			      
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
	}

}