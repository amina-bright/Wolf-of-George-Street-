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
//import java.util.Date;
import java.util.Calendar;
import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class WolfOfGeorgeStreet
 */
@WebServlet("/createleague")
public class CreateLeague extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateLeague() {
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
		request.getRequestDispatcher("/jsps/createLeague.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    //String username = request.getSession().getAttribute("username");
		String button=request.getParameter("Submit");
		//sets button to a value when submit is pressed 
		
		
		String leagueName = request.getParameter("leagueName"); //Stores user-inputed league Name as string 
		int gameMode = Integer.parseInt(request.getParameter("gameMode")); //Stores user-inputed gameMode as int
		java.util.Date startDate = Date.valueOf(request.getParameter("startDate")); //Stores user-inputed start date as date type
		
		java.util.Date endDate;
		java.sql.Date endDateSQL;
		
		if (gameMode == 1)//Checks if gameMode is normal
		{
		
			endDate = Date.valueOf(request.getParameter("endDate")); //Stores user-inputed end date as date type
			endDateSQL = new java.sql.Date(endDate.getTime()); //Converts from java date to sql datetime
		}
		else //If gameMode is Head2head
		{
			int roundNum = Integer.parseInt(request.getParameter("roundNum")); //Stores user-inputed round number as int
			String roundDur = request.getParameter("duration"); //Stores user-inputted round duration as string
			//System.out.print(roundNum);
			//System.out.print(roundDur);
			
			Calendar calendar = Calendar.getInstance();  //Creates calendar instance
				calendar.setTime(startDate); //Sets calendar time
				
				switch (roundDur) //Calculates new endDate depending on user input of round number and round duration
				{
				case "1Min":
					calendar.add(Calendar.MINUTE, 1*roundNum); 
					break;
					
				case "Hourly":
					calendar.add(Calendar.HOUR, 1*roundNum); 
					break;
					
				case "Daily":
					calendar.add(Calendar.DATE, 1*roundNum);
					break;
					
				case "Weekly":
					calendar.add(Calendar.DATE, 7*roundNum);
					break;
					
				case "Biweekly" :
					calendar.add(Calendar.DATE, 14*roundNum);
					break;
					
				case "Monthly":
					calendar.add(Calendar.MONTH, 1*roundNum);
					break;					
					
				}
				//System.out.println(calendar.getTime());
				endDate =  calendar.getTime();//Converts from java date to sql datetime
				endDateSQL = new java.sql.Date(endDate.getTime()); //Converts from java date to sql datetime
				System.out.print(endDateSQL);
		}
			
			
		
		
		int crypto = Integer.parseInt(request.getParameter("crypto"));//Stores user-inputed include crypto as int
		int maxParticipantNum = Integer.parseInt(request.getParameter("maxParticipantNum")); //Stores user-inputed max participants as int
		double startCapital = Double.parseDouble(request.getParameter("startCapital")); //Stores user-inputed starting principle as double
		
		java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());//Converts from java date to sql datetime
		
		int leagueID = (int)(Math.random()*999999+100000); //generate random number for leagueID
		
		
		//if the submit button was clicked
		if("submit".equals(button)) {			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			      
			      String sql;
			      //sql query to add the league to database
			      sql = "INSERT INTO League (leagueID, leagueName, size, username, startDate, endDate, crypto, startingPrinciple, gameMode)"
				      		+ "VALUES ('" + leagueID + "', '" + leagueName + "', '" + maxParticipantNum + "', '" + request.getSession().getAttribute("username") + "', '" + startDateSQL + 
				      		"','" + endDateSQL + "', b'" + crypto + "', '" + startCapital + "', b'" + gameMode + "')";
			      
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();//executes sql query to add league to database
			      
			      //sql query to add host to the list of users of this league
			      sql = "INSERT INTO LeagueUserList (username, leagueID, liquidMoney)"
				      		+ "VALUES ('" + request.getSession().getAttribute("username") + "', '" + leagueID + "', '" + startCapital + "')";
			      
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();//executes sql query to add host to the list of users of this league
			      
			      if (gameMode == 0) { //Checks if head-to-head mode
					   int initial =0;
					   
				    	  sql = "INSERT INTO Head2Head (LeagueID, username, win, loss,percentage)"//sql query to add to h2h mode
					    		  + "VALUES ('" + leagueID + "', '" + request.getSession().getAttribute("username") + "', '"+initial+" ' , '"+ initial +"')";
				    	  
				    	  stmt=conn.prepareStatement(sql);
					      stmt.executeUpdate();//executes sql query to add to H2H table if in H2H mode
			      
			      }
			      
			      
			      stmt.close();
			      conn.close();
			      
		    	  request.setAttribute("success",true);
		    	 
		    	  System.out.println("success");
		    	  HttpSession session = request.getSession();
		    	  session.setAttribute("leagueID", leagueID); //stores the league ID as session attribute so it can be displayed in confirmation screen
		    	  response.sendRedirect(request.getContextPath()+ "/CreateLeagueConfirmation"); 
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

	public boolean CreateLeagueTest(String username,String leagueName, String gamemode,String startdate,String enddate,int crypto,int participants,double principle) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if (participants>100 || participants <0) //Checks if participant input is valid
			return false;
		 
		if (principle>10000 || principle <0) //Checks if principle input is valid
			return false;
		
		if(!gamemode.equals("normal")&&!gamemode.equals("headtohead")) { //Checks if gamemode input is valid
			return false; 
		}
		
		if(crypto != 0 &&crypto!=1) { //Checks if crypto input is valid
			return false; 
		}
		
		 try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //Check database for entry corresponding to the username inputted
		      String sql;
		      sql = "SELECT * FROM User WHERE username='" + username + "'";
		      stmt=conn.prepareStatement(sql);
		      ResultSet rs=stmt.executeQuery();
		      
		      //If found return true
		      if(rs.next()) {
		    	  rs.close();
			      stmt.close();
			      conn.close();
		    	  return true;
		    	  
		      } 
		      
		      else {
		    	  rs.close();
			      stmt.close();
			      conn.close();
		    	  return false;
		      }
		
		      
		   }
		 	catch(SQLException se){
		      se.printStackTrace();
	
		   }
		 	catch(Exception e){
		      e.printStackTrace();
		   }
		 	finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }
		      catch(SQLException se2){
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }
		      
		      catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		 
		 return false;
		
		
	}

}