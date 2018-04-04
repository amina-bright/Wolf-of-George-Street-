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
@WebServlet("/league")
public class League extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public League() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username")==null) { //if no user is logged in, redirects to login page
			response.sendRedirect(request.getContextPath());
			return;
		}		
		
		 Connection conn = null;
		 Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		 
	      //open a connection
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //Execute sql query searching for leagues hosted by the current user
	      stmt = conn.createStatement();
	      String sql;
	            
	      String username = (String)request.getSession().getAttribute("username"); //retrieves username from session
	      
	      
	      sql = "SELECT * FROM League WHERE username= '" + username + "'"; //sql query that will return rows with the username
	      ResultSet rs=stmt.executeQuery(sql);
	      
	    //List of all the leagues the user is hosting
	      ArrayList<String> hostedLeagueNames=new ArrayList<String>();
		  ArrayList<Integer> hostedLeagueIDs=new ArrayList<Integer>();
		  
		//Extract the return data
	      while(rs.next()){
	         String leagueName = rs.getString("leagueName");
	         int leagueID = Integer.parseInt(rs.getString("leagueID"));
	         hostedLeagueNames.add(leagueName);
	         hostedLeagueIDs.add(leagueID);
	      }
	      
	      sql = "SELECT * From LeagueUserList s, League w WHERE s.username='" + username + "' AND s.leagueID=w.leagueID"; //grab all the leagues the user is in
	      
	      rs=stmt.executeQuery(sql);
	      
	      //List for all the leagues the user is in
	      ArrayList<String> joinedLeagueNames=new ArrayList<String>();
	      ArrayList<String> joinedLeagueIDs=new ArrayList<String>();
	      
	      while(rs.next()) {
	    	  joinedLeagueIDs.add(rs.getString("leagueID"));
	    	  joinedLeagueNames.add(rs.getString("leagueName"));
	      }
	      
	      //close connections
	      rs.close();
	      stmt.close();
	      conn.close();
	      
	    //pass arrays to the jsp
	      request.setAttribute("hostedLeagueNames",hostedLeagueNames);
	      request.setAttribute("hostedLeagueIDs",hostedLeagueIDs);
	      request.setAttribute("joinedLeagueNames",joinedLeagueNames);
	      request.setAttribute("joinedLeagueIDs",joinedLeagueIDs);
	      
	      //display jsp
	      request.getRequestDispatcher("/jsps/League.jsp").forward(request, response);	
	      
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
		return;
	}

}

