package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trophies")
public class Trophies extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Trophies() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String username=(String) request.getSession().getAttribute("username");

		
		Connection conn = null;
		Statement stmt = null;
				 
		try{
			//register jdbc driver
			Class.forName("com.mysql.jdbc.Driver");

			//open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//Execute sql query searching fro similar strings in symbols, titles, or market
			stmt = conn.createStatement();
			
			String sql="SELECT * From LeagueUserList s, League w WHERE s.username='" + username + "' AND s.leagueID=w.leagueID";
			
			ArrayList<String> leagueIds=new ArrayList<String>();
			ArrayList<String> leagueNames=new ArrayList<String>();
			
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String id=rs.getString("leagueID");
				leagueIds.add(id);
				leagueNames.add(rs.getString("leagueName"));
			}
			
			ArrayList<String []> trophyList= new ArrayList<String []>();
			
			for(int i=0;i<leagueIds.size();i++) {
				ArrayList<String> temp=new ArrayList<String>();
				 sql = "SELECT * FROM Trophies WHERE username='" + username + "' AND leagueID=" + leagueIds.get(i);
				 rs=stmt.executeQuery(sql);
				 
				 while(rs.next()) {
					 temp.add(rs.getString("trophy"));
				 }
				 String [] array= temp.toArray(new String[temp.size()]);
				 trophyList.add(array);
			}
			request.setAttribute("leagueNames", leagueNames);
			request.setAttribute("leagueIds", leagueIds);
			request.setAttribute("trophyList", trophyList);
	      
	      
	      
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
		
		
		request.getRequestDispatcher("/jsps/trophies.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}

}
