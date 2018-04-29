package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserAccounts
 */
@WebServlet("/joinleague")
public class JoinLeague extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinLeague() {
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
		
		request.getRequestDispatcher("/jsps/joinleague.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String button=request.getParameter("Submit");
		
		int leagueID = Integer.parseInt(request.getParameter("leagueID"));
				
				//if the submit button was clicked
				if("submit".equals(button)) {			
					 Connection conn = null;
					 Statement stmt = null;
					 
					 try{
					      //register jdbc driver
					      Class.forName("com.mysql.jdbc.Driver");

					      //open a connection
					      conn = DriverManager.getConnection(DB_URL,USER,PASS);

					      stmt = conn.createStatement();
					      String sql;
					      //sql query to find the starting principle for the leagueID the user wishes to join
					      sql = "SELECT * FROM WolfOfGeorgeStreetDB.League WHERE leagueID = ?";
					      PreparedStatement stmt2 = conn.prepareStatement(sql);
					      stmt2.setInt(1, leagueID);
					      ResultSet rs = stmt2.executeQuery(); //executes the sql query and stores the row that has the leagueID as primary key as rs
					      while (rs.next()) {
					          int principle;
					        principle = rs.getInt(8); //stores the starting principle for the league as principle
					          //System.out.println(principle);
					          sql = "INSERT INTO LeagueUserList (username, leagueID, liquidMoney)"
							      		+ "VALUES ('" + request.getSession().getAttribute("username") + "', '" + leagueID + "', '" + principle + "')";
						      //sql query to add a user into a league
						      stmt=conn.prepareStatement(sql);
						      stmt.executeUpdate(sql); //executes the sql query which inserts the username, leagueID and liquid money into the database
					        }
					     
					  

					      stmt.close();
					      conn.close();
					      
				    	  request.setAttribute("success",true);
				    	 
				    	  //System.out.println("success");
				     
				    	  response.sendRedirect(request.getContextPath()+ "/JoinLeagueConfirmation");
				    	  //Sends the user to the join league confirmation page
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

	public boolean JoinLeagueTest(String leagueId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		 
		 try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //Check database for entry corresponding to the leagueID inputted
		      String sql;
		      sql = "SELECT * FROM League WHERE leagueID='" + leagueId + "'";
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