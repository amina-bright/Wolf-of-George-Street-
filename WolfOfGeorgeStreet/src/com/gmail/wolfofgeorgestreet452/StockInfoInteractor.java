package com.gmail.wolfofgeorgestreet452;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class StockInfoInteractor {
	private static String apiKey="7BX0DHJ8VQXXO3L";
	private static String baseURL="https://www.alphavantage.co/query?function=";
	
	private StockInfoInteractor() {};
	
	/*
	 * Fetches stock data for the requested symbol. Invalid symbols will return null. A failed connection will
	 * also return null. The function parameter specifies the type of data requested. 0 corresponds to intraday
	 * data. 1 corresponds to daily data. More to come later. The full variable specifies whether the full or
	 * compact data is requested. True would mean the user wants the full data. Only use the full data if
	 * absolutely necessary as the amount of data requested is huge
	 */
	public static String fetchStockData(String symbol, int function, boolean full) {
		
		StringBuilder builder=new StringBuilder();
		
		builder.append(baseURL);
		
		//intraday
		if(function==0) {
			builder.append("TIME_SERIES_INTRADAY&interval=1min");
		}
		
		//daily
		else if(function==1) {
			builder.append("TIME_SERIES_DAILY");
		}
		
		//more to come later
		else {
			return null;
		}
		
		//Add the symbol and api key to the url
		builder.append("&symbol=" + symbol +"&apikey=" + apiKey);
		
		//If the full data is wanted
		if(full==true) {
			builder.append("&outputsize=full");
		}
		
		String fullUrl=builder.toString();
		
		HttpURLConnection connection=null;
		
		try {
			
			//Boilerplaye for http connection
			URL url=new URL(fullUrl);
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			
			//Read from input stream
			 InputStream is = connection.getInputStream();
			 BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			 StringBuilder response = new StringBuilder();
			 String line;
			 
			 //Add the data to a string builder
			 while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			 }
			 rd.close();
			 
			 //Response from the get request
			 String output=response.toString();
		    
			 if(connection!=null) {
				 connection.disconnect();
			 }
			 
			 if(output.contains("Error Message")) {
				 return null;
			 }
			 
			 //return the response
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
	 * Method to get the open, high, low, and close information in that order from the time series stock data json. The function
	 * parameter specifies whether its referring to intraday, daily, or something else. The  numPeriodBefore
	 * paramater specifies the number of time period before the most recent that the user wants. For
	 * intraday the period is 1 sec. For daily its 1 day. Giving this a nonzero might cause the request to fail
	 * if there is no data for that day.
	 */
	public static double[] getTimeSeriesData(String stockData, int function, int numPeriodBefore) {
		double [] output=new double[4];
		try {
			JSONObject obj=new JSONObject(stockData);
			
			JSONObject metadata=obj.getJSONObject("Meta Data");
			
			//Last time the data was updated
			String lastRefreshed=metadata.getString("3. Last Refreshed");
			
			String dateWanted=null;
			
			//intra day
			if(function==0) {
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date lastDate=formatter.parse(lastRefreshed);
				long lastTimeStamp=lastDate.getTime();
				long timeRequested=(long)lastTimeStamp-(long)1000*(long)60*(long)numPeriodBefore;
				lastDate.setTime(timeRequested);
				dateWanted=formatter.format(lastDate);
			}
			
			//daily
			else if(function==1) {
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
				Date lastDate=formatter.parse(lastRefreshed);
				long lastTimeStamp=lastDate.getTime();
				long timeRequested=(long)lastTimeStamp-(long)1000*(long)60*(long)60*(long)24*(long)numPeriodBefore;
				lastDate.setTime(timeRequested);
				dateWanted=formatter.format(lastDate);
				/*System.out.println(lastTimeStamp);
				System.out.println(timeRequested);
				System.out.println(dateWanted);*/
			}
			
			
			JSONObject timeSeries;
			
			//intraday
			if(function==0) {
				timeSeries=obj.getJSONObject("Time Series (1min)");
			}
			
			//daily
			else if (function==1) {
				timeSeries=obj.getJSONObject("Time Series (Daily)");
			}
			
			else {
				return null;
			}
			
			
			JSONObject wanted=timeSeries.getJSONObject(dateWanted);
			
			String openString=wanted.getString("1. open");
			String highString=wanted.getString("2. high");
			String lowString=wanted.getString("3. low");
			String closeString=wanted.getString("4. close");
			
			Double openDouble=Double.parseDouble(openString);
			Double highDouble=Double.parseDouble(highString);
			Double lowDouble=Double.parseDouble(lowString);
			Double closeDouble=Double.parseDouble(closeString);
			
			output[0]=openDouble.doubleValue();
			output[1]=highDouble.doubleValue();
			output[2]=lowDouble.doubleValue();
			output[3]=closeDouble.doubleValue();
			
			return output;
		} catch(Exception e) {
			e.printStackTrace();
			//System.out.println(stockData);
		}
		
		return null;
	}
	
	//Function to get the last refreshed time from data
	public static String getLastRefreshed(String stockData) {
		try {
			JSONObject obj=new JSONObject(stockData);
			
			JSONObject metadata=obj.getJSONObject("Meta Data");
			String lastRefreshed=metadata.getString("3. Last Refreshed");
			
			return lastRefreshed;
		}
		
		catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String fetchCryptoData(String symbol, int function) {
		StringBuilder builder=new StringBuilder();
		
		builder.append(baseURL);
		
		//intraday
		if(function==0) {
			builder.append("DIGITAL_CURRENCY_INTRADAY");
		}
		
		//daily
		else if(function==1) {
			builder.append("DIGITAL_CURRENCY_DAILY");
		}
		
		//more to come later
		else {
			return null;
		}
		
		//Add the symbol and api key to the url
		builder.append("&symbol=" + symbol +"&market=USD&apikey=" + apiKey);
		
		String fullUrl=builder.toString();
		
		HttpURLConnection connection=null;
		
		try {
			
			//Boilerplaye for http connection
			URL url=new URL(fullUrl);
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			
			//Read from input stream
			 InputStream is = connection.getInputStream();
			 BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			 StringBuilder response = new StringBuilder();
			 String line;
			 
			 //Add the data to a string builder
			 while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			 }
			 rd.close();
			 
			 //Response from the get request
			 String output=response.toString();
		    
			 if(connection!=null) {
				 connection.disconnect();
			 }
			 
			 if(output.contains("Error Message")) {
				 return null;
			 }
			 
			 //return the response
			 return output;

		} catch(Exception e) {
			e.printStackTrace();
			if(connection!=null) {
				 connection.disconnect();
			 }
			return null;
		}
	}
	
	public static double[] getTimeSeriesDataCrypto(String cryptoData, int function, int numPeriodBefore) {
		double [] output=new double[4];
		try {
			JSONObject obj=new JSONObject(cryptoData);
			
			JSONObject metadata=obj.getJSONObject("Meta Data");
			String dateWanted=null;
			
			//intra day
			if(function==0) {
				//Last time the data was updated
				String lastRefreshed=metadata.getString("7. Last Refreshed");
				
				dateWanted=null;
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date lastDate=formatter.parse(lastRefreshed);
				long lastTimeStamp=lastDate.getTime();
				long timeRequested=lastTimeStamp-(long)1000*(long)60*(long)numPeriodBefore;
				lastDate.setTime(timeRequested);
				dateWanted=formatter.format(lastDate);
			}
			
			//daily
			else if(function==1) {
				//Last time the data was updated
				String lastRefreshed=metadata.getString("6. Last Refreshed");
				
				dateWanted=null;
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
				Date lastDate=formatter.parse(lastRefreshed);
				long lastTimeStamp=lastDate.getTime();
				long timeRequested=(long)lastTimeStamp-(long)1000*(long)60*(long)60*(long)24*(long)numPeriodBefore;
				lastDate.setTime(timeRequested);
				dateWanted=formatter.format(lastDate);
				/*System.out.println(lastTimeStamp);
				System.out.println(timeRequested);
				System.out.println(dateWanted);*/
			}
			
			
			JSONObject timeSeries;
			
			
			
			//intraday
			if(function==0) {
				timeSeries=obj.getJSONObject("Time Series (Digital Currency Intraday)");
				
				JSONObject wanted=timeSeries.getJSONObject(dateWanted);
				
				String price=wanted.getString("1b. price (USD)");
				
				output[0]=Double.parseDouble(price);
				
				output[1]=0;
				output[2]=0;
				output[3]=0;
			}
			
			//daily
			else if (function==1) {
				timeSeries=obj.getJSONObject("Time Series (Digital Currency Daily)");
				JSONObject wanted=timeSeries.getJSONObject(dateWanted);
				
				String openString=wanted.getString("1b. open (USD)");
				String highString=wanted.getString("2a. high (USD)");
				String lowString=wanted.getString("3b. low (USD)");
				String closeString=wanted.getString("4b. close (USD)");
				
				Double openDouble=Double.parseDouble(openString);
				Double highDouble=Double.parseDouble(highString);
				Double lowDouble=Double.parseDouble(lowString);
				Double closeDouble=Double.parseDouble(closeString);
				
				output[0]=openDouble.doubleValue();
				output[1]=highDouble.doubleValue();
				output[2]=lowDouble.doubleValue();
				output[3]=closeDouble.doubleValue();
			}
			
			else {
				return null;
			}
			
			
		
			
			return output;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getLastRefreshedCrypto(String cryptoData, int function) {
		try {
			JSONObject obj=new JSONObject(cryptoData);
			
			JSONObject metadata=obj.getJSONObject("Meta Data");
			String lastRefreshed;
			if(function==1) {
				lastRefreshed=metadata.getString("6. Last Refreshed");
			}
			
			else {
				lastRefreshed=metadata.getString("7. Last Refreshed");	
			}
			
			return lastRefreshed;
		}
		
		catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String fetchStockDataBatch(ArrayList<Stock> stocks) {
		StringBuilder builder=new StringBuilder();
		
		builder.append(baseURL);

		builder.append("BATCH_STOCK_QUOTES");

		
		//Add the symbol and api key to the url
		builder.append("&apikey=" + apiKey + "&symbols=");
		
		for(int i=0;i<stocks.size();i++) {
			if(i==stocks.size()-1) {
				builder.append(stocks.get(i).getSymbol());
			}
			
			else {
				builder.append(stocks.get(i).getSymbol() + ",");
			}
		}
		
		String fullUrl=builder.toString();
		
		HttpURLConnection connection=null;
		
		try {
			
			//Boilerplaye for http connection
			URL url=new URL(fullUrl);
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			
			//Read from input stream
			 InputStream is = connection.getInputStream();
			 BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			 StringBuilder response = new StringBuilder();
			 String line;
			 
			 //Add the data to a string builder
			 while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			 }
			 rd.close();
			 
			 //Response from the get request
			 String output=response.toString();
		    
			 if(connection!=null) {
				 connection.disconnect();
			 }
			 
			 if(output.contains("Error Message")) {
				 return null;
			 }
			 
			 //return the response
			 return output;

		} catch(Exception e) {
			e.printStackTrace();
			if(connection!=null) {
				 connection.disconnect();
			 }
			return null;
		}
		
	}
	
	public static double[] getPriceFromBatch(String data) {
		double[] output=null;
		try {
			JSONObject obj=new JSONObject(data);
			
			JSONArray prices=obj.getJSONArray(("Stock Quotes"));
			
			output=new double[prices.length()];
			
			for(int i=0;i<prices.length();i++) {
				JSONObject temp=(JSONObject) prices.get(i);
				output[i]=Double.parseDouble(temp.getString("2. price"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return output;
		
		
		
	}
	
	
}	
