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
		
		
		String leagueName = request.getParameter("leagueName");
		int gameMode = Integer.parseInt(request.getParameter("gameMode"));
		java.util.Date startDate = Date.valueOf(request.getParameter("startDate"));
		java.util.Date endDate = Date.valueOf(request.getParameter("endDate"));
		int crypto = Integer.parseInt(request.getParameter("crypto"));
		int maxParticipantNum = Integer.parseInt(request.getParameter("maxParticipantNum"));
		double startCapital = Double.parseDouble(request.getParameter("startCapital"));
		
		java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());
		java.sql.Date endDateSQL = new java.sql.Date(endDate.getTime());
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
			      //add the league to database
			      sql = "INSERT INTO League (leagueID, leagueName, size, username, startDate, endDate, crypto, startingPrinciple, gameMode)"
				      		+ "VALUES ('" + leagueID + "', '" + leagueName + "', '" + maxParticipantNum + "', '" + request.getSession().getAttribute("username") + "', '" + startDateSQL + 
				      		"','" + endDateSQL + "', b'" + crypto + "', '" + startCapital + "', b'" + gameMode + "')";
			      
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();
			      
			      //add host to the list of users of this league
			      sql = "INSERT INTO LeagueUserList (username, leagueID, liquidMoney)"
				      		+ "VALUES ('" + request.getSession().getAttribute("username") + "', '" + leagueID + "', '" + startCapital + "')";
			      
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();

			      stmt.close();
			      conn.close();
			      
		    	  request.setAttribute("success",true);
		    	 
		    	  System.out.println("success");
		    	  HttpSession session = request.getSession();
		    	  session.setAttribute("leagueID", leagueID);
		    	  response.sendRedirect(request.getContextPath()+ "/CreateLeagueConfirmation");
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