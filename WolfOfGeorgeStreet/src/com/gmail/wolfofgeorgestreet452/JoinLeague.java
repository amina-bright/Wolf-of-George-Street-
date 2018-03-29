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
					      
					      sql = "SELECT * FROM WolfOfGeorgeStreetDB.League WHERE leagueID = ?";
					      PreparedStatement stmt2 = conn.prepareStatement(sql);
					      stmt2.setInt(1, leagueID);
					      ResultSet rs = stmt2.executeQuery();
					      while (rs.next()) {
					          int principle;
					        principle = rs.getInt(8);
					          //System.out.println(principle);
					          sql = "INSERT INTO LeagueUserList (username, leagueID, liquidMoney)"
							      		+ "VALUES ('" + request.getSession().getAttribute("username") + "', '" + leagueID + "', '" + principle + "')";
						      
						      stmt=conn.prepareStatement(sql);
						      stmt.executeUpdate(sql);
					        }
					     
					      
					      //stmt=conn.prepareStatement(sql);
					      //stmt.executeUpdate();
					      
					      //add to the list of users of this league
					      //sql = "INSERT INTO LeagueUserList (username, leagueID, liquidMoney)"
						  //    		+ "VALUES ('" + request.getSession().getAttribute("username") + "', '" + leagueID + "', '" + 1000 + "')";
					      
					      //stmt=conn.prepareStatement(sql);
					      //stmt.executeUpdate(sql);

					      stmt.close();
					      conn.close();
					      
				    	  request.setAttribute("success",true);
				    	 
				    	  //System.out.println("success");
				     
				    	  response.sendRedirect(request.getContextPath()+ "/JoinLeagueConfirmation");
				    	  
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