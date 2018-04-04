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
    font-size: 16px; /* Increased text to enable scrolling */
    padding: 20px 10px;
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
Wolf of George Street
</P>

<form action="${pageContext.request.contextPath}/" method="post">
	<input type="text" name="username" placeholder="Username" size=50> 
	<br>
	<input type="password" name="password" placeholder="Password" size=50> 
	<br>
	<button type="submit" name="button" value="button1">Submit</button>
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