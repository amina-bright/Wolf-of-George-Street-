<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>

body {
	background-color: #FFDE26;
	}
	
.sidenav {
	width: 170px;
    position: fixed;
    border: 1px solid;
    border-color: #1A1F28;
    border-radius: 5px;
    z-index: 1;
    top: 20px;
    left: 10px;
    background: #1A1F28;
    overflow-x: hidden;
    padding: 8px 0;
    margin: 50px 0px;
}

.sidenav a {
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 25px;
    color: #FFFFFF;
    display: block;
}

.sidenav a:hover {
    color: #FFDE26;
}

input[type=text], select, textarea {
    width: 50%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
    resize: vertical;
}
input[type=number], select, textarea {
    width: 25%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 5px;
    margin-bottom: 5px;
    resize: vertical;
}
input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.btn {
	margin-top: 15px;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
    color: white;
    top: 300px;
    left: 200px;
    padding: 14px 28px;
    font-size: 16px;
    cursor: pointer;
}

.green {background-color: #4CAF50;} /* Green */
.green:hover {background-color: #46a049;}

.btn:hover{
	background-color:
		#FFDE26}
.purple {background-color: #5E0099;} /* Purple */
.purple:hover {background-color: #FFDE26; /*Change color to yellow when hovering*/
				color:black;}


.main {
    margin-left: 190px; /* Same width as the sidebar + left position in px */
    font-size: 28px; /* Increased text to enable scrolling */
    padding: 0px 10px;
}

@media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
}

.topnav {
    background-color: #1A1F28;
    border: 1px solid;
    border-radius: 5px;
    overflow: hidden;
}

.topnav a:hover {
   <%-- background-color: #ddd; --%>
    color: #FFDE26;
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
	<a href="/WolfOfGeorgeStreet/updateaccount">My Account</a>
	<a href=/WolfOfGeorgeStreet/logout>Logout</a>
</div>

<div class="sidenav">
	<a href ="/WolfOfGeorgeStreet/portfolio">My Portfolio</a>
	<a href ="/WolfOfGeorgeStreet/stocksearch">Stocks</a>
	<a href ="#">Crypto</a>
	<a href ="/WolfOfGeorgeStreet/league">My Leagues</a>
</div>

<div class="main">
		<br><br>
		${leagueName}: ${param.leagueID} <br>
		 <p>
		 Your League Ranking: ${userRank}<br>
		 Your Cash: $<fmt:formatNumber value = "${userCash}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>
					<br>
		 The value of Your Assets: $<fmt:formatNumber value = "${userAsset}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>
					<br>			
		 The Total Value of Your Portfolio: $<fmt:formatNumber value = "${userPortfolioValue}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>
					
		 </p>
		<table border=1 frame=void rules=rows>
		
		<tr>
		    <th>Ranking</th>
		    <th>League Member Name</th>
		    <th>Assets</th>
		   
	  	</tr>
			<c:forEach items="${userPortfolioValue_list}" varStatus="loop">
	
				<tr>
					
					<td>
						${loop.index + 1}
					</td>
					
					<td>
						${userPortfolioValue_list[loop.index].getUsername()}	
					</td>
					
					<td>
						$<fmt:formatNumber value = "${userPortfolioValue_list[loop.index].getPortfolioValue()}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>
					
					</td>
				
				
				</tr>
			
			</c:forEach>
			
			
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
		
		
		<c:if test="${not empty VoteParam}" >
        
        <button class="btn green" id="button_Vote">View Votes in Progress</button><br>
        
		</c:if>
				
		<!--  
		<form action="${pageContext.request.contextPath}/Leagueinfo?leagueID=${param.leagueID}" method="post">
		<input type="submit" name="Submit" value="submit">   submit button 
	</form> 
	-->
				<br>
				<br>		

</div>




<script>
document.getElementById("button_Update_Round").onclick = function () {
    location.href = "/WolfOfGeorgeStreet/LeagueInfo?leagueID=${param.leagueID}";
};
</script>
<script>
document.getElementById("button_Vote").onclick = function () {
    location.href = "/WolfOfGeorgeStreet/LeagueVotingUser?leagueID=${param.leagueID}";
};


</script>

<!-- Flyzoo script -->
<script type="text/javascript">
(function () {
 window._FlyzooApplicationId="5ae22ffcbb547e096099f58f5ae22fb2bb547e096099f58b";
 var fz = document.createElement('script'); fz.type = 'text/javascript'; fz.async = true;
 fz.src = '//widget.flyzoo.co/scripts/flyzoo.start.js';
 var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(fz, s);
})();
</script>

</body>
</html>