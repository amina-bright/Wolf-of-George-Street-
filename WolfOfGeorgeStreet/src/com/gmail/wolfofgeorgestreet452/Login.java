package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/jsps/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String button=request.getParameter("submit");
		
		//what was typed into the search bar
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		
		//if the submit button was clicked
		if("submit".equals(button)) {
			System.out.println("The button was pressed");
			System.out.println("Search content: " + email);
			System.out.println("Search content: " + password);
			/*
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
		*/
	}
	}

}
