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
		
		//array of containers for usernames and their corresponding portfolio values
		ArrayList<UserPortfolio> user_portfoliovalue_list = new ArrayList<UserPortfolio>();
		
		
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
		      sql="SELECT * from LeagueUserList WHERE leagueID = '"+ leagueID + "'";
		      ResultSet rs=stmt.executeQuery(sql);
		       
		      int size = 0;
		      while(rs.next()) {
		    	  int rank = size;
		    	  double value = 0;
		    	  
		    	  //adds the specific user's liquid money and current value of asset and return as portfolio value
		    	  value = rs.getDouble("liquidMoney") + LeagueFunctions.getUserLeagueAsset(leagueID, rs.getString("username"));
		    	  UserPortfolio currentPair = new UserPortfolio(rs.getString("username"),value);
		    	  /*
		    	  System.out.println(currentPair.getUsername());
		    	  System.out.println(currentPair.getPortfolioValue());
		    	  */
		    	  if(size == 0) { //first element
		    		  
		    		  user_portfoliovalue_list.add(currentPair);
		    	  }else { //not the first element; must determine correct ranking
		    		 
		    		  for(int i = size; i > 0; i--) {
		    		  	//compare the portfolio values of the two elements
			    		  if(currentPair.getPortfolioValue() > user_portfoliovalue_list.get(i - 1).getPortfolioValue()){ //the new portfolio is greater
			    			  //insert
			    			 rank = i - 1;
			    		  }else {//the new portfolio is lesser than or equal  
			    			  rank = i;
			    			  break; //no need to continue the for loop
			    		  }
			    	  } 
		    		//use the determined rank value to insert the current pair at the right rank in the list
		    		  
		    		  user_portfoliovalue_list.add(rank,currentPair);
		    	  }
	    		  size++;
		      }
		      
		      for(int i = size-1; i >= 0; i--) {
		    	  if(user_portfoliovalue_list.get(i).getUsername().equals(username)) { //current row is referring to the current user's information
	    			  //System.out.println("current user info found");
		    		  request.setAttribute("userRank", i+1);
				      request.setAttribute("userPortfolioValue", user_portfoliovalue_list.get(i).getPortfolioValue());
		    	  }else {
		    		  //System.out.println(user_portfoliovalue_list.get(i).getUsername() + " is not the current user");
		    	  }
		      }
		      
		      sql="SELECT LeagueName from League WHERE leagueID = '"+ leagueID + "' ";
		      rs = stmt.executeQuery(sql);
		      
		      while(rs.next()) {
		    	  leagueName = rs.getString("LeagueName");
		      }
		      
		      
		      //Send the lists to the jsp
		      request.setAttribute("leagueName", leagueName);
		      request.setAttribute("user_portfoliovalue_list", user_portfoliovalue_list);
		      
		      
		      
		      
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