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
@WebServlet("/manageLeague")
public class ManageLeague extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageLeague() {
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
		
		String leagueID=request.getParameter("leagueID");
		//System.out.print(leagueID);
		
		request.setAttribute("leagueID", leagueID);
		
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
		      
		 sql="SELECT LeagueName from League WHERE leagueID = '"+ leagueID + "' ";
	     ResultSet rs = stmt.executeQuery(sql);
	     String leagueName = " ";
	      
	      while(rs.next()) {
	    	  leagueName = rs.getString("LeagueName");
	      }		
		
	      stmt.close();
	      conn.close();
  
  
  
  //Send the lists to the jsp
  request.setAttribute("leagueName", leagueName);
  request.setAttribute("leagueID", leagueID);
  
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
	      
		request.getRequestDispatcher("/jsps/manageLeague.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hello");

		//String username = request.getSession().getAttribute("username");
		String username = (String)request.getSession().getAttribute("username"); //retrieves username from session
		String leagueID=(String)request.getParameter("leagueID");
		System.out.println(leagueID);
//		if((int)request.getSession().getAttribute("leagueID") < 0)
//		{
//			continue;
//		}
//		int leagueID=(int)request.getSession().getAttribute("leagueID");
		String button=request.getParameter("Submit");
		//sets button to a value when submit is pressed 
		
		String leagueName = request.getParameter("leagueName");
		String OldleagueName = request.getParameter("OldleagueName");
		
		int crypto = Integer.parseInt(request.getParameter("crypto"));//Stores user-inputed include crypto as int
		int maxParticipantNum = Integer.parseInt(request.getParameter("maxParticipantNum"));
		System.out.println(leagueName);
		System.out.println(crypto);
		System.out.println(maxParticipantNum);
		//if the submit button was clicked
				if("submit".equals(button)) {			
					 Connection conn = null;
					 Statement stmt = null;
					 
					 System.out.println("hello");
					 
					 try{
					      //register jdbc driver
					      Class.forName("com.mysql.jdbc.Driver");

					      //open a connection
					      conn = DriverManager.getConnection(DB_URL,USER,PASS);
					      
					      stmt = conn.createStatement();
//					      String sql;
//					      sql = "SELECT * FROM WolfOfGeorgeStreetDB.League WHERE leagueID = ?";
//					      PreparedStatement stmt2 = conn.prepareStatement(sql);
//					      stmt2.setInt(1, leagueID);
//					      ResultSet rs = stmt2.executeQuery(); 
					     

					     
					      String sql;
					      
					      sql="SELECT LeagueID from League WHERE leagueName = '"+ OldleagueName + "' ";
					       ResultSet rs = stmt.executeQuery(sql);
					      
					      while(rs.next()) {
					    	  leagueID = rs.getString("leagueID");
					      }
					      
					      System.out.println(leagueID);
					      
					      
//					      sql = "SELECT * FROM League WHERE username= '" + username + "'"; //sql query that will return rows with the username
//					      ResultSet rs=stmt.executeQuery(sql);
				
					      //sql query to add the league to database
					      sql = "UPDATE League SET leagueName= '" + leagueName + "', size= '" + maxParticipantNum + "', crypto= b'"+ crypto + "' WHERE username= '" +username+ "' AND leagueID= '"+leagueID+"' ";
					      System.out.println(leagueID);
					      System.out.println(username);
					      stmt=conn.prepareStatement(sql);
					      stmt.executeUpdate(sql);
					   
					      
					      

					      stmt.close();
					      conn.close();
					      request.setAttribute("success",true);
					    	 
				    	  System.out.println("success");
				    	  HttpSession session = request.getSession();
				    	  session.setAttribute("leagueID", leagueID); //stores the league ID as session attribute so it can be displayed in confirmation screen
				    	  response.sendRedirect(request.getContextPath()+ "/ManageLeagueConfirmation"); 
				    	  //redirects to confirmation page
				    	  
				    	  //request.getRequestDispatcher(request.getContextPath()+ "/CreateLeagueConfirmation").forward(request, response);
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
				
			}
}