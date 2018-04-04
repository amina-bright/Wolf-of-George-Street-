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
		    	  if(username.equals(rs.getString("username"))) {
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
		      
		      //Send the lists to the jsp
		      request.setAttribute("leagueName", leagueName);
		      request.setAttribute("leagueMemberNames", leagueMemberNames);
		      request.setAttribute("leagueAssets", leagueAssets);
		      
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
		doGet(request,response);
	}
	
	
	
}