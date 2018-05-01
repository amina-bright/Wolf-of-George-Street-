<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
  

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

.blue {background-color: #2196F3;} /* Blue */
.blue:hover {background: #0b7dda;}

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
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
    resize: vertical;
}

input[type=number], select, textarea {
    width: 25%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 5px;
    margin-bottom: 5px;
    resize: vertical;
}

input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #45a049;
}

.container {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}

.main {
    margin-left: 140px; /* Same width as the sidebar + left position in px */
    font-size: 28px; /* Increased text to enable scrolling */
    padding: 0px 10px;
}

.form {
	margin-left: 140px; /* Same width as the sidebar + left position in px */
    font-size: 22px; /* Increased text to enable scrolling */
    padding: 0px 10px;
    border-radius: 5px;
    background-color: #f2f2f2;
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
<title>ManageLeague</title>
</head>

<body>
<div class="topnav">
	<a href="alerts">alerts</a>
	<a href="help">help</a>
	<a href="myaccount">My account</a>
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
Vote for league settings!
</font>
</p>



</div>
<c:forEach items="${VoteParam}" varStatus="loop">
<div class="form"> <!--  form to create a league -->

<form action="${pageContext.request.contextPath}/LeagueVotingUser?leagueID=${leagueID}&VoteInstanceID=${VoteInstanceIDs[loop.index]}" method="post">

Kick member:


	
	<!--   <input type="radio" value= 1 name="crypto">${leagueMemberVote[loop.index]}
	
	<option value="${VoteParam[loop.index]}">${VoteParam[loop.index]}</option>      -->
	${VoteParam[loop.index]}<input type="radio" value= 1 name="kick">Yes 
	<input type="radio" value= 0 name="kick">No 

   <br>      

<input type="submit" name="Submit" value="Submit vote"> <!--  submit button -->
	</form>

</c:forEach>	
	
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