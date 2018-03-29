package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserAccounts
 */
@WebServlet("/createaccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/createaccount.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get parameters from the jsp
		String button=request.getParameter("button");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		
		//User hit submit
		if("button1".equals(button)) {
			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
			      
			      Timestamp ts=new Timestamp(System.currentTimeMillis());
			      
			      //Create a new entry in the user table
			      String sql;
			      sql = "INSERT INTO User (username, pw, firstName, lastName, email, emailUpdates, creationTime)"
			      		+ "VALUES ('" + username + "', '" + password + "', '" + fname + "', '" + lname + "', '" + email + "', b'1', '" + ts + "')";
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();

			      stmt.close();
			      conn.close();
			      
		    	  request.setAttribute("success",true);
		    	
		    	  //Enter username into http session
		    	  HttpSession session=request.getSession();
		    	  session.setAttribute("username", username);
		    	  
		    	  //redirect to the league page
		    	  response.sendRedirect(request.getContextPath()+ "/league");
			      
			      
			   }catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/createaccount.jsp").forward(request, response);	
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
