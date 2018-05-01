package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class WolfOfGeorgeStreet
 */
@WebServlet("/LeagueVoting")
public class LeagueVoting extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeagueVoting() {
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
		
		
		Connection conn = null;
		 Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		 
	      //open a connection
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //Execute sql query searching for leagues hosted by the current user
	      stmt = conn.createStatement();
	      String sql;
	      String leagueID=request.getParameter("leagueID");
	      
	      ArrayList<String> leagueMemberVote=new ArrayList<String>();
	            
	      sql = "SELECT * FROM LeagueUserList WHERE leagueID= '" + leagueID + "'"; //sql query that will return rows with the leagueMembers
	      ResultSet rs=stmt.executeQuery(sql);
	      
	    
	     
		  
		//Extract the return data
	      while(rs.next()){
	    	  
	    	  if(rs.getString("username").equals(request.getSession().getAttribute("username"))) { //removes the host from the list of users
		    		
		    		
		    	} else {
	    	  leagueMemberVote.add(rs.getString("username"));
	         //int leagueID = Integer.parseInt(rs.getString("leagueID"));
		    	}
	      }
	      
	   
	      
	      
	      
	      //close connections
	      rs.close();
	      stmt.close();
	      conn.close();
	      
	    //pass to the jsp
	      request.setAttribute("leagueID", leagueID);
	      request.setAttribute("leagueMemberVote", leagueMemberVote);
	      //request.setAttribute("leaguesize", leaguesize);
	      
	  	request.getRequestDispatcher("/jsps/LeagueVoting.jsp").forward(request, response);
	   	
	      
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
		//String username = request.getSession().getAttribute("username");
				

				String button=request.getParameter("Submit");
				//sets button to a value when submit is pressed 
				String leagueID = request.getParameter("leagueID");
				String member = request.getParameter("member");
				//String leagueName = request.getParameter("leagueName");
			//System.out.print(button);
				
				//if the submit button was clicked
						if("Allow users to vote".equals(button)) {			
							 Connection conn = null;
							 Statement stmt = null;
							 
							 //System.out.println("hello");
							 System.out.print("test");
							 try{
							      //register jdbc driver
							      Class.forName("com.mysql.jdbc.Driver");

							      //open a connection
							      conn = DriverManager.getConnection(DB_URL,USER,PASS);
							      
							      stmt = conn.createStatement();
							      String sql;
							      String kick= "kick";
							      int VoteID = (int)(Math.random()*999999+100000);
							      sql = "INSERT INTO Voting (VoteInstanceID, leagueID, parameter,voteType)"
								      		+ "VALUES ('" +VoteID  + "', '" + leagueID + "', '" +member+ "','"+kick+"')";
							      
							      stmt=conn.prepareStatement(sql);
							      stmt.executeUpdate(sql);//executes sql query to add host to the list of users of this league
							     

							     
							     
							      
	
							      
							      
//							      sql = "SELECT * FROM League WHERE username= '" + username + "'"; //sql query that will return rows with the username
//							      ResultSet rs=stmt.executeQuery(sql);
						
							      //sql query to add the league to database
							      //sql = "UPDATE League SET leagueName= '" + leagueName + "', size= '" + maxParticipantNum + "', crypto= b'"+ crypto + "' WHERE username= '" +username+ "' AND leagueID= '"+leagueID+"' ";
							      
							      //stmt=conn.prepareStatement(sql);
							      //stmt.executeUpdate(sql);
							   
							      
							      

							      stmt.close();
							      conn.close();
							      request.setAttribute("success",true);
							    	 
						    	  System.out.println("success");
						    	  HttpSession session = request.getSession();
						    	  
						    	  response.sendRedirect(request.getContextPath()+ "/LeagueVotingConfirmation"); 
						    	  //redirects to confirmation page
						    	  
						    	  
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
						}//end of if
						
					
		return;
	}

}