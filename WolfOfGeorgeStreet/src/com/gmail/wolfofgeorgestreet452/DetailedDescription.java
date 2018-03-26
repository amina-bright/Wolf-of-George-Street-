package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

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
		String symbol=request.getParameter("symbol");
		
		if(symbol==null) {
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
		doGet(request,response);
		//request.getRequestDispatcher("/jsps/detailed-description.jsp").forward(request, response);	
	}
	

}
