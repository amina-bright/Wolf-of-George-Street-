<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    border: dotted;
    margin-top: 8px;
    margin-right: 16px;
    font-size: 17px;
}

</style>
<title>Stock Search</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">alerts</a>
	<a href="help">help</a>
	<a href="myaccount">My account</a>
</div>

<div class="sidenav">
	<a href ="/WolfOfGeorgeStreet/">Home</a>
	<a href ="#">My Portfolio</a>
	<a href ="/WolfOfGeorgeStreet/stocksearch">Stocks</a>
	<a href ="#">Crypto</a>
	<a href ="#">My Leagues</a>
</div>

<div class="main">

<form action="${pageContext.request.contextPath}/stocksearch" method="post">
	<input type="text" name="searchContent" placeholder="Search.." size=50> <button type="submit" name="button" value="button1">Submit</button>
</form>

<c:if test="${not empty stocks}" >
<table border=1 frame=void rules=rows>
		<tr>
		    <th>Symbol</th>
		    <th>Title</th>
		    <th>Market</th>
	  </tr>
		<c:forEach items="${stocks}" var="stock">
			<tr>
	        	<td>${stock.symbol}</td>
	        	<td>${stock.title}</td> 
	        	<td>${stock.market}</td>
	    	</tr>
		</c:forEach>
	</table>
</c:if>

</div>


</body>
</html>