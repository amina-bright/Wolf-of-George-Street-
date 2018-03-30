package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WolfOfGeorgeStreet
 */
@WebServlet("/")
public class WolfOfGeorgeStreet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WolfOfGeorgeStreet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//If user logged in then redirect to the portfolio page else display jsp
		if(request.getSession().getAttribute("username")!=null) {
			response.sendRedirect(request.getContextPath() + "/portfolio");
		} else {
			request.getRequestDispatcher("/jsps/home.jsp").forward(request, response);
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get params
		String button=request.getParameter("button");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		if("button1".equals(button)) {
			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
			      
			      //Check database for entry corresponding to the login info inputted
			      String sql;
			      sql = "SELECT * FROM User	 WHERE username='" + username + "'AND pw='" + password + "'";
			      stmt=conn.prepareStatement(sql);
			      ResultSet rs=stmt.executeQuery();
			      
			      //If found return success and redirect to portfolio
			      if(rs.next()) {
			    	  request.setAttribute("success",true);
			    	  HttpSession session=request.getSession();
			    	  session.setAttribute("username", username);
			    	  response.sendRedirect(request.getContextPath() + "/portfolio");
			      } 
			      
			      //Not found return failure and display jsp
			      else {
			    	  request.setAttribute("success",false);
			    	  request.getRequestDispatcher("/jsps/home.jsp").forward(request, response);
			      }
			      rs.close();
			      stmt.close();
			      conn.close();
			      
			      
			   }
			 	catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/home.jsp").forward(request, response);	
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
		}
	}
	
	//Method used in the testing script simulate login
	public boolean loginTest(String username, String password) {
		Connection conn = null;
		 PreparedStatement stmt = null;
		 
		 try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //Check database for entry corresponding to the login info inputted
		      String sql;
		      sql = "SELECT * FROM User	 WHERE username='" + username + "'AND pw='" + password + "'";
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
