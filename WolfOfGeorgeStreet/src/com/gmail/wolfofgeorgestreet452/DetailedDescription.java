package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/detailed-description")
public class DetailedDescription extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
    
	
	public DetailedDescription() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String username=(String) request.getSession().getAttribute("username");
			
		String symbol=request.getParameter("symbol");
		
		if(symbol==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String data=StockInfoInteractor.fetchStockData(symbol, 1, false);
		
		//String dataDayFull=StockInfoInteractor.fetchStockData(symbol, 0, true);
		
		double[] dataParsed=StockInfoInteractor.getTimeSeriesData(data, 1, 0);

		Stock requestedStock=null;
		
		double[] dataParsedBefore=null;
		int count=1;
		while(dataParsedBefore==null) {
			dataParsedBefore=StockInfoInteractor.getTimeSeriesData(data, 1, count);
			count++;
		}
		
		/*double[][] fullDayData=new double[390][4];
		
		for(int i=0;i<390;i++) {
			fullDayData[i]=StockInfoInteractor.getTimeSeriesData(dataDayFull, 0, i);
		}*/
		
		ArrayList<String> leagueNames=new ArrayList<String>();
		ArrayList<String> leagueIds=new ArrayList<String>();
		
		Connection conn = null;
		Statement stmt = null;
		 
		try{
		      //register jdbc driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //Execute sql query
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT * FROM StockLookup WHERE symbol= '" + symbol + "'";
		      ResultSet rs = stmt.executeQuery(sql);

		      //Extract the return data
		      while(rs.next()){
		         String title = rs.getString("title");
		         String market=rs.getString("market");
		         
		         //Create a stock out of each set
		         requestedStock=new Stock(symbol,title,market);
		      }
		      
		      sql="SELECT w.leagueID, leagueName From LeagueUserList s, League w WHERE s.username='" + username + "' AND s.leagueID=w.leagueID";
		      rs=stmt.executeQuery(sql);
		      
		      while(rs.next()) {
		    	  leagueIds.add(rs.getString("leagueID"));
		    	  leagueNames.add(rs.getString("leagueName"));
		      }
		      
		      //close connections
		      rs.close();
		      stmt.close();
		      conn.close();
		      
		      
		      //pass stock to the jsp
		      request.setAttribute("stock",requestedStock);
		      request.setAttribute("currentPrice", dataParsed[3]);
		      request.setAttribute("previousClose", dataParsedBefore[3]);
		      
		      double amountChanged=dataParsed[3]-dataParsedBefore[3];
		      
		      double percentChanged=amountChanged/dataParsedBefore[3];
		      
		      
		      request.setAttribute("amountChanged", amountChanged);
		      request.setAttribute("percentChanged", percentChanged);
		      request.setAttribute("leagueIds", leagueIds.toArray());
		      request.setAttribute("leagueNames", leagueNames.toArray());
		      
		     /* String lastRefreshTime=StockInfoInteractor.getLastRefreshed(dataDayFull);
		      
		      
		      Map<Object,Object> map = null;
		      List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
		      
		      for(int i=0;i<390;i++) {
		    	  if(fullDayData[i]==null) {
		    		  System.out.println("NULL RESULT");
		    		  continue;
		    	  }
		    	  SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	  Date lastDate=formatter.parse(lastRefreshTime);
		    	  long lastTimeStamp=lastDate.getTime();
		    	  long timeRequested=lastTimeStamp-1000*60*i;
		    	  lastDate.setTime(timeRequested);
		    	  String dateWanted=formatter.format(lastDate);
		    	  map = new HashMap<Object,Object>(); 
		    	  map.put("label", dateWanted); map.put("y", fullDayData[i][3]);
		    	  list.add(map);
		      }
		      
		      JSONObject obj=new JSONObject();
		      obj.put("list", list);
		      
		      request.setAttribute("dataPoints", obj.toString());*/
		      
		      	
		      request.getRequestDispatcher("/jsps/detailed-description.jsp").forward(request, response);
		      
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String username=(String) request.getSession().getAttribute("username");
		
		String button=request.getParameter("button");
		
		String amount=request.getParameter("amount");
		
		String transcationType=request.getParameter("transcationType");
		
		String league=request.getParameter("league");
		
		String symbol=request.getParameter("symbol");
		
		Double currentPrice=Double.parseDouble(request.getParameter("price"));
		
		Double amountDouble=Double.parseDouble(amount);
		
		double totalCost=currentPrice.doubleValue()*amountDouble.doubleValue();
	
		
		if("button1".equals(button) && transcationType.equals("buy")) {
			
		
			Connection conn = null;
			Statement stmt = null;
		 
			try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");
	
			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
			      //Execute sql query
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT * FROM LeagueUserList WHERE username='" + username + "' AND leagueID=" + league + "";
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      double liquidMoney=0;
			      
			      //Extract the return data
			      while(rs.next()){
			    	  liquidMoney=rs.getDouble("liquidMoney");
			      }
			      
			      if(totalCost>liquidMoney) {
			    	  request.setAttribute("failure", "true");
			    	  request.setAttribute("reason", "Transaction Failed: Not Enough Liquid Money");
			    	  doGet(request,response);
			    	  return;
			      } 
			      
			      	liquidMoney-=totalCost;
			    	sql="UPDATE LeagueUserList SET liquidMoney=" + liquidMoney + "WHERE username='" + username + "' AND leagueID=" + league + "";
			    	conn.prepareStatement(sql).executeUpdate();
			    	
			    	
			    	sql="SELECT * FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'" ;
			    	rs = stmt.executeQuery(sql);
			    	
			    	//need to update
			    	if(rs.next()) {
			    		double amountAlready=rs.getDouble("amount");
			    		sql="UPDATE Asset SET amount=" + (amountAlready+amountDouble.doubleValue()) + " WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'" ;
			    		conn.prepareStatement(sql).executeUpdate();
			    	}
			    	
			    	//need to create
			    	else {
			    		sql="INSERT INTO Asset (username, amount, asset, leagueID) VALUES ('" + username + "', " + amountDouble.doubleValue() + ", '" 
			    				+ symbol + "', " + league + ")";
			    		conn.prepareStatement(sql).executeUpdate();
			    	}
			    	 
			    	Timestamp ts=new Timestamp(System.currentTimeMillis());
			    	sql="INSERT INTO Transaction (username, amount, price, asset, transactionType, transactionTime, leagueID) VALUES ('"  +
			    			username + "', " + amountDouble.doubleValue() + ", " + currentPrice + ",  '" + symbol + "', 'buy', '" +  ts + "', " + league + ")";
			    	conn.prepareStatement(sql).executeUpdate();
			    	  
			    	request.setAttribute("success", true);
			      
			      
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
		
		else if ("button1".equals(button) && transcationType.equals("sell")) {
			
			Connection conn = null;
			Statement stmt = null;
		 
			try{
			      //register jdbc driver
			      Class.forName("com.mysql.jdbc.Driver");
	
			      //open a connection
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
			      //Execute sql query
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT * FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'";
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      double amountInPossession=0;
			      while(rs.next()) {
			    	  amountInPossession=rs.getDouble("amount");
			      }
			      
			      if(amountInPossession<amountDouble.doubleValue()) {
			    	  request.setAttribute("failure", "true");
			    	  request.setAttribute("reason", "Transaction Failed: Not Enough Of Asset In Possession To Sell");
			    	  doGet(request,response);
			    	  return; 
			    	  
			      }
			      
			      //Delete from table
			      if(amountInPossession-amountDouble.doubleValue()==0.0) {
			    	  sql = "DELETE FROM Asset WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'";
			    	  conn.prepareStatement(sql).executeUpdate();
			      }
			      
			      else {
			    	  sql = "UPDATE Asset SET amount=" + (amountInPossession-amountDouble.doubleValue()) + " WHERE username='" + username + "' AND leagueID=" + league + " AND asset='" + symbol + "'" ;
			    	  conn.prepareStatement(sql).executeUpdate();
			      }
			      
			      sql = "SELECT * FROM LeagueUserList WHERE username='" + username + "' AND leagueID=" + league + "";
			      rs = stmt.executeQuery(sql);
			      
			      double liquidMoney=0;
			      
			      //Extract the return data
			      while(rs.next()){
			    	  liquidMoney=rs.getDouble("liquidMoney");
			      }
			      
			      liquidMoney+=totalCost;
			      
			      sql="UPDATE LeagueUserList SET liquidMoney=" + liquidMoney + "WHERE username='" + username + "' AND leagueID=" + league + "";
			      conn.prepareStatement(sql).executeUpdate();
			      
			      Timestamp ts=new Timestamp(System.currentTimeMillis());
			      sql="INSERT INTO Transaction (username, amount, price, asset, transactionType, transactionTime, leagueID) VALUES ('"  +
			    			username + "', " + amountDouble.doubleValue() + ", " + currentPrice + ",  '" + symbol + "', 'sell', '" +  ts + "', " + league + ")";
			      conn.prepareStatement(sql).executeUpdate();
			    	  
			      request.setAttribute("success", true);
			      
			      
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
		
		doGet(request,response);
	}
	

}
