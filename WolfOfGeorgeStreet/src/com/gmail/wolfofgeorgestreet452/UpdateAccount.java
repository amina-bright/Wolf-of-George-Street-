package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserAccounts
 */
@WebServlet("/updateaccount")
public class UpdateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAccount() {
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
	      
	      
	      sql = "SELECT * FROM User WHERE username= '" + username + "'"; //sql query that will return rows with the username
	      ResultSet rs=stmt.executeQuery(sql);
	      
	    //List of all the leagues the user is hosting
		 // ArrayList<Integer> hostedLeagueIDs=new ArrayList<Integer>();
		  String firstName= null;
		  String lastName= null;
		  String email= null;
		  String password= null;
		  String userName = null;
		  String strategy = null;
		//Extract the return data
	      while(rs.next()){
	         firstName = rs.getString("firstName");
	         email = rs.getString("email");
	         lastName = rs.getString("lastName");
	         password = rs.getString("pw");
	         username = rs.getString("username");
	         strategy = rs.getString("strategy");
	         //int leagueID = Integer.parseInt(rs.getString("leagueID"));
	         //hostedLeagueIDs.add(leagueID);
	      }
	      
	      sql = "SELECT * From User"; //grab all the leagues the user is in
	      
	      rs=stmt.executeQuery(sql);
	      
	      //List for all the leagues the user is in
	      String userInfoAs= null;
	      //ArrayList<String> joinedLeagueIDs=new ArrayList<String>();
	      
	      while(rs.next()) {
	    	  //joinedLeagueIDs.add(rs.getString("leagueID"));
	    	  userInfoAs=rs.getString("firstName");
	      }
	      
	      //close connections
	      rs.close();
	      stmt.close();
	      conn.close();
	      
	    //pass arrays to the jsp
	      request.setAttribute("username",userName);
	      request.setAttribute("last",lastName);
	      request.setAttribute("first",firstName);
	      request.setAttribute("password",password);
	      request.setAttribute("email",email);
	      request.setAttribute("strategy", strategy);
	      
	      //display jsp
	      request.getRequestDispatcher("/jsps/updateaccount.jsp").forward(request, response);	
	      
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
		
		//Get parameters from the jsp
		String button  = request.getParameter("Submit");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String username=(String) request.getSession().getAttribute("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String strategy=request.getParameter("strategy");
		
		//User hit submit
		if("submit".equals(button)) {
			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
			      
			      Timestamp ts=new Timestamp(System.currentTimeMillis());
			      
			      //Create a new entry in the user table
			      String sql;
			      sql = "Update User Set firstName='"+ fname+"', lastName='"+ lname +"', pw='"+
			    		  password +"',  email ='"+ email +"', strategy ='" + strategy + "' WHERE Username='" + username + "'";
			     
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();

			      stmt.close();
			      conn.close();
			      
		    	  request.setAttribute("success",true);
		    	
		    	  //Enter username into http session
		    	  HttpSession session=request.getSession();
		    	  session.setAttribute("username", username);
		    	  
		    	  //redirect to the league page
		    	  response.sendRedirect(request.getContextPath()+ "/portfolio");
			      
			      
			   }catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/updateaccount.jsp").forward(request, response);	
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
	}
}