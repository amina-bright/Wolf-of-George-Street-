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
@WebServlet("/LeagueVotingUser")
public class LeagueVotingUser extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeagueVotingUser() {
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
	      
	      ArrayList<String> leagueVotes=new ArrayList<String>();
	            
	      sql="SELECT * from Voting WHERE leagueID = '"+ leagueID + "' ";//Sql query to view votes in progress for the league
		    ResultSet rs=stmt.executeQuery(sql);
		    ArrayList<String> VoteParam= new ArrayList<String>();
		    ArrayList<String> Votetype= new ArrayList<String>();
		    ArrayList<Integer> VoteInstanceIDs= new ArrayList<Integer>();
		     
		  //Extract the return data
		      while(rs.next()) {
		    	  VoteParam.add(rs.getString("parameter")); //Gets parameter,like user to kick, from database
		    	  Votetype.add(rs.getString("voteType")); //Gets amount of wins from database
		    	  VoteInstanceIDs.add(rs.getInt("voteInstanceID"));//Gets amount of loss from database
		      }
	    
	     
		  
		   //   for (int f= 0;f<VoteParam.size();f++) {
		   // 	  System.out.print(VoteParam.get(f));
		   // 	  System.out.print(Votetype.get(f));
		   // 	  System.out.print(VoteInstanceIDs.get(f));
		   // 	  
		   //   }
	   
	   
	      
	      
	      
	      //close connections
	      rs.close();
	      stmt.close();
	      conn.close();
	      
	    //pass to the jsp
	      request.setAttribute("leagueID", leagueID);
	      request.setAttribute("VoteParam", VoteParam);
	      request.setAttribute("Votetype", Votetype);
	      request.setAttribute("VoteInstanceIDs", VoteInstanceIDs);
	     
	      
	  	request.getRequestDispatcher("/jsps/LeagueVotingUser.jsp").forward(request, response);
	   	
	      
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
				int kick = Integer.parseInt(request.getParameter("kick"));
				String VoteID = request.getParameter("VoteInstanceID");
				int other=0;
				if (kick==0)
				{
					other =1;
				}
					
				//String leagueName = request.getParameter("leagueName");
			//System.out.print(button);
				
				//if the submit button was clicked
						if("Submit vote".equals(button)) {			
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
							      
							      sql = "INSERT INTO Votes (VoteInstanceID, username,yes,no)"
								      		+ "VALUES ('" + VoteID + "', '" +request.getSession().getAttribute("username")+ "',b'"+kick+"',b'"+other+"')";
							    
							    		 
							      
							      
							      stmt=conn.prepareStatement(sql);
							      stmt.executeUpdate(sql);//executes sql query to add vote to the database
							     						      

							      stmt.close();
							      conn.close();
							      request.setAttribute("success",true);
							    	 
						    	  System.out.println("success");
						    	  HttpSession session = request.getSession();
						    	  
						    	  response.sendRedirect(request.getContextPath()+ "/LeagueVotingUserConfirmation"); 
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