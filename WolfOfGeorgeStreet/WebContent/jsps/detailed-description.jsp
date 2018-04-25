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
    text-align: center;
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

.stockTitle {
	text-align: center;
	margin-top: 16px;
	font-size: 42px;
}

.currentPrice {
	display: inline;
	font-szie: 32px
}

.amountChangedPositive{
	display: inline;
	font-size: 18px;
	color: #009900;
}

.amountChangedNegative{
	display: inline;
	font-size: 18px;
	color: #ff0000;
}


.percentChangedPositive {
	display:inline;
	font-size: 18px;
	color: #009900;
}

.percentChangedNegative {
	display:inline;
	font-size: 18px;
	color: #ff0000;
}

.stockData {
	text-align: center;
}

.transcationForm {
	text-align: center;
}


</style>
<script>
window.onload = function () {
	

var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	zoomEnabled: true,
	theme: "light2",
	title:{
		text: "${stock.title} Past 30 Days before Peformance"
	},
	 axisX:{
		   reversed:  true,
		   maximum: 30,
		   title: "Days In the Past"
		 },
	axisY:{
		includeZero: false,
		title: "Close Price: USD"
	},
	data: [{        
		type: "line",       
		dataPoints: [
			{ x: "30", y: ${weekBefore[30]} },
			{ x: "29", y: ${weekBefore[29]} },
			{x: "28", y: ${weekBefore[28]} },
			{x: "27", y: ${weekBefore[27]} },
			{x: "26", y: ${weekBefore[26]} },
			{x: "25", y: ${weekBefore[25]} },
			{x: "24", y: ${weekBefore[24]} },
			{x: "23", y: ${weekBefore[23]} },
			{x: "22", y: ${weekBefore[22]} },
			{x: "21", y: ${weekBefore[21]} },
			{x: "20", y: ${weekBefore[20]} },
			{x: "19", y: ${weekBefore[19]} },
			{x: "18", y: ${weekBefore[18]} },
			{x: "17", y: ${weekBefore[17]} },
			{x: "16", y: ${weekBefore[16]} },
			{x: "15", y: ${weekBefore[15]} },
			{x: "14", y: ${weekBefore[14]} },
			{x: "13", y: ${weekBefore[13]} },
			{x: "12", y: ${weekBefore[12]} },
			{x: "11", y: ${weekBefore[11]} },
			{x: "10", y: ${weekBefore[10]} },
			{x: "9", y: ${weekBefore[9]} },
			{x: "8", y: ${weekBefore[8]} },
			{x: "7", y: ${weekBefore[7]} },
			{x: "6", y: ${weekBefore[6]} },
			{x: "5", y: ${weekBefore[5]}},
			{x: "4", y: ${weekBefore[4]}},
			{x: "3", y: ${weekBefore[3]} },
			{x: "2", y: ${weekBefore[2]} },
			{x: "1", y: ${weekBefore[1]} },
			{x: "0", y: ${weekBefore[0]} }
		]
	}]
});
chart.render();

}
</script>
<title>${param.symbol}</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">Alerts</a>
	<a href="help">Help</a>
	<a href="myaccount">My account</a>
	<a href="/WolfOfGeorgeStreet/logout">Logout</a>
</div>

<div class="sidenav">
	<a href ="/WolfOfGeorgeStreet/portfolio">My Portfolio</a>
	<a href ="/WolfOfGeorgeStreet/stocksearch">Stocks</a>
	<a href ="#">Crypto</a>
	<a href ="/WolfOfGeorgeStreet/league">My Leagues</a>
</div>

<div class="main">

	<div class="stockTitle">
		${stock.market}:${stock.title}
	</div>
	
	<div class="stockData">
	
		<div class="currentPrice">
			${currentPrice} USD
		</div>
		<c:if test="${amountChanged ge 0}" >
			<div class="amountChangedPositive">
				<fmt:formatNumber value = "${amountChanged}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>
			</div>
		</c:if>
		
		<c:if test="${amountChanged lt 0}" >
			<div class="amountChangedNegative">
				<fmt:formatNumber value = "${amountChanged}" type = "number" maxFractionDigits = "2" minFractionDigits = "2"/>
			</div>
		</c:if>
		
		<c:if test="${percentChanged ge 0}" >
			<div class= "percentChangedPositive">
				(<fmt:formatNumber value = "${percentChanged}" type = "percent" maxFractionDigits = "2" minFractionDigits = "2"/>)
			</div>
		</c:if>
		
		<c:if test="${percentChanged lt 0}" >
			<div class= "percentChangedNegative">
				(<fmt:formatNumber value = "${percentChanged}" type = "percent" maxFractionDigits = "2" minFractionDigits = "2"/>)
			</div>
		</c:if>
		
	</div>
	
	<div id="chartContainer" style="height: 370px; width: 100%;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	
	<div class="transcationForm" >
	
		<form action="${pageContext.request.contextPath}/detailed-description?symbol=${param.symbol}&market=${param.market}" method="post">
			
			<input type="number" name="amount" placeholder="Number of Shares" size=25 min="0" step=".01">
			
			<select name="transcationType">
				<option value="buy">Buy</option>
				<option value="sell">Sell</option>
			</select>
			
			<select name="league">
				<c:forEach items="${leagueIds}" varStatus="loop">
					<option value="${leagueIds[loop.index]}">${leagueNames[loop.index]}</option>
				</c:forEach>
			</select>
			
			 <input type="hidden" name="price" value="${currentPrice}">
			 
			 <input type="hidden" name="symbol" value="${stock.symbol}">
			
			<button type="submit" name="button" value="button1">Submit</button>
			
		</form>
	
	</div>
	
	<c:if test="${success}" >
		<script>
			alert("Transaction Completed");
		</script>
	</c:if>
	
	<c:if test="${failure}" >
		<script>
			alert('${reason}');
		</script>
	</c:if>

</div>


</body>
</html>