<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <style>
body{
	background-color: #FFFFFF;
	}
.sidenav {
	width: 150px;
    position: fixed;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
    z-index: 1;
    top: 20px;
    left: 10px;
    background: #FFC53F;
    overflow-x: hidden;
    padding: 8px 0;
    margin: 50px 0px;
}

.sidenav a {
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 25px;
    color: black;
    display: block;
}

.sidenav a:hover {
    background-color: #FFDE26;
}

.column {
    float: left;
    width: 20%;
    padding: 15px;
}

/* Clear floats after the columns */
.row:after {
    content: "";
    display: table;
    clear: both;
}

.btn {
	margin-top: 15px;
    border: 1px solid;
    border-color: black;
    color: black;
    top: 300px;
    left: 200px;
    padding: 14px 28px;
    font-size: 16px;
    cursor: pointer;
}
.btn:hover{
	background-color:
		#FFDE26}
.yellow {background-color: #FFC53F;} /* Purple */
.yellow:hover {background-color: #FFDE26; /*Change color to yellow when hovering*/
				color:black;}

.dropbtn {
    background-color: #3498DB;
    color: white;
    padding: 16px;
    font-size: 16px;
    border: none;
    cursor: pointer;
}

.dropbtn:hover, .dropbtn:focus {
    background-color: #2980B9;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f1f1f1;
    min-width: 160px;
    overflow: auto;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown a:hover {background-color: #ddd}

.show {display:block;}

input[type=text], select, textarea {
    width: 50%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
    resize: vertical;
}

input[type=number], select, textarea {
    width: 25%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    margin-top: 5px;
    margin-bottom: 5px;
    resize: vertical;
}

input[type=submit] {
	margin-top: 15px;
    background-color: #FFC53F;
    color: black;
    padding: 12px 40px;
    font-size: 14px;
    border: 1px solid;;
    border-radius: 5px;
    border-color: black;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #FFDE26;
    color: black;
}

input[type=date] {
	border: 1px solid;
	border-radius: 5px;
	border-color: black;
	}
	
.container {
    border-radius: 5px;
    background-color: #F2F2F2;
    padding: 20px;
}

.main {
    margin-left: 200px; /* Same width as the sidebar + left position in px */
    font-size: 28px; /* Increased text to enable scrolling */
    padding: 0px 10px;
}

.form {
	margin-left: 200px; /* Same width as the sidebar + left position in px */
    font-size: 22px; /* Increased text to enable scrolling */
    padding: 2px 10px;
    border-radius: 5px;
    background-color: #F2F2F2;
}

@media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
}

.topnav {
    background-color: #FFC53F;
    border: 1px solid;
    border-radius: 5px;
    overflow: hidden;
}

.topnav a:hover {
   <%-- background-color: #ddd; --%>
    background-color: #FFDE26;
}

.topnav a {
    float: right;
    color: black;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    height:15px;
    font-size: 17px;
}
</style>
<title>CreateLeague</title>
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

<p>
<font size=8>
Create League!
<br>
</font>
</p>


<form action="${pageContext.request.contextPath}/createaccount" method="post">
	<input type="text" name="fname" placeholder="First Name*" size=50 class="wrapper"> <br>
	<br/>
	<input type="text" name="lname" placeholder="Last Name*" size=50 class="wrapper"> <br>
	<br/>
	<input type="text" name="username" placeholder="Username*" size=50 class="wrapper"> <br>
	<br/>
	<input type="text" name="password" placeholder="Password*" size=50 class="wrapper"> <br>
	<br/>
	<input type="text" name="email" placeholder="Email*" size=50 class="wrapper"> <br>
	<br/>
	
	<p>Strategy: 
	<select name="strategy">
		<option value="Moderate">Moderate </option>
		<option value="Aggressive">Aggressive</option>
		<option value="Conservative">Conservative</option>
	</select>
</p>
	<input type="submit" name="Submit" value="Submit" > 
	
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