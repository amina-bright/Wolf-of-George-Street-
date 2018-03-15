package com.gmail.wolfofwallstreet452;
import java.net.URL;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class StockInfoInteractor {
	private static String apiKey="7BX0DHJ8VQXXO3L";
	private static String baseURL="https://www.alphavantage.co/query?function=";
	
	private StockInfoInteractor() {};
	
	/*
	 * Fetches stock data for the requested symbol. Invalid symbols will return null. A failed connection will
	 * also return null. The stock data is the time series intraday for the asset. It contains open, high, low,
	 * close, and volume data for each day. THe output size is compact. 
	 */
	public static String fetchStockDataIntraDay(String symbol) {
		
		String fullUrl=baseURL+"TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=1min&apikey=" + apiKey;
		
		HttpURLConnection connection=null;
		
		try {
			URL url=new URL(fullUrl);
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			
			 InputStream is = connection.getInputStream();
			 BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			 StringBuilder response = new StringBuilder();
			 String line;
			 while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			 }
			 rd.close();
			 String output=response.toString();
		    
			 if(connection!=null) {
				 connection.disconnect();
			 }
			 
			 if(output.contains("Error Message")) {
				 return null;
			 }
			 
			 return output;

		} catch(Exception e) {
			e.printStackTrace();
			if(connection!=null) {
				 connection.disconnect();
			 }
			return null;
		}
	}
	
	/*
	 * Same as above but with full history going back 20 years. Use sparingly as it retrieves a very large
	 * amount of data.
	 */
	public static String fetchStockDataIntraDayFull(String symbol) {
		
		String fullUrl=baseURL+"TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=1min&apikey=" + apiKey + "&outputsize=full";
		
		HttpURLConnection connection=null;
		
		try {
			URL url=new URL(fullUrl);
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			
			 InputStream is = connection.getInputStream();
			 BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			 StringBuilder response = new StringBuilder();
			 String line;
			 while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			 }
			 rd.close();
			 String output=response.toString();
		    
			 if(connection!=null) {
				 connection.disconnect();
			 }
			 
			 if(output.contains("Error Message")) {
				 return null;
			 }
			 
			 return output;

		} catch(Exception e) {
			e.printStackTrace();
			if(connection!=null) {
				 connection.disconnect();
			 }
			return null;
		}
	}
	
	/*
	 * Method to get the most recent volume of a sock from its time series Intraday json information
	 * Note: Work in progress.
	 */
	public static int getVolumeIntraDay(String stockData) {
		
		try {
			JSONObject obj=new JSONObject(stockData);
			
			JSONObject metadata=obj.getJSONObject("Meta Data");
			String lastRefreshed=metadata.getString("3. Last Refreshed");
	
			
			JSONObject timeSeries=obj.getJSONObject("Time Series (1min)");
			
			
			JSONObject mostRecent=timeSeries.getJSONObject(lastRefreshed);

			String volumeString=mostRecent.getString("5. volume");
			
			Integer output= Integer.parseInt(volumeString);
			
			return output.intValue();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}	
