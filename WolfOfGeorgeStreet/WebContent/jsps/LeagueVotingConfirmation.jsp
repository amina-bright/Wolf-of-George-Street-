<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>
body{
	background-color: #FFFFFF;
	}
.sidenav {
	width: 150px;
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
    background-color: #FFC53F;
    overflow: hidden;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
}

.topnav a:hover {
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

.btn {
	margin-top: 15px;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
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
.yellow {background-color: #FFC53F;} /* yellow */
.yellow:hover {background-color: #FFDE26; /*Change color to yellow when hovering*/
				color:black;}



</style>
<title>Wolf of George Street</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">alerts</a>
	<a href="help">help</a>
	<a href="myaccount">My account</a>
	<a href=/WolfOfGeorgeStreet/logout>Logout</a>
</div>


<div class="main">


<p> 
Your League changes have been proposed to the other league Members! <br>
</p>

  <button class="btn green" id="button_Continue">Continue</button>
<!--  Continue button that redirects user to league page -->

</div>

<script> 
document.getElementById("button_Continue").onclick = function () {
    location.href = "/WolfOfGeorgeStreet/league"; 
}; 
</script>


</body>
</html>