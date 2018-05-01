<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
  

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>

body {
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
.color {background-color: #5E0099;} /* Purple */
.color:hover {background-color: #FFC53F; /*Change color to yellow when hovering*/
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
    border-radius: 5px;
    box-sizing: border-box;
    margin-top: 5px;
    margin-bottom: 5px;
    resize: vertical;
}

input[type=submit] {
 	margin-top: 15px;
 	margin-left: 100px;
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

.container {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
    margin-left: 190px;
    width: 700px;
}

.main {
    margin-left: 190px; /* Same width as the sidebar + left position in px */
    font-size: 28px; /* Increased text to enable scrolling */
    padding: 0px 10px;
}

.form {
	margin-left: 0px; /* Same width as the sidebar + left position in px */
    font-size: 22px; /* Increased text to enable scrolling */
    padding: 2px 10px;
    border-radius: 5px;
    background-color: #f2f2f2;
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
<title>JoinLeague</title>
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
	<a href ="/WolfOfGeorgeStreet/trophies">Trophies</a>
	<a href ="/WolfOfGeorgeStreet/league">My Leagues</a>
</div>



<div class="main">

<p>
<font size=8>
Join League<br>
</font>
</p>
</div>

<div class="container">
<div class="form"> <!--  Form to join a league -->
<p>
<form action="${pageContext.request.contextPath}/joinleague" method="post">
		LeagueID: <input type="text" name="leagueID" required><br>
		<!--  Asks user for the leagueID they want to join -->
		
		
	
		<input type="submit" name="Submit" value="Submit">
		 <!--  Submit button -->
	</form>
<p>
	</div>
</div>






<script>


</script>
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