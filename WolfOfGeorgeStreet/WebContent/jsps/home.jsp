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
	
.button {
	margin-top: 15px;
    border: 1px solid;
    border-radius: 5px;
    border-color: black;
    color: white;
    background-color: #5E0099;
    top: 300px;
    left: 200px;
    padding: 8px 14px;
    font-size: 16px;
    cursor: pointer;
}
.button:hover{
	background-color: #FFDE26;
	color: black;
	 		}
	 		
.border {
	border: 1px solid;
	border-color: black;
	border-radius: 5px;
	padding: 2px 2px;
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

.main {
    margin-left: 140px; /* Same width as the sidebar + left position in px */
    font-size: 16px; /* Increased text to enable scrolling */
    padding: 20px 10px;
    text-align: center;
}
@media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
}

.topnav {
    background-color: #1A1F28;
    overflow: hidden;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
}

.topnav a:hover {
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
</style>
<title>Wolf of George Street</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">Alerts</a>
	<a href="help">Help</a>
</div>


<div class="main">

<P>
<font size=8>
Wolf of George Street
</font>
</P><br><br>

<form action="${pageContext.request.contextPath}/" method="post">
	<input type="text" name="username" placeholder="Username" class="border" size=50> 
	<br><br>
	<input type="password" name="password" placeholder="Password" class="border" size=51> 
	<br><br>
	<button type="submit" name="button" value="button1" class="button">Submit</button>
</form>
<c:if test="${not empty success}" >
	<br>
	<c:if test="${not success}" >
		Login Failed
	</c:if>
	
</c:if>

<br>
<br>
<a href="${pageContext.request.contextPath}/createaccount">Create An Account</a>

</div>


</body>
</html>