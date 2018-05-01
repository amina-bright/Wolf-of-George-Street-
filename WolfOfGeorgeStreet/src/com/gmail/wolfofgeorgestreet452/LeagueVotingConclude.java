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
@WebServlet("/LeagueVotingConclude")
public class LeagueVotingConclude extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeagueVotingConclude() {
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
	      
	 
	            
	      sql="SELECT * from Voting WHERE leagueID = '"+ leagueID + "' ";//Sql query to view votes in progress for the league
		    ResultSet rs=stmt.executeQuery(sql);
		    ArrayList<String> VoteParam1= new ArrayList<String>();
		    ArrayList<String> Votetype1= new ArrayList<String>();
		    ArrayList<Integer> VoteInstanceIDs1= new ArrayList<Integer>();
		     
		  //Extract the return data
		      while(rs.next()) {
		    	  VoteParam1.add(rs.getString("parameter")); //Gets parameter,like user to kick, from database
		    	  Votetype1.add(rs.getString("voteType")); //Gets amount of wins from database
		    	  VoteInstanceIDs1.add(rs.getInt("voteInstanceID"));//Gets amount of loss from database
		      }
	    
		      
		      ArrayList<Integer> yes= new ArrayList<Integer>();
		      ArrayList<Integer> no= new ArrayList<Integer>();
		      for (int d =0;d<VoteInstanceIDs1.size();d++)
		      {
		      
		      sql="SELECT SUM(Yes) from Votes WHERE VoteInstanceID = '"+ VoteInstanceIDs1.get(d) + "' ";//Sql query to view votes in progress for the league
			  rs=stmt.executeQuery(sql);
			   
			     
			  //Extract the return data
			      while(rs.next()) {
			    	  yes.add(rs.getInt("SUM(Yes)"));
			    	  
			    	
			      }
		      
		 
		      sql="SELECT SUM(No) from Votes WHERE VoteInstanceID = '"+ VoteInstanceIDs1.get(d) + "' ";//Sql query to view votes in progress for the league
			  rs=stmt.executeQuery(sql);
			   
			     
			  //Extract the return data
			      while(rs.next()) {
			    	  no.add(rs.getInt("SUM(No)"));
			    	  
			    	
			      }
		      
		
	      
		      }
	      
	      //close connections
	      rs.close();
	      stmt.close();
	      conn.close();
	      ArrayList<String> kickable= new ArrayList<String>();
	      for (int a=0;a<VoteParam1.size();a++) 
	      {
	      if (yes.get(a)>no.get(a))
	    	  kickable.add(VoteParam1.get(a));
	      }
		      
	    //pass to the jsp
	      request.setAttribute("leagueID", leagueID);
	      request.setAttribute("VoteParam1", VoteParam1);
	      request.setAttribute("Votetype1", Votetype1);
	      request.setAttribute("VoteInstanceIDs1", VoteInstanceIDs1);
	      request.setAttribute("yes", yes);
	      request.setAttribute("no", no);
	      request.setAttribute("kickable", kickable);
	     
	      
	  	request.getRequestDispatcher("/jsps/LeagueVotingConclude.jsp").forward(request, response);
	   	
	      
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
						if("Kick".equals(button)) {			
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
							      
							      int zero=0;
							      
							      sql = "Delete from LeagueUserList where username ='"+member+ "' and leagueID = '"+leagueID+ "' ";
							      
							      
							      stmt=conn.prepareStatement(sql);
							      stmt.executeUpdate(sql);//executes sql query to add vote to the database
							     						      

							      stmt.close();
							      conn.close();
							      request.setAttribute("success",true);
							    	 
						    	  System.out.println("success");
						    	  HttpSession session = request.getSession();
						    	  
						    	  response.sendRedirect(request.getContextPath()+ "/LeagueVotingConcludeConfirmation"); 
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