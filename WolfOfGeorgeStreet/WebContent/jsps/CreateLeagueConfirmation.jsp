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
.btn {
    border: none;
    color: white;
    top: 300px;
    left: 200px;
    padding: 14px 28px;
    font-size: 16px;
    cursor: pointer;
}

.green {background-color: #4CAF50;} /* Green */
.green:hover {background-color: #46a049;}


</style>
<title>Wolf of George Street</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">Alerts</a>
	<a href="help">Help</a>
</div>


<div class="main">


<p> 
Congratulations on creating a League! 
Your leagueID is:  <c:out value = "${leagueID}"/>
Use this to invite others to your league. <!--  Congratulates user and outputs the randomly generated leagueID -->
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