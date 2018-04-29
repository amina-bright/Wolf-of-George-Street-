<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
<title>My Portfolio</title>
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

	<c:forEach items="${leagueIds}" varStatus="loop">
	
		${leagueNames[loop.index]} : ${leagueIds[loop.index]} 
		<br> 
		Cash Available: $<fmt:formatNumber value = "${liquidmoneys[loop.index]}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>  <!--  Prints the liquid money for the league -->
		<br> 
		Asset Value: $<fmt:formatNumber value = "${assetSums[loop.index]}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>  <!--  Prints the total asset for the league -->
		<br> 
		Total Value of Portfolio: $<fmt:formatNumber value = "${liquidmoneys[loop.index] + assetSums[loop.index]}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>  <!--  Prints the total asset for the league -->
		<p>
		</p>
		<table border=1 frame=void rules=rows>
		
		<tr>
		    <th>Symbol</th>
		    <th>Title</th>
		    <th>Market</th>
		    <th>Amount </th>
	  	</tr>
			
			<c:forEach var="currentAsset" items="${assets[loop.index]}" varStatus="loop2">
				<tr>
					<td>
						<a href="${pageContext.request.contextPath}/detailed-description?symbol=${currentAsset.symbol}&market=${currentAsset.market}">${currentAsset.symbol} </a>
					</td>
					
					<td>
						${currentAsset.title}
					</td>
					
					<td>
						${currentAsset.market}
					</td>
					
					<td>
						${currentAsset.amount}
					</td>
				
				
				</tr>
			
			</c:forEach>
			
			
		</table>
	
				<br>
				<br>		
	</c:forEach>

</div>

<!-- Chat script -->
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