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
	<a href ="#">My Portfolio</a>
	<a href ="/WolfOfGeorgeStreet/stocksearch">Stocks</a>
	<a href ="#">Crypto</a>
	<a href ="#">My Leagues</a>
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
	
	<div class="transcationForm" >
	
		<form action="${pageContext.request.contextPath}/detailed-description?symbol=${param.symbol}" method="post">
			
			<input type="number" name="amount" placeholder="Number of Shares" size=25 min="0" step=".01">
			
			<select name="transcationType">
				<option>Buy</option>
				<option>Sell</option>
				<option>Short</option>
			</select>
			
			<button type="submit" name="button" value="button1">Submit</button>
			
		</form>
	
	</div>
	


</div>


</body>
</html>