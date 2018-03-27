<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
h1 {text-align:center;}
h2 {text-align:center;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>
.main {
    margin-left: 0px; /* Same width as the sidebar + left position in px */
    font-size: 16px; /* Increased text to enable scrolling */
    padding: 0px 10px;
    text-align: center;
}

</style>
<title>Create Account</title>
</head>

<h1>Registration</h1>
<br>
<br>
<h2>Please register your account information below:</h2>
<br>
<br>

<div class="main">

<form action="${pageContext.request.contextPath}/createaccount" method="post">
	<input type="text" name="fname" placeholder="First Name" size=50>
	<br>
	<input type="text" name="lname" placeholder="Last Name" size=50>
	<br>
	<input type="text" name="username" placeholder="Username*" size=50> 
	<br>
	<input type="text" name="password" placeholder="Password*" size=50> 
	<br>
	<input type="text" name="email" placeholder="Email*" size=50> 
	<br>
	<button type="submit" name="button" value="button1">Submit</button>
</form>
<br>
<br>




<c:if test="${not empty success}" >
	<c:if test="${success}" >
		<text>Account Creation Was Successful </text>
	</c:if>
	
	<c:if test="${not success}" >
		<text>Account Creation Failed </text>
	</c:if>
	
</c:if>

<br>
<br>
* Indicates a required field.
<br>
<br>
<a href="${pageContext.request.contextPath}/">Existing user? Log in.</a>

</div>


</body>
</html>