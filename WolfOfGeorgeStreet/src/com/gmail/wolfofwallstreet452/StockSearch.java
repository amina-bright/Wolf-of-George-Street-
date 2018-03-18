package com.gmail.wolfofwallstreet452;

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
@SuppressWarnings("unused")
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
		//String data=StockInfoInteractor.fetchStockData("MSFT",1,false);
		//System.out.println(StockInfoInteractor.getTimeSeriesData(data, 1, 0)[0]);
		request.getRequestDispatcher("/jsps/stocksearch.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String button=request.getParameter("button");
		
		//what was typed into the search bar
		String searchContent=request.getParameter("searchContent");
		
		//List of all the stocks that will be sent to the jsp
		ArrayList<Stock> stocks=new ArrayList<Stock>();
		
		//if the submit button was clicked
		if("button1".equals(button)) {
			//System.out.println("The button was pressed");
			//System.out.println("Search content: " + searchContent);
			
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
			 
			  // System.out.println("Goodbye!");
			
		}
	}

}