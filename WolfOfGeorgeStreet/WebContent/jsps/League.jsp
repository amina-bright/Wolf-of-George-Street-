<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
    border-radius: 5px;
    color: white;
    top: 300px;
    left: 200px;
    padding: 14px 28px;
    font-size: 16px;
    cursor: pointer;
}
.btn:hover{
	background-color:
		#FFDE26}
.purple {background-color: #5E0099;} /* Purple */
.purple:hover {background-color: #FFDE26; /*Change color to yellow when hovering*/
				color:black;}

.dropbtn {
    background-color: #5E0099;
    color: white;
    padding: 14px 28px;
    font-size: 16px;
    border: 1px solid;
    border-color: black;
    border-radius: 5px;
    cursor: pointer;
}

.dropbtn:hover, .dropbtn:focus {
    background-color: #FFDE26;
    color: black;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #ddd;
    border: 1px solid;
    border-radius: 5px;
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

.dropdown a:hover {background-color: #5E0099;
				   color: white;}

.show {display:block;}

input[type=text], select, textarea {
    width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
    resize: vertical;
}

input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: 1px solid;
    border-radius: 5px;
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
 
</style>
<title>Wolf of George Street</title>
</head>
<body>
<div class="topnav">
	<a href="alerts">Alerts</a>
	<a href="help">Help</a>
	<a href="myaccount">My Account</a>
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
Welcome to the League Page!
</font>
</p>


<div class="row">
  <div class="column"> 
    <button class="btn purple" id="button_CreateLeague">Create League</button><br><br>
  <!--  Create league button that redirects to create league page -->
  	<p></p>
   <div class="row">
		<div class="dropdown">
		<button onclick="hostedLeagueDrpDwnFunction()" class="dropbtn">Hosted Leagues</button>
		 <!--  manage league button that redirects to manage league page -->
		  <div id="hostedLeagueDrpDwn" class="dropdown-content">
		  	<!-- if the hostedLeagueNames array is not empty, list the elements in the drop-down menu--> 
		    <c:if test="${not empty hostedLeagueNames}" > <%-- create a drop-down element for each hosted league --%>
				<c:forEach items="${hostedLeagueNames}" varStatus="loop">
						<!-- each drop-down menu's option will redirect to manage league page with the corresponding league ID passed as a 
						parameter -->
					    <a href="${pageContext.request.contextPath}/manageLeague?leagueID=${hostedLeagueIDs[loop.index]}">
				        ${hostedLeagueNames[loop.index]} </a> 
				</c:forEach>
			</c:if>
		  </div>
		</div>
	</div>
</div>
  
  <div class="column">
    <button class="btn purple" id="button_JoinLeague">Join League</button><br><br>
     <!--  join league button that redirects to join league page -->
     <p></p>
     <div class="row">
		<div class="dropdown">
		<button onclick="joinedLeagueDrpDwnFunction()" class="dropbtn">View My Leagues</button>
		 <!--  joined league button that redirects to league info page -->
		  <div id="joinedLeagueDrpDwn" class="dropdown-content">
		  	<!-- if the joinedLeagueNames array is not empty, list the elements in the drop-down menu--> 
		    <c:if test="${not empty joinedLeagueNames}" > <%-- create a drop-down element for each joined league --%>
				<c:forEach items="${joinedLeagueNames}" varStatus="loop">
						<!-- each drop-down menu's option will redirect to leagueInfo page with the corresponding league ID passed as a 
						parameter -->
						<a href="${pageContext.request.contextPath}/LeagueInfo?leagueID=${joinedLeagueIDs[loop.index]}">
				        ${joinedLeagueNames[loop.index]} </a>
				</c:forEach>
			</c:if>
			
		  </div>
		</div>
  </div>
  
  </div>
  
 
  
</div>

</div>

<script>
/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */

document.getElementById("button_CreateLeague").onclick = function () {
    location.href = "/WolfOfGeorgeStreet/createleague";
};

document.getElementById("button_JoinLeague").onclick = function () {
    location.href = "/WolfOfGeorgeStreet/joinleague";
};

function hostedLeagueDrpDwnFunction() {
    document.getElementById("hostedLeagueDrpDwn").classList.toggle("show");
}

function joinedLeagueDrpDwnFunction() {
    document.getElementById("joinedLeagueDrpDwn").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}


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