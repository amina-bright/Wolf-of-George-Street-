<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

.btn {
	margin-top: 15px;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
    color: white;
    top: 300px;
    left: 200px;
    padding: 8px 28px;
    font-size: 16px;
    cursor: pointer;
}
.btn:hover{
	background-color:
		#FFDE26}
.purple {background-color: #5E0099;} /* Purple */
.purple:hover {background-color: #FFDE26; /*Change color to yellow when hovering*/
				color:black;}

.border{
	border: 1px solid;
	border-color: black;
	border-radius: 5px;
	}
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
<title>Stock Search</title>
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

<form action="${pageContext.request.contextPath}/stocksearch" method="post">
	<input type="text" name="searchContent" placeholder="Search.." size=50 class="border"> <button type="submit" name="button" value="button1" class="btn purple">Submit</button>
</form>
<br><br>
<c:if test="${not empty stocks}" >
<table border=1 frame=void rules=rows>
		<tr>
		    <th>Symbol</th>
		    <th>Title</th>
		    <th>Market</th>
	  </tr>
		<c:forEach items="${stocks}" var="stock">
			<tr>
	        	<td>
		        	<a href="${pageContext.request.contextPath}/detailed-description?symbol=${stock.symbol}&market=${stock.market}">${stock.symbol} </a>
	        	</td>
	        	<td>${stock.title}</td> 
	        	<td>${stock.market}</td>
	    	</tr>
		</c:forEach>
	</table>
</c:if>

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