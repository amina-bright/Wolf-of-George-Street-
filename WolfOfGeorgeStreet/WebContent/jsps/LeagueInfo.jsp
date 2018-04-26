<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>
.sidenav {
    width: 130px;
    position: fixed;
    z-index: 1;
    top: 20px;
    left: 10px;
    background: #eee;
    overflow-x: hidden;
    padding: 8px 0;
    margin: 50px 0px;
}

.sidenav a {
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 25px;
    color: #2196F3;
    display: block;
}

.sidenav a:hover {
    color: #064579;
}

.main {
    margin-left: 140px; /* Same width as the sidebar + left position in px */
    font-size: 28px; /* Increased text to enable scrolling */
    padding: 0px 10px;
}

@media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
}

.topnav {
    background-color: #333;
    overflow: hidden;
}

.topnav a:hover {
    background-color: #ddd;
    color: black;
}

.topnav a {
    float: right;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    height:15px;
    font-size: 17px;
}

.main input[type=text] {
    float: center;
    padding: 6px;
    margin-top: 8px;
    margin-right: 16px;
    font-size: 17px;
}

</style>
<title>League Rankings</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">Alerts</a>
	<a href="help">Help</a>
	<a href="myaccount">My account</a>
	<a href=/WolfOfGeorgeStreet/logout>Logout</a>
</div>

<div class="sidenav">
	<a href ="/WolfOfGeorgeStreet/portfolio">My Portfolio</a>
	<a href ="/WolfOfGeorgeStreet/stocksearch">Stocks</a>
	<a href ="#">Crypto</a>
	<a href ="/WolfOfGeorgeStreet/league">My Leagues</a>
</div>

<div class="main">
		
		${leagueName} : ${param.leagueID} <br>
		 <p>
		 Your League Ranking: ${userRank}<br>
		 Your League Assets: $${userAsset}
		 </p>
		<table border=1 frame=void rules=rows>
		
		<tr>
		    <th>Ranking</th>
		    <th>League Member Name</th>
		    <th>Assets</th>
		   
	  	</tr>
			<c:forEach items="${leagueMemberNames}" varStatus="loop">
	
				<tr>
					
					<td>
						${loop.index + 1}
					</td>
					
					<td>
						${leagueMemberNames[loop.index]}
					</td>
					
					<td>
						${leagueAssets[loop.index]}
					</td>
				
				
				</tr>
			
			</c:forEach>
			<br>
		
			
		</table>
		
		<p>
		 Matchups for this Round 
		 </p>
		
		<table border=1 frame=void rules=rows> <!--  Table to show current matchups -->
		
		
		<tr>
		    <th>User1</th>
		    <th>User2</th>
		    
		   
	  	</tr>
			<c:forEach items="${User1}" varStatus="loop">
	
				<tr>
					
					<td>
						${User1[loop.index]}
					</td>
					
					<td>
						${User2[loop.index]}
					</td>
				
				
				</tr>
			
			</c:forEach>
			
			
		</table>
	
	
		 
		 <table border=1 frame=void rules=rows> <!-- Table which shows ranking for H2H mode -->
		
		<tr>
		    <th>Ranking</th>
		    <th>League Member Name</th>
		    <th>Win</th>
		   <th>Loss</th>
		   <th>Percentage</th>
	  	</tr>
			<c:forEach items="${leagueMemberNames}" varStatus="loop">
	
				<tr>
					
					<td>
						${loop.index + 1}
					</td>
					
					<td>
						${leagueMemberNamesH2H[loop.index]}
					</td>
					
					<td>
						${wins[loop.index]}
					</td>
					
					<td>
						${losses[loop.index]}
					</td>
					
					<td>
						${percentage[loop.index]}
					</td>
				
				
				</tr>
			
			</c:forEach>
			<br>
		
			
		</table>
				<br>
				<br>		

</div>


</body>
</html>