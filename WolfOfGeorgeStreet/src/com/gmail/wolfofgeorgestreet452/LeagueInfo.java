package com.gmail.wolfofgeorgestreet452;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LeagueInfo")
public class LeagueInfo extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://wolfofgeorgestreet.c984b9paepnh.us-east-2.rds.amazonaws.com:3306/WolfOfGeorgeStreetDB";
    
	static final String USER = "WolfOfGeorgeSt";
	static final String PASS = "SWEdb452";
	
	public LeagueInfo() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		if(request.getSession().getAttribute("username")==null) {
 			response.sendRedirect(request.getContextPath());
 			return;
 		}  
 		String button=request.getParameter("Submit");
 		
 		if("submit".equals(button)) {			
 		System.out.print("button_pressed");	
 		}    	  
     	  
		String username=(String) request.getSession().getAttribute("username");

		String leagueID=(String) request.getParameter("leagueID");
		String leagueName = " ";
		
		//ArrayList<String> leagueMemberNames=new ArrayList<String>();
		
		ArrayList<UserPortfolio> userPortfolioValue_list = new ArrayList<UserPortfolio>();
		
		//List of lists for all the assets the user has for each league
		
		Connection conn = null;
		Statement stmt = null;
		 
		try{
		      //register jdbc driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //Grab all the users in the league
		      stmt = conn.createStatement();
		      String sql;
		      sql="SELECT * from LeagueUserList WHERE leagueID = '"+ leagueID + "' ORDER BY liquidMoney DESC";
		      ResultSet rs=stmt.executeQuery(sql);
		      
		     
		      while(rs.next()) {
		    	  double assetValue = LeagueFunctions.getUserLeagueAssetValue(rs.getString("username").trim(), leagueID);
		    	  if(rs.getString("username").trim().equals(username)) {
		    		  request.setAttribute("userCash", rs.getDouble("liquidMoney"));
		    		  request.setAttribute("userAsset", assetValue);
		    	  }
		    	  UserPortfolio current = new UserPortfolio(rs.getString("username").trim(),rs.getDouble("liquidMoney") + assetValue);
		    	  
		    	  
		    	  //find the right ranking in the list for the current portfolio
		    	  if(userPortfolioValue_list.size() == 0) {//first element
		    		  userPortfolioValue_list.add(current);
		    	  }else {
		    		  int rank = 0;
		    		  for(int i = 0; i < userPortfolioValue_list.size();i++) {
			    		  if(current.getPortfolioValue() > userPortfolioValue_list.get(i).getPortfolioValue()) {//current portfolio is greater
			    			  userPortfolioValue_list.add(i, current);
			    			  rank = i + 1;
			    			  break;//no need to continue the for loop
			    		  }
			    	  }
		    		  if(rank == 0) {//the current portfolio is the lowest(haven't changed)
		    			  userPortfolioValue_list.add(current); //add the current portfolio at the end of the list
		    		  }
		    	  }	  
		      }
		      
		      //ranked list of portfolios is ready. now I must find the current user's info from the list
		      /*
		       for(int i = 0; i < userPortfolioValue_list.size();i++) {
	    		  System.out.println(userPortfolioValue_list.get(i).getUsername() + userPortfolioValue_list.get(i).getPortfolioValue());
	    	  }
		      */
		      for(int i = 0; i < userPortfolioValue_list.size();i++) {
	    		  if(userPortfolioValue_list.get(i).getUsername().equals(username)) {//current user
	    			  request.setAttribute("userRank", i+1);
	    			  request.setAttribute("userPortfolioValue", userPortfolioValue_list.get(i).getPortfolioValue());
	    			  break;//no need to continue the for loop
	    		  }
	    	  }
		      
		      sql="SELECT * from League WHERE leagueID = '"+ leagueID + "' ";
		      rs = stmt.executeQuery(sql);
		      
		      
		      //check head to head mode or not
		      boolean gameMode = false;
		      
		      while(rs.next()) {
		    	  leagueName = rs.getString("LeagueName");
		    	  gameMode = rs.getBoolean("GameMode");
		      }		      
		      
		      request.setAttribute("userPortfolioValue_list", userPortfolioValue_list);
		      
		      
		    //start of head to head
		      if (gameMode == false) { //the game mode is head2head
		    	  request.setAttribute("gameMode", "head2head");
		    	  
				    ArrayList<UserPortfolio> User1;
				    ArrayList<UserPortfolio> User2;
				     
				    ArrayList<Integer> wins; //arraylist with user wins
		      		ArrayList<Integer> losses; //arraylist with user losses
		      		ArrayList<String> leagueMemberNamesH2H; //arraylist with membernames in H2H mode
		      		ArrayList<Double> percentage;  
		      		
		      		//checks whether any match-up exists
		      		sql="SELECT * from Head2HeadMatchUp WHERE leagueID = '"+ leagueID + "' ";
				      rs = stmt.executeQuery(sql);
		      		
		      		
		      		if(!rs.next()) { //no current match-up
		      		//randomized match up for first round
		      			if((userPortfolioValue_list.size() % 2) == 0) {//even number of players
		      				ArrayList<String> leagueMemberNames_temp = new ArrayList<String>();
						User1 = new ArrayList<UserPortfolio>();
						User2 = new ArrayList<UserPortfolio>();
						
						//Collections.shuffle(leagueMemberNames_temp); //Randomizes order of names to determine matchups
						
						for (int x = 0; x < userPortfolioValue_list.size(); x++) {//for match-making
							//leagueMemberNames_temp.add(userPortfolioValue_list.get(x).getUsername()); //Creates duplicate league member names arraylist 
							if((x % 2) == 0) {//Adds users to arraylist for matchmaking	
								User1.add(userPortfolioValue_list.get(x)); 
							}else {
								User2.add(userPortfolioValue_list.get(x));
							}
						} 
						
						int initialRound = 1;						
						for(int h=0;h<userPortfolioValue_list.size()/2;h++){
							  sql = "INSERT INTO Head2HeadMatchUp (LeagueID, round,username1 ,username2,User1_StartVal, User2_StartVal)"//sql query to add to h2h mode
								  + "VALUES ('" + leagueID + "', '" + initialRound + "', '"+ User1.get(h).getUsername()+ " ' , '"+ User2.get(h).getUsername() + "', '"+User1.get(h).getPortfolioValue()+ "','"+ User2.get(h).getPortfolioValue() +"')";
						
						  stmt=conn.prepareStatement(sql);
						  stmt.executeUpdate(sql);//executes sql query to add to H2Hmatchups if in H2H mode
						  }
							//System.out.println("match ups added to database");
		      			}else {
		      				System.out.println("odd number of players");
		      			}
		      		}else { //not the first round
		      			
		      		}
		      			//display current stats
						
							sql = "SELECT * from Head2HeadMatchUp";
							rs = stmt.executeQuery(sql);
							
							ArrayList<String> u1Names = new ArrayList<String>();
							ArrayList<String> u2Names = new ArrayList<String>();
							ArrayList<Double> u1Values = new ArrayList<Double>();
							ArrayList<Double> u2Values = new ArrayList<Double>();
							ArrayList<String> winning = new ArrayList<String>();
							
							while (rs.next()) {
								/*
								double U1_StartVal = rs.getDouble("User1_StartVal"); //Gets the portfolio value of users at start of round
								double U2_StartVal = rs.getDouble("User2_StartVal");

								double U1_EndVal = rs.getDouble("User1_EndVal"); //Gets the portfolio value of users at end of round
								double U2_EndVal = rs.getDouble("User2_EndVal");
								
								double U1_profit = U1_StartVal - U1_EndVal; //Calculates who won the round
								double U2_profit = U2_StartVal - U2_EndVal;
								*/
								
								String U1 = rs.getString("username1");//Gets usernames to send to the jsp for leaderboard
								String U2 = rs.getString("username2");
								
								u1Names.add(U1);
								u2Names.add(U2);
								//find the corresponding user's portfolio value from the list
								double U1Val = 0;
								double U2Val = 0;
								for (int g = 0; g < userPortfolioValue_list.size(); g++) {
									if(userPortfolioValue_list.get(g).getUsername().trim().equals(U1.trim())) {
										U1Val = userPortfolioValue_list.get(g).getPortfolioValue();
										//System.out.println(U1 + " value =" + U1Val);
										u1Values.add(U1Val);										
									}else if(userPortfolioValue_list.get(g).getUsername().equals(U2)) {
										U2Val = userPortfolioValue_list.get(g).getPortfolioValue();
										//System.out.println(U2 + " value =" + U2Val);
										u2Values.add(U2Val);
									}
								}

								if (U1Val > U2Val) { //User1 is winning
									/*
									sql = "update WolfOfGeorgeStreetDB.Head2Head set win =win +1 where leagueID = '"
											+ leagueID + "' and username = '" + U1 + "'";
									stmt = conn.prepareStatement(sql);
									stmt.executeUpdate(sql);

									sql = "update WolfOfGeorgeStreetDB.Head2Head set loss =loss +1 where leagueID = '"
											+ leagueID + "' and username = '" + U2 + "'";
									stmt = conn.prepareStatement(sql);
									stmt.executeUpdate(sql);
									*/
									winning.add(U1);
								} else if(U2Val > U1Val) {//User2 is winning
									/*
									sql = "update WolfOfGeorgeStreetDB.Head2Head set win =win +1 where leagueID = '"
											+ leagueID + "' and username = '" + U2 + "'";
									stmt = conn.prepareStatement(sql);
									stmt.executeUpdate(sql);

									sql = "update WolfOfGeorgeStreetDB.Head2Head set loss =loss +1 where leagueID = '"
											+ leagueID + "' and username = '" + U1 + "'";
									stmt = conn.prepareStatement(sql);
									stmt.executeUpdate(sql);
									*/
									winning.add(U2);
								}else {//tied
									winning.add("Tied");
								}
						}
							/*
						wins = new ArrayList<Integer>();
						losses = new ArrayList<Integer>();
						leagueMemberNamesH2H = new ArrayList<String>();
						sql = "SELECT * from Head2Head WHERE leagueID = '" + leagueID + "' ORDER BY win desc, loss asc";//Sql query to determine H2H standings based on Win/Loss
						ResultSet rs2 = stmt.executeQuery(sql);
						int ii = 0;
						while (rs2.next()) {
							leagueMemberNamesH2H.add(rs2.getString("username")); //Gets username from database
							wins.add(rs2.getInt("win")); //Gets amount of wins from database
							losses.add(rs2.getInt("loss"));//Gets amount of loss from database
						}
						percentage = new ArrayList<Double>();
						for (int q = 0; q < wins.size(); q++) //Calculates user win-loss percentage
						{
							if (wins.get(q) + losses.get(q) == 0) //Prevents division by zero
							{
								percentage.add((1.00));
							} else
								percentage.add((double) wins.get(q) / (wins.get(q) + losses.get(q)));
						} 
						
						request.setAttribute("leagueMemberNamesH2H", leagueMemberNamesH2H); 
				      request.setAttribute("User1", User1);
				      request.setAttribute("User2", User2);
				      request.setAttribute("wins", wins);
				      request.setAttribute("losses", losses);
				      request.setAttribute("percentage", percentage);
				      */
						request.setAttribute("User1", u1Names);
						request.setAttribute("User2", u2Names);
						request.setAttribute("User1Val", u1Values);
						request.setAttribute("User2Val", u2Values);
						request.setAttribute("Winning", winning);
						
					}else {
						
						request.setAttribute("gameMode", "normal");
					}
		      		
		      		

		      		
		      		sql="SELECT * from Voting WHERE leagueID = '"+ leagueID + "' ";//Sql query to view votes in progress for the league
				    rs2=stmt.executeQuery(sql);
				    ArrayList<String> VoteParam= new ArrayList<String>();
				    ArrayList<String> Votetype= new ArrayList<String>();
				    ArrayList<Integer> VoteInstanceIDs= new ArrayList<Integer>();
				     
				      while(rs2.next()) {
				    	  VoteParam.add(rs2.getString("parameter")); //Gets parameter,like user to kick, from database
				    	  Votetype.add(rs2.getString("voteType")); //Gets amount of wins from database
				    	  VoteInstanceIDs.add(rs2.getInt("voteInstanceID"));//Gets amount of loss from database
				      }
		      		
				      stmt.close();
				      conn.close();

		      
		      //for (int f= 0;f<VoteParam.size();f++) {
		    //	  System.out.print(VoteParam.get(f));
		    //	  System.out.print(Votetype.get(f));
		    //	  System.out.print(VoteInstanceIDs.get(f));
		    	  
		   //   }
		      
		      //Send the lists to the jsp
		      request.setAttribute("leagueName", leagueName);
		      request.setAttribute("leagueID", leagueID);
		      //request.setAttribute("leagueMemberNames", leagueMemberNames);
		      
		      //request.setAttribute("assetSums", assetSums);
		      request.setAttribute("VoteParam", VoteParam);
		      request.setAttribute("Votetype", Votetype);
		      request.setAttribute("VoteInstanceIDs", VoteInstanceIDs);
		      
		     //Display the jsp
		     request.getRequestDispatcher("/jsps/LeagueInfo.jsp").forward(request, response);
		      
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
		//doGet(request,response);
		System.out.print("button_pressed");
		String button=request.getParameter("Submit");
		if("submit".equals(button)) {	
			System.out.print("button_pressed");
		}
		
		
	}
	
	
	
}